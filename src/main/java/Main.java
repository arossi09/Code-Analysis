import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
    public static void main(String[] args) {

        Main ui = new Main();
        ui.setSize(500, 100);
        ui.setTitle("Github Repo Control Panel");
        ui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ui.setResizable(true);
        ui.setVisible(true);
    }

    public Main(){
        ControlPanel p = new ControlPanel();
        add(p);

    }

}