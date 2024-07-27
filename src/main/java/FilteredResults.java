import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FilteredResults extends JFrame {
    private static FilteredResults instance;
    private JList<String> fileList;
    private DefaultListModel<String> fileListModel;
    private DefaultListModel<MethodMetrics> methodListModel;
    private JList<MethodMetrics> methodList;
    private MethodDetailPanel methodDetailPanel;
    private FilterCheckPanel filterCheckPanel;
    private JTextArea textField;

    private FilteredResults() {
        setTitle("Filtered Results");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        fileListModel = new DefaultListModel<>();
        fileList = new JList<>(fileListModel);

        methodListModel = new DefaultListModel<>();
        methodList = new JList<>(methodListModel);
        methodList.setCellRenderer(new MethodCellRenderer());

        methodDetailPanel = new MethodDetailPanel();
        filterCheckPanel = new FilterCheckPanel();
        textField = new JTextArea();
        textField.setEditable(false);
        setLayout(new BorderLayout());

        JSplitPane fileMethodSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(fileList), new JScrollPane(methodList));
        fileMethodSplitPane.setDividerLocation(200);

        JSplitPane filterDetailSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, filterCheckPanel, methodDetailPanel);
        filterDetailSplitPane.setDividerLocation(200);

        JSplitPane mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, fileMethodSplitPane, filterDetailSplitPane);
        mainSplitPane.setDividerLocation(400);

        add(mainSplitPane, BorderLayout.CENTER);
        add(new JScrollPane(textField), BorderLayout.SOUTH);

        fileList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                displayMethods(fileList.getSelectedValue());
            }
        });
        methodList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                displayMethodDetails(methodList.getSelectedValue());
            }
        });
    }

    public static FilteredResults getInstance() {
        if (instance == null) {
            instance = new FilteredResults();
        }
        return instance;
    }

    public void setFiles(List<FileMetrics> files) {
        fileListModel.clear();
        System.out.println("Setting files in FilteredResults:");
        for (FileMetrics file : files) {
            System.out.println("File: " + file.getFileName());
            fileListModel.addElement(file.getFileName());
        }
    }

    private void displayMethods(String selectedFile) {
        methodListModel.clear();
        FileMetrics file = GithubRepo.getInstance().findFile(selectedFile);
        if (file != null) {
            System.out.println("Displaying methods for file: " + selectedFile);
            for (MethodMetrics method : file.getMethods()) {
                System.out.println("Method: " + method.getMethodName());
                methodListModel.addElement(method);
            }
        }
    }

    private void displayMethodDetails(MethodMetrics selectedMethod) {
        if (selectedMethod != null) {
            filterCheckPanel.updateFilterChecks("Method: " + selectedMethod.getMethodName() +
                    "\nLines: " + selectedMethod.getLines() +
                    "\nLOC: " + selectedMethod.getLoc() +
                    "\nELOC: " + selectedMethod.getEloc() +
                    "\nILOC: " + selectedMethod.getIloc() +
                    "\nConditionals: " + selectedMethod.getConditionalCount() +
                    "\nStatus: " + selectedMethod.getMetricStatus());

            String methodCode = "Placeholder for method code: " + selectedMethod.getMethodName();
            methodDetailPanel.updateMethodDetails(methodCode);

            textField.setText("Placeholder text for method: " + selectedMethod.getMethodName());
        }
    }

    public void filterResults(int loc, int eloc, int iloc, int conditionals) {
        List<FileMetrics> filteredFiles = new ArrayList<>();
        System.out.println("Filtering results with criteria - LOC: " + loc + ", ELOC: " + eloc + ", ILOC: " + iloc + ", Conditionals: " + conditionals);
        for (FileMetrics file : GithubRepo.getInstance().getFileMetricsList()) {
            FileMetrics filteredFile = new FileMetrics(file.getFileName());
            for (MethodMetrics method : file.getMethods()) {
                if (method.getLoc() >= loc && method.getEloc() >= eloc && method.getIloc() >= iloc && method.getConditionalCount() >= conditionals) {
                    filteredFile.addMethod(method);
                }
            }
            if (!filteredFile.getMethods().isEmpty()) {
                filteredFiles.add(filteredFile);
            }
        }
        setFiles(filteredFiles);
    }

    public static void openFilteredResultsFrame(int loc, int eloc, int iloc, int conditionals) {
        FilteredResults filteredResults = FilteredResults.getInstance();
        filteredResults.filterResults(loc, eloc, iloc, conditionals);
        filteredResults.setVisible(true);
    }
}
