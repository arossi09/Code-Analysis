import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionNanny extends JFrame implements ActionListener{

    JTextField repoUrl;
    ControlPanel p;
    public ActionNanny(JTextField url, ControlPanel p){
        this.p = p;
        if(url != null){
            repoUrl = url;
        }else {
            System.out.println("No url entered!");
        }

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Go")){
            //check drop down for certain features
            if(!repoUrl.getText().isEmpty()) {
                GithubRepo gitInstance = GithubRepo.getInstance();
                gitInstance.cloneRepo(repoUrl.getText());
                gitInstance.processData();
                gitInstance.deleteClonedDir();
                FileExplorePanel.openFileExplorerFrame();
            }
        }
    }


}
