import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FourColorCircle extends JPanel {

    public FourColorCircle() {
        setPreferredSize(new Dimension(400, 400));
        setOpaque(false);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                int centerX = getWidth() / 2;
                int centerY = getHeight() / 2;
                double angle = Math.atan2(y - centerY, x - centerX);

                if (angle < 0) {
                    angle += 2 * Math.PI;
                }

                int segment = (int) (angle / (Math.PI / 2));
                System.out.println("Segment clicked: " + segment);
            }
        });

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

        g2d.setColor(Color.RED);
        g2d.fillArc(centerX - radius, centerY - radius, diameter, diameter, 0, 90);

        g2d.setColor(Color.GREEN);
        g2d.fillArc(centerX - radius, centerY - radius, diameter, diameter, 90, 90);

        g2d.setColor(Color.BLUE);
        g2d.fillArc(centerX - radius, centerY - radius, diameter, diameter, 180, 90);

        g2d.setColor(Color.YELLOW);
        g2d.fillArc(centerX - radius, centerY - radius, diameter, diameter, 270, 90);

        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(5));
        g2d.drawOval(centerX - radius, centerY - radius, diameter, diameter);
    }
}