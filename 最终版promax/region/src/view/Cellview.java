package view;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;


public class Cellview extends JPanel {
    public Color background;
    int size;
    public boolean canStep;
    public boolean mouseAt;
    public CellType type;

    public Cellview(Point location, int size, CellType type) {
        setLayout(new GridLayout(1, 2));
        setLocation(location);
        setSize(size, size);
        this.size = size;
        this.type = type;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponents(g);
        if (type == CellType.TRAP) {g.setColor(Color.PINK);}
        else if (type == CellType.CAVE) {g.setColor(Color.WHITE);}
        else if (type== CellType.RIVER) {g.setColor(Color.CYAN);}
        else g.setColor(background);
        g.fillRect(1, 1, this.getWidth() - 1, this.getHeight() - 1);

        if (canStep) { // Highlights the model if selected.
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(new Color(255, 253, 87, 150));
            RoundRectangle2D roundedRectangle = new RoundRectangle2D.Double(1, 1,
                    this.getWidth() - 1, this.getHeight() - 1, size / 4, size / 4);
            g2d.fill(roundedRectangle);
        }

        if (mouseAt){
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(new Color(255, 172, 155, 120));
            RoundRectangle2D roundedRectangle = new RoundRectangle2D.Double(1, 1,
                    this.getWidth() - 1, this.getHeight() - 1, size / 4, size / 4);
            g2d.fill(roundedRectangle);
        }
    }

}
