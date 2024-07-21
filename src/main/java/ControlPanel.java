import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ControlPanel extends  JPanel{


    public ControlPanel(){
        setLayout(new BorderLayout());

        JTextField urlTextField = new JTextField(30);
        ActionNanny nanny = new ActionNanny(urlTextField);

        JButton goButton = new JButton("Go");
        goButton.addActionListener(nanny);
        add(goButton, BorderLayout.EAST);
        add(urlTextField, BorderLayout.WEST);
    }


}
