import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FourColorCircle extends JPanel {
    private int highlightedSegment = -1;
    private int segmentClicked = -1;

    public FourColorCircle() {
        setPreferredSize(new Dimension(200, 200));
        setOpaque(false);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                segmentClicked = getSegment(e.getX(), e.getY());
                System.out.println("Segment clicked: " + segmentClicked);
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int segment = getSegment(e.getX(), e.getY());
                if (segment != highlightedSegment) {
                    highlightedSegment = segment;
                    repaint();
                }
            }
        });
    }

    private int getSegment(int x, int y) {
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        double angle = Math.atan2(y - centerY, x - centerX);

        if (angle < 0) {
            angle += 2 * Math.PI;
        }

        return (int) (angle / (Math.PI / 2));
    }

    public int getSegmentClicked() {
        return segmentClicked;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        int width = getWidth();
        int height = getHeight();
        int diameter = Math.min(width, height) - 10;
        int radius = diameter / 2;
        int centerX = width / 2;
        int centerY = height / 2;

        Color[] colors = {Color.GREEN, Color.RED, Color.YELLOW, Color.BLUE};
        for (int i = 0; i < 4; i++) {
            if (i == highlightedSegment) {
                g2d.setColor(colors[i].brighter());
            } else {
                g2d.setColor(colors[i]);
            }
            g2d.fillArc(centerX - radius, centerY - radius, diameter, diameter, i * 90, 90);
        }

        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(5));
        g2d.drawOval(centerX - radius, centerY - radius, diameter, diameter);
    }
    public void setSegmentClicked(int segmentClicked) {
        this.segmentClicked = segmentClicked;
    }
}