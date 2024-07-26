import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/***
 @author Anthony

 Class Description: Creates custom cell
 renderer used for the method list in file
 explorer window.
 */
public class MethodCellRenderer extends JPanel implements ListCellRenderer<MethodMetrics> {
    private JLabel methodLabel;
    private JLabel statusLabel;

    private JLabel conditionFlagLabel;
    private JPanel iconPanel;

    public MethodCellRenderer() {
        setLayout(new BorderLayout(5, 5));
        methodLabel = new JLabel();
        statusLabel = new JLabel();
        conditionFlagLabel = new JLabel();
        iconPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 5));
        iconPanel.add(statusLabel);
        iconPanel.add(conditionFlagLabel);

        add(iconPanel, BorderLayout.WEST);
        add(methodLabel, BorderLayout.CENTER);
    }


    @Override
    public Component getListCellRendererComponent(JList<? extends MethodMetrics> list, MethodMetrics value, int index, boolean isSelected, boolean cellHasFocus) {
        String methodName = value.getMethodName();
        String metricStatus = value.getMetricStatus();

        methodLabel.setText(methodName + " (" + value.getLines() + ")");
        statusLabel.setIcon(createCircleIcon(getColor(metricStatus)));
        if(value.getConditionalCount() > 10){
            conditionFlagLabel.setIcon(createTriangleIcon());
        }else{
            conditionFlagLabel.setIcon(null);
        }

        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
            iconPanel.setBackground(list.getSelectionBackground());
            iconPanel.setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
            iconPanel.setBackground(list.getBackground());
            iconPanel.setForeground(list.getForeground());
        }


        return this;
    }

    private Color getColor(String metricStatus) {
        switch (metricStatus) {
            case "good":
                return Color.GREEN;
            case "average":
                return Color.YELLOW;
            case "bad":
                return Color.RED;
            default:
                return Color.GRAY;
        }
    }

    private Icon createCircleIcon(Color color) {
        int size = 10;
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();
        g2.setColor(color);
        g2.fillOval(0, 0, size, size);
        g2.dispose();
        return new ImageIcon(image);
    }
    private Icon createTriangleIcon() {
        int size = 10;
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();

        g2.setColor(Color.red);
        int[] xPoints = {0, size / 2, size};
        int[] yPoints = {size, 0, size};
        g2.fillPolygon(xPoints, yPoints, 3);

        g2.setColor(Color.WHITE);
        int margin = size / 4;
        int[] xPointsInner = {margin, size / 2, size - margin};
        int[] yPointsInner = {size - margin, margin, size - margin};
        g2.fillPolygon(xPointsInner, yPointsInner, 3);

        g2.dispose();
        return new ImageIcon(image);
    }
}
