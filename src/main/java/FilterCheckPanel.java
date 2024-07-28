import javax.swing.*;
import java.awt.*;

/***
 @author Blake

 Class Description: Top Right panel on filtered results showing the metrics
 and filter pass/fails
 */
public class FilterCheckPanel extends JPanel {
    private JLabel filterCheckLabel;

    public FilterCheckPanel() {
        setLayout(new BorderLayout());
        filterCheckLabel = new JLabel();
        filterCheckLabel.setVerticalAlignment(SwingConstants.TOP);
        add(new JScrollPane(filterCheckLabel), BorderLayout.CENTER);
    }

    public void updateFilterChecks(MethodMetrics method, int loc, int eloc, int iloc, int conditionals) {
        StringBuilder checks = new StringBuilder();
        checks.append("<html>");
        checks.append("<p>Method: ").append(method.getMethodName()).append("</p>");
        checks.append("<p>Lines: ").append(method.getLines()).append("</p>");
        checks.append("<p>LOC: ").append(method.getLoc()).append("</p>");
        checks.append("<p>ELOC: ").append(method.getEloc()).append("</p>");
        checks.append("<p>ILOC: ").append(method.getIloc()).append("</p>");
        checks.append("<p>Conditionals: ").append(method.getConditionalCount()).append("</p>");
        checks.append("<p>Status: ").append(method.getMetricStatus()).append("</p>");
        checks.append("<p>LOC Filter: ").append(getFilterResult(method.getLoc(), loc)).append("</p>");
        checks.append("<p>ELOC Filter: ").append(getFilterResult(method.getEloc(), eloc)).append("</p>");
        checks.append("<p>ILOC Filter: ").append(getFilterResult(method.getIloc(), iloc)).append("</p>");
        checks.append("<p>Conditionals Filter: ").append(getFilterResult(method.getConditionalCount(), conditionals)).append("</p>");
        checks.append("</html>");

        filterCheckLabel.setText(checks.toString());
    }

    private String getFilterResult(int methodValue, int filterValue) {
        if (methodValue > filterValue) {
            return "<span style='color:red'>Fail</span>";
        } else {
            return "<span style='color:green'>Pass</span>";
        }
    }
}
