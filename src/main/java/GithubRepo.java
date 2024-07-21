import com.github.javaparser.StaticJavaParser;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.Statement;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class GithubRepo {

    private static GithubRepo instance = null;
    private static String localRepoPath = null;
    private List<FileMetrics> fileMetricsList = new ArrayList<>();

    private GithubRepo(){

    }

    public static GithubRepo getInstance(){
        if(instance == null){
            instance = new GithubRepo();
        }
        return instance;
    }

    public void cloneRepo(String repoUrl){
        String localPath = "cloned_repos";
        if (Files.exists(Path.of(localPath))) {
            try {
                deleteDirectoryRecursively(Paths.get(localPath).toFile());
            }catch (IOException e){
                return;
            }
        }

        try {
            Git.cloneRepository().setURI(repoUrl).setDirectory(new File(localPath)).call();
            localRepoPath = localPath;
            System.out.println("Repository cloned to " + localPath);
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
    }

    public void deleteClonedDir(){
        String localPath = "cloned_repos";
        if (Files.exists(Path.of(localPath))) {
            try {
                deleteDirectoryRecursively(Paths.get(localPath).toFile());
            }catch (IOException e){
                return;
            }
        }
        System.out.println("No repo cloned yet!");
    }
    public void processData(){
        if (localRepoPath == null) {
            System.out.println("No repository has been cloned yet.");
            return;
        }

        try{
            Files.walk(Paths.get(localRepoPath))
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith("java"))
                    .forEach(this::processFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processFile(Path file) {

        try {
            FileMetrics fileMetrics = new FileMetrics(file.getFileName().toString());
            System.out.println(fileMetrics.getFileName());
            List<String> lines = Files.readAllLines(file);

            int loc = lines.size();
            int eloc = 0;
            int iloc = 0;

            CompilationUnit cu = StaticJavaParser.parse(file);

            for (MethodDeclaration method : cu.findAll(MethodDeclaration.class)) {
                MethodMetrics methodMetrics = new MethodMetrics(method.getNameAsString());
                int methodLines = 0;
                int methodLoc = 0;
                int methodEloc = 0;
                int methodIloc = 0;
                int conditionalCount = 0;

                if (method.getBody().isPresent()) {
                    String[] Lines = method.getBody().get().toString().split("\n");

                    for (String line : Lines) {
                        String trimmedLine = line.trim();
                        methodLines++;

                        if (!trimmedLine.isEmpty() && !trimmedLine.startsWith("//")) {
                            methodLoc++;

                            if((!trimmedLine.contains("{") && !trimmedLine.contains("}")) ||
                                    trimmedLine.contains("for") || ConditionalsDictionary.isConditional(trimmedLine)){
                                methodEloc++;
                            }
                            if(trimmedLine.contains(";")){
                                methodIloc++;
                            }
                            if (ConditionalsDictionary.isConditional(trimmedLine)) {
                                conditionalCount++;
                            }
                        }
                    }
                }


                methodMetrics.setLines(methodLines);
                methodMetrics.setLoc(methodLoc);
                methodMetrics.setEloc(methodEloc);
                methodMetrics.setIloc(methodIloc);
                methodMetrics.setConditionalCount(conditionalCount);
                fileMetrics.addMethod(methodMetrics);


                eloc += methodEloc;
                iloc += methodIloc;
            }

            fileMetrics.setLoc(loc);
            fileMetrics.setEloc(eloc);
            fileMetrics.setIloc(iloc);
            fileMetricsList.add(fileMetrics);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void deleteDirectoryRecursively(File dir) throws IOException {
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    deleteDirectoryRecursively(file);
                }
            }
        }
        if (!dir.delete()) {
            throw new IOException("Cuoldnt delete dir!");
        }
    }


}
