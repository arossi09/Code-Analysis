import javax.swing.*;
import java.awt.*;

public class FilterCheckPanel extends JPanel {
    private JTextArea filterCheckArea;

    public FilterCheckPanel() {
        setLayout(new BorderLayout());
        filterCheckArea = new JTextArea();
        filterCheckArea.setEditable(false);
        add(new JScrollPane(filterCheckArea), BorderLayout.CENTER);
    }

    public void updateFilterChecks(String filterChecks) {
        filterCheckArea.setText(filterChecks);
    }
}
