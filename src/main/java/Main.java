public class Main {
    public static void main(String[] args) {

        GithubRepo gitInstance = GithubRepo.getInstance();
        gitInstance.cloneRepo("https://github.com/arossi09/Game-Of-Life");
        gitInstance.processData();
    }

    public void Main(){


    }

}