import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/***
 @author Anthony
 @author Blake

 Class Description: Nanny for events on "Go". Processes data through a GithubRepo instance,
 and opens features based on menu selection
 */
public class ActionNanny extends JFrame implements ActionListener{

    private JTextField repoUrl;
    private ControlPanel p;
    public ActionNanny(ControlPanel p){
        this.p = p;
        if(p.getUrlTextField() != null){
            repoUrl = p.getUrlTextField();
        }else {
            System.out.println("No url entered!");
        }

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Go")){
            if(!repoUrl.getText().isEmpty()) {
                GithubRepo gitInstance = GithubRepo.getInstance();

                if (gitInstance.cloneRepo(repoUrl.getText()) == -1) {
                    return;
                }

                if(gitInstance.getFileMetricsList().isEmpty()){
                    gitInstance.processData();
                    gitInstance.deleteClonedDir();
                }




                //check drop down for certain features
                if (p.getSelctionsBox().getSelectedItem().equals("File Explorer")){
                    FileExplorePanel.openFileExplorerFrame();
                }
                else if (p.getSelctionsBox().getSelectedItem().equals("Filter")){
                    FilterPanel.openFilterPanel();
                }
                else if (p.getSelctionsBox().getSelectedItem().equals("UML diagram")){

                }
                else if (p.getSelctionsBox().getSelectedItem().equals("Charts")){

                }
            }
        }
    }


}
