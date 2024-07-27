import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;

public class FilterPanel extends JPanel {

    private JTextField locField;
    private JTextField elocField;
    private JTextField ilocField;
    private JTextField conditionalsField;
    private JButton runFilterButton;

    public FilterPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        JLabel locLabel = new JLabel("LOC:");
        locField = new JTextField(10);
        JLabel elocLabel = new JLabel("ELOC:");
        elocField = new JTextField(10);
        JLabel ilocLabel = new JLabel("ILOC:");
        ilocField = new JTextField(10);
        JLabel conditionalsLabel = new JLabel("Conditionals:");
        conditionalsField = new JTextField(10);

        runFilterButton = new JButton("Run Filter");
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        add(locLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(locField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        add(elocLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(elocField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        add(ilocLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(ilocField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        add(conditionalsLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(conditionalsField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(runFilterButton, gbc);
        runFilterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runFilter();
            }
        });
    }

    private void runFilter() {
        try {
            int loc = Integer.parseInt(locField.getText());
            int eloc = Integer.parseInt(elocField.getText());
            int iloc = Integer.parseInt(ilocField.getText());
            int conditionals = Integer.parseInt(conditionalsField.getText());

            FilteredResults.openFilteredResultsFrame(loc, eloc, iloc, conditionals);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }




    public static void openFilterPanel() {
        JFrame filterFrame = new JFrame("Filter");
        filterFrame.setSize(400, 300);
        filterFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        FilterPanel filterPanel = new FilterPanel();
        filterFrame.add(filterPanel);
        filterFrame.setVisible(true);
    }

//    //public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> openFilterPanel());
//    }
}
