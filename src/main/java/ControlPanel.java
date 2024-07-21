import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ControlPanel extends  JPanel{


    public ControlPanel(){
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


        add(inputPanel);
        add(goButton);
    }



}
