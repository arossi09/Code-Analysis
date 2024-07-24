import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ControlPanel extends  JPanel{


    public ControlPanel(){
        String[] selections = {"feature1","feature2", "feature3", "feature 4"};
        setPreferredSize(new Dimension(100, 100));
        setLayout(new BorderLayout());
        setVisible(true);
        JTextField urlTextField = new JTextField(28);
        ActionNanny nanny = new ActionNanny(urlTextField, this);
        JPanel inputPanel = new JPanel();
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));

        JLabel urlLabel = new JLabel("GitHub URL:");
        inputPanel.add(urlLabel);
        inputPanel.add(urlTextField);

        JButton goButton = new JButton("Go");
        goButton.addActionListener(nanny);
        goButton.setPreferredSize(new Dimension(50, 30));

        JComboBox<String> selctionsBox = new JComboBox<>(selections);


        add(inputPanel);
        add(goButton);
        add(selctionsBox);
    }



}
