import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class BarChartPanel extends JPanel {
    private static List<FileMetrics> fileMetricsList;
    private static String chartTitle;
    private DefaultListModel<String> fileListModel;
    private static JTextArea methodDetailsText = new JTextArea();

    public BarChartPanel(String chartTitle, List<FileMetrics> fileMetricsList) {
        BarChartPanel.chartTitle = chartTitle;
        BarChartPanel.fileMetricsList = fileMetricsList;
        this.fileListModel = new DefaultListModel<>();
        setLayout(new BorderLayout());
        openBarChartPanel();
    }

    public void openBarChartPanel() {
        JFreeChart barChart = ChartFactory.createBarChart(
                chartTitle,
                "Name of method",
                "Amount",
                createDataset(),
                PlotOrientation.VERTICAL,
                true, true, false);
        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(1000, 800));
        setLayout(new BorderLayout());
        add(chartPanel, BorderLayout.CENTER);
        JFrame explorerFrame = new JFrame("Charts");
        explorerFrame.setSize(1000, 800);
        explorerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        explorerFrame.setContentPane(this);
        explorerFrame.setVisible(true);
    }
    private DefaultCategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        if (fileMetricsList == null) {
            fileMetricsList = GithubRepo.getInstance().getFileMetricsList();
        }
        for (FileMetrics fileMetrics : fileMetricsList) {
            dataset.addValue(fileMetrics.getLines(), "Lines", fileMetrics.getFileName());
            dataset.addValue(fileMetrics.getLoc(), "LOC", fileMetrics.getFileName());
            dataset.addValue(fileMetrics.getEloc(), "ELOC", fileMetrics.getFileName());
            dataset.addValue(fileMetrics.getIloc(), "ILOC", fileMetrics.getFileName());
            dataset.addValue(fileMetrics.getConditionals(), "Conditionals", fileMetrics.getFileName());
        }
        return dataset;
    }

    public static void runBarChart() {
        SwingUtilities.invokeLater(() -> {
            String chartTitle = "File Statistics";
            List<FileMetrics> fileMetricsList = GithubRepo.getInstance().getFileMetricsList();
            BarChartPanel chartPanel = new BarChartPanel(chartTitle, fileMetricsList);
            chartPanel.openBarChartPanel();
        });
    }

}

