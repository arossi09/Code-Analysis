import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MethodListNanny implements MouseListener {

    private JList<String> List;




    private String selectedFile = null;

    public MethodListNanny(JList<String> List ){
        this.List = List;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
            if (e.getSource().equals(List) && selectedFile != null) {
                String selectedMethod = List.getSelectedValue().split(" ")[0];
                FileExplorePanel.openMethodDetails(selectedFile, selectedMethod);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
    public void setSelectedFile(String selectedFile) {
        this.selectedFile = selectedFile;
    }
}
