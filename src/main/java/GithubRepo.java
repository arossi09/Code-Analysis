import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.util.IO;

import java.awt.*;
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
            List<String> lines = Files.readAllLines(file);
//
            int loc = 0;
            int eloc = 0;
            int iloc = 0;
            int conditionals = 0;

            for (String line : lines) {
                System.out.println(line);
                String trimmedLine = line.trim();
                if (!trimmedLine.isEmpty() && !trimmedLine.startsWith("//")) {
                    loc++;
                    if (trimmedLine.contains(";")) {
                        eloc++;
                        iloc++;
                    }
                    if (conditionalsDictionary.isConditional(trimmedLine)) {
                        eloc++;
                        conditionals++;
                    }
                }


            }

            fileMetrics.setLines(lines.size());
            fileMetrics.setEloc(eloc);
            fileMetrics.setIloc(iloc);
            fileMetrics.setLoc(loc);
            fileMetricsList.add(fileMetrics);
        }catch (IOException e){
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
            throw new IOException("Couldnt delete dir!");
        }
    }


}
