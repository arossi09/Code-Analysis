import javax.swing.*;
import java.awt.*;

/***
 @author Blake

 Class Description: Nanny for events on "Go". Processes data through a GithubRepo instance,
 and opens features based on menu selection
 */
public class MethodDetailPanel extends JPanel {
    private JTextArea methodDetailArea;

    public MethodDetailPanel() {
        setLayout(new BorderLayout());
        methodDetailArea = new JTextArea();
        methodDetailArea.setEditable(false);
        add(new JScrollPane(methodDetailArea), BorderLayout.CENTER);
    }

    public void updateMethodDetails(String methodDetails) {
        methodDetailArea.setText(methodDetails);
    }
}
