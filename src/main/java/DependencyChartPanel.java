import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/***
 @author Noa

 Class Description: Creates the Depenedency Chart Panel
 */
public class DependencyChartPanel extends JPanel{


    public DependencyChartPanel(){
    }
    public static void openDependencyChartFrame() {
        JFrame frame = new JFrame("Class Reference Graph");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 600);

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Set<String>> entry : DependencyData.getInstance().classReferences.entrySet()) {
            sb.append("Class ").append(entry.getKey()).append(" references: ").append(entry.getValue()).append("\n");
        }
        textArea.setText(sb.toString());

        // Graph visualization
        mxGraph mxGraph = new mxGraph();
        Object parent = mxGraph.getDefaultParent();
        mxGraph.getModel().beginUpdate();

            Map<String, Object> vertexMap = new HashMap<>();
            for (String vertex : DependencyData.getInstance().graph.vertexSet()) {
                Object v = mxGraph.insertVertex(parent, null, vertex, 100, 100, 80, 30);
                vertexMap.put(vertex, v);
            }

            for (DefaultEdge edge : DependencyData.getInstance().graph.edgeSet()) {
                String source = DependencyData.getInstance().graph.getEdgeSource(edge);
                String target = DependencyData.getInstance().graph.getEdgeTarget(edge);
                mxGraph.insertEdge(parent, null, "", vertexMap.get(source), vertexMap.get(target));
            }
            mxGraph.getModel().endUpdate();

        mxGraphComponent graphComponent = new mxGraphComponent(mxGraph);
        graphComponent.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        graphComponent.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        graphComponent.setWheelScrollingEnabled(true);

        mxGraph.getModel().beginUpdate();
        new mxCircleLayout(mxGraph).execute(mxGraph.getDefaultParent());
        mxGraph.getModel().endUpdate();

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollPane, graphComponent);
        splitPane.setDividerLocation(200);
        frame.add(splitPane);
        frame.add(scrollPane);
        frame.setVisible(true);
    }
}

