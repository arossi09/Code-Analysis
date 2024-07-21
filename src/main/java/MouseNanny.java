import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseNanny implements MouseListener {

    private JList<String> fileList;
    private JTextArea methodDetails;

    public MouseNanny(JList<String> fileList, JTextArea methodDetails){
        this.fileList = fileList;
        this.methodDetails = methodDetails;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 1) {
            String selectedFile = fileList.getSelectedValue();
            FileExplorePanel.displayMethods(selectedFile, methodDetails);
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
}
