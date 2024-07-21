import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
public class FileExplorePanel extends JPanel {

    private JList<String> fileList;
    private DefaultListModel<String> fileListModel;
    private JTextArea methodDetails;

    public FileExplorePanel(){
        fileListModel = new DefaultListModel<>();
        fileList = new JList<>(fileListModel);
        methodDetails = new JTextArea();
        methodDetails.setEditable(false);

        Font font = new Font("", Font.PLAIN, 16);
        fileList.setFont(font);
        methodDetails.setFont(font);

        JScrollPane fileScrollPane = new JScrollPane(fileList);
        JScrollPane methodScrollPane = new JScrollPane(methodDetails);

        fileScrollPane.setPreferredSize(new Dimension(200, 500));
        methodScrollPane.setPreferredSize(new Dimension(500, 500));

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, fileScrollPane, methodScrollPane);
        splitPane.setDividerLocation(200);
        add(splitPane, BorderLayout.CENTER);

        fileList.addMouseListener(new MouseNanny(fileList, methodDetails));

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

    public static void displayMethods(String selectedFile, JTextArea methodDetails){
        FileMetrics file = GithubRepo.getInstance().findFile(selectedFile);
        if(file != null){
            StringBuilder methodDetailsText = new StringBuilder();
            for (MethodMetrics method : file.getMethods()) {
                methodDetailsText.append("Method Name: ").append(method.getMethodName()).append("\n");
                methodDetailsText.append("LOC: ").append(method.getLoc()).append("\n");
                methodDetailsText.append("ELOC: ").append(method.getEloc()).append("\n");
                methodDetailsText.append("ILOC: ").append(method.getIloc()).append("\n");
                methodDetailsText.append("Conditionals: ").append(method.getConditionalCount()).append("\n\n");
            }
            methodDetails.setText(methodDetailsText.toString());
        } else {
            methodDetails.setText("No methods found for the selected file.");
        }

    }
    public void setFiles(List<FileMetrics> files) {
        fileListModel.clear();
        for (FileMetrics file : files) {
            fileListModel.addElement(file.getFileName());
        }
    }
}
