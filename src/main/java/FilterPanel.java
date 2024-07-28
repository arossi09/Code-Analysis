import weka.clusterers.ClusterEvaluation;
import weka.clusterers.SimpleKMeans;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/***
 @author Blake

 Class Description: Nanny for events on "Go". Processes data through a GithubRepo instance,
 and opens features based on menu selection
 */


public class FilterPanel extends JPanel {
    private JTextField locField;
    private JTextField elocField;
    private JTextField ilocField;
    private JTextField conditionalsField;
    private JButton runFilterButton;
    private JButton clusteringAnalysisButton;

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
        clusteringAnalysisButton = new JButton("Clustering Analysis"); // New Button
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

        gbc.gridy = 5;
        gbc.insets = new Insets(20, 10, 10, 10);
        add(clusteringAnalysisButton, gbc);
        runFilterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runFilter();
            }
        });

        clusteringAnalysisButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runClusteringAnalysis();
            }
        }); // Add ActionListener
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

    private void runClusteringAnalysis() {
        try {
            String arffFilePath = "src/main/java/data.arff";
            GithubRepo.getInstance().generateArffFile(arffFilePath);
            File arffFile = new File(arffFilePath);
            if (!arffFile.exists() || !arffFile.canRead()) {
                throw new IOException("ARFF file not found or not readable: " + arffFilePath);
            }
            DataSource source = new DataSource(arffFile.getAbsolutePath());
            Instances data = source.getDataSet();

            SimpleKMeans clusterer = new SimpleKMeans();
            clusterer.setNumClusters(3);
            clusterer.buildClusterer(data);
            ClusterEvaluation eval = new ClusterEvaluation();
            eval.setClusterer(clusterer);
            eval.evaluateClusterer(new Instances(data));

            StringBuilder results = new StringBuilder("Clustering Results:\n");
            results.append(eval.clusterResultsToString());

            JOptionPane.showMessageDialog(this, results.toString(), "Clustering Analysis", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error loading ARFF file: " + ex.getMessage(), "File Error", JOptionPane.ERROR_MESSAGE);
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
}
