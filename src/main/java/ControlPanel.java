import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/***
 @author Anthony

 Class Description: Creates control
 panel for user to select featurues
 and insert their Github repo url
 */
public class ControlPanel extends  JPanel{

    private JTextField urlTextField;
    private JComboBox<String> selctionsBox;

    public JTextField getUrlTextField() {
        return urlTextField;
    }

    public JComboBox<String> getSelctionsBox() {
        return selctionsBox;
    }

    public ControlPanel(){
        String[] selections = {"File Explorer","References", "Charts", "Filter"};

        setPreferredSize(new Dimension(100, 100));
        setLayout(new BorderLayout());
        setVisible(true);
        urlTextField = new JTextField(28);
        ActionNanny nanny = new ActionNanny(this);
        JPanel inputPanel = new JPanel();
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));

        JLabel urlLabel = new JLabel("GitHub URL:");
        inputPanel.add(urlLabel);
        inputPanel.add(urlTextField);

        JButton goButton = new JButton("Go");
        goButton.addActionListener(nanny);
        goButton.setPreferredSize(new Dimension(50, 30));

        selctionsBox = new JComboBox<>(selections);


        add(inputPanel);
        add(goButton);
        add(selctionsBox);
    }



}
