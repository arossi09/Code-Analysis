import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
/***
@author Anthony

Class Description: adds functionality for
file explorer so that methods are tracked
when clicked in Methods panel
*/

public class MethodListNanny implements MouseListener, FileSelectionObserver {

    private JList<MethodMetrics> List;
    private String selectedFile = null;

    public MethodListNanny(JList<MethodMetrics> List ){
        this.List = List;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
            if (e.getSource().equals(List) && selectedFile != null) {
                String selectedMethod = List.getSelectedValue().getMethodName();
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

    @Override
    public void onFileSelected(String selectedFile) {
        this.selectedFile = selectedFile;
    }
}
