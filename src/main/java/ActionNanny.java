import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionNanny implements ActionListener {

    JTextField repoUrl;
    public ActionNanny(JTextField url){
        if(url != null){
            repoUrl = url;
        }else {
            System.out.println("No url entered!");
        }

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Go")){
            GithubRepo gitInstance = GithubRepo.getInstance();
            gitInstance.cloneRepo(repoUrl.getText());
            gitInstance.processData();
            gitInstance.deleteClonedDir();
        }
    }
}
