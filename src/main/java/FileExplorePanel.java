import javax.swing.*;
import java.awt.*;
import java.util.List;
/***
 @author Anthony

 Class Description: Creates and adds data
 to the file explorer panel from the repo
 parsed through the GithubRepo sentinal.
 */
public class FileExplorePanel extends JPanel{

    private JList<String> fileList;
    private DefaultListModel<String> fileListModel;
    private static DefaultListModel<MethodMetrics> methodListModel;
    private JList<MethodMetrics> methodList;
    private static JDialog methodDialog = new JDialog((java.awt.Frame) null, true);
    private static JTextArea methodDetailsText = new JTextArea();

    public FileExplorePanel(){

        fileListModel = new DefaultListModel<>();
        methodListModel = new DefaultListModel<>();
        fileList = new JList<>(fileListModel);
        methodList = new JList<>(methodListModel);

        Font font = new Font("Arial", Font.BOLD, 12);
        fileList.setFont(font);
        methodList.setFont(font);

        methodList.setCellRenderer(new MethodCellRenderer());

        JLabel filesLabel = new JLabel("Java Files:");
        JLabel methodsLabel = new JLabel("Methods:");

        JPanel filePanel = new JPanel();
        filePanel.setLayout(new BorderLayout());
        filePanel.add(filesLabel, BorderLayout.NORTH);
        filePanel.add(new JScrollPane(fileList), BorderLayout.CENTER);

        JPanel methodPanel = new JPanel();
        methodPanel.setLayout(new BorderLayout());
        methodPanel.add(methodsLabel, BorderLayout.NORTH);
        methodPanel.add(new JScrollPane(methodList), BorderLayout.CENTER);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, filePanel, methodPanel);
        splitPane.setDividerLocation(200);
        setLayout(new BorderLayout());
        add(splitPane, BorderLayout.CENTER);

        MethodListNanny methodNanny = new MethodListNanny(methodList);
        FileListNanny fileListNanny = new FileListNanny(fileList);


        fileListNanny.addObserver(methodNanny);
        fileList.addMouseListener(fileListNanny);
        methodList.addMouseListener(methodNanny);

        methodDialog.setTitle("Method Details");
        methodDialog.setLayout(new BorderLayout());

    }
   public static void openFileExplorerFrame() {
        JFrame explorerFrame = new JFrame("File Explorer");
        explorerFrame.setSize(800, 600);
        explorerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        FileExplorePanel fileExplorePanel = new FileExplorePanel();
        fileExplorePanel.setFiles(GithubRepo.getInstance().getFileMetricsList());
        explorerFrame.add(fileExplorePanel);
        explorerFrame.addWindowListener(new WindowListener());
        explorerFrame.setVisible(true);
    }

    public static void displayMethods(String selectedFile){
        methodListModel.clear();
        FileMetrics file = GithubRepo.getInstance().findFile(selectedFile);
        if(file != null){

            for (MethodMetrics method : file.getMethods()) {
                methodListModel.addElement(method);
            }
        } else {
            methodListModel.addElement(new MethodMetrics("No methods found for the selected file."));
        }

    }

    public static void openMethodDetails(String selectedFile, String selectedMethod){
        FileMetrics file = GithubRepo.getInstance().findFile(selectedFile);
        MethodMetrics method = file.findMethod(selectedMethod);
        methodDetailsText.setText("Lines: " + method.getLines() +
                "\nLOC: "+ method.getLoc() +"\nELOC: " + method.getEloc() +
                "\nILOC: " + method.getIloc() + "\nConditionals: " + method.getConditionalCount());

        methodDetailsText.setEditable(false);
        methodDetailsText.setColumns(15);
        methodDetailsText.setRows(6);
        methodDetailsText.setBackground(null);
        methodDialog.add(methodDetailsText);
        methodDialog.revalidate();
        methodDialog.pack();
        methodDialog.setVisible(true);
    }
    public void setFiles(List<FileMetrics> files) {
        fileListModel.clear();
        for (FileMetrics file : files) {
            fileListModel.addElement(file.getFileName() + " (File)");
        }
    }

}
