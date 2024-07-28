import javax.swing.*;
import java.awt.*;

/***
 @author Blake

 Class Description: Separate panel for the method Details
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
