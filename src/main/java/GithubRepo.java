import com.github.javaparser.StaticJavaParser;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class GithubRepo {

    private static GithubRepo instance = null;
    private static String localRepoPath = null;
    private List<FileMetrics> fileMetricsList = new ArrayList<>();
    private Git cloneCommand;
    private static String localPath = "cloned_repos";

    private GithubRepo(){

    }

    public List<FileMetrics> getFileMetricsList() {
        return fileMetricsList;
    }

    public static GithubRepo getInstance(){
        if(instance == null){
            instance = new GithubRepo();
        }
        return instance;
    }

    public FileMetrics findFile(String fileName){
        for(FileMetrics file : fileMetricsList){
            if(file.getFileName().equals(fileName)){
                return file;
            }
        }
        return null;
    }

    public void cloneRepo(String repoUrl){
        if (Files.exists(Path.of(localPath))) {
            try {
                FileUtils.deleteDirectory(Paths.get(localPath).toFile());
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        try {
            cloneCommand = Git.cloneRepository().setURI(repoUrl).setDirectory(new File(localPath)).call();
            localRepoPath = localPath;
            System.out.println("Repository cloned to " + localPath);
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
    }

    public void deleteClonedDir(){
        if (Files.exists(Path.of(localPath))) {
            try {
                FileUtils.deleteDirectory(Paths.get(localPath).toFile());
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
                    .filter(path -> path.toString().endsWith("java") || path.toString().endsWith(".c"))
                    .forEach(this::processFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processFile(Path file) {

        try {
            FileMetrics fileMetrics = new FileMetrics(file.getFileName().toString());
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



    public void clearData() {
        cloneCommand.close();
        System.out.println("clearing this");
        fileMetricsList.clear();
        localRepoPath = null;
        System.out.println("Data cleared successfully.");
    }

}
