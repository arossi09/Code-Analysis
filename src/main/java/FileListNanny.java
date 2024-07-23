import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class FileListNanny implements MouseListener {

    private JList<String> List;


    private String selectedFile = null;

    private MethodListNanny methodNanny;

    public FileListNanny(JList<String> List, MethodListNanny methodNanny){
        this.List = List;
        this.methodNanny = methodNanny;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource().equals(List)) {
            String selectedFile = List.getSelectedValue();
            FileExplorePanel.displayMethods(selectedFile);
            methodNanny.setSelectedFile(selectedFile);
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
