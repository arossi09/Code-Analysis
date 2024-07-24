import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

/***
 @author Anthony

 Class Description: adds functionality for
 file explorer so that files are tracked
 when clicked in File panel
 */
public class FileListNanny implements MouseListener {

    private JList<String> myList;


    private String selectedFile = null;
    private List<FileSelectionObserver> observers = new ArrayList<>();

    public FileListNanny(JList<String> List){
        this.myList = List;
    }
    public void addObserver(FileSelectionObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(FileSelectionObserver observer) {
        observers.remove(observer);
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource().equals(myList)) {
            String selectedFile = myList.getSelectedValue().split(" ")[0];
            FileExplorePanel.displayMethods(selectedFile);
            notifyObservers(selectedFile);
        }

    }

    private void notifyObservers(String selectedFile) {
        for (FileSelectionObserver observer : observers) {
            observer.onFileSelected(selectedFile);
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
