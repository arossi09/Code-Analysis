import java.awt.event.WindowEvent;
/***
 @author Anthony

 Class Description: Clears the sentinal
 instance and data when window is closed
 when attactched (useful for clearing
 sentinal when feature is closed)
 */
public class WindowListener implements java.awt.event.WindowListener {

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {

    }

    @Override
    public void windowClosed(WindowEvent e) {
        GithubRepo.getInstance().clearData();
    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
