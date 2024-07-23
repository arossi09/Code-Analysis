import javax.swing.*;
import java.awt.*;
import java.util.List;
public class FileExplorePanel extends JPanel {

    private JList<String> fileList;
    private DefaultListModel<String> fileListModel;
    private static DefaultListModel<String> methodListModel;
    private JList<String> methodList;

    public FileExplorePanel(){
        fileListModel = new DefaultListModel<>();
        methodListModel = new DefaultListModel<>();
        fileList = new JList<>(fileListModel);
        methodList = new JList<>(methodListModel);

        Font font = new Font("Arial", Font.BOLD, 16);
        fileList.setFont(font);
        methodList.setFont(font);

        JMenu menu = new JMenu();
        add(menu);
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
        fileList.addMouseListener(new FileListNanny(fileList, methodNanny));
        methodList.addMouseListener(methodNanny);
    }
   public static void openFileExplorerFrame() {
        JFrame explorerFrame = new JFrame("File Explorer");
        explorerFrame.setSize(800, 600);
        explorerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        FileExplorePanel fileExplorePanel = new FileExplorePanel();
        fileExplorePanel.setFiles(GithubRepo.getInstance().getFileMetricsList());
        explorerFrame.add(fileExplorePanel);

        explorerFrame.setVisible(true);
    }

    public static void displayMethods(String selectedFile){
        methodListModel.clear();
        FileMetrics file = GithubRepo.getInstance().findFile(selectedFile);
        if(file != null){

            for (MethodMetrics method : file.getMethods()) {
                methodListModel.addElement(method.getMethodName() + " (" + method.getLines() + ") ");
            }
        } else {
            methodListModel.addElement("No methods found for the selected file.");
        }

    }

    public static void openMethodDetails(String selectedFile, String selectedMethod){
        FileMetrics file = GithubRepo.getInstance().findFile(selectedFile);
        MethodMetrics method = file.findMethod(selectedMethod);
        JOptionPane.showMessageDialog(null, "LOC" + method.getEloc());
    }
    public void setFiles(List<FileMetrics> files) {
        fileListModel.clear();
        for (FileMetrics file : files) {
            fileListModel.addElement(file.getFileName());
        }
    }

}
