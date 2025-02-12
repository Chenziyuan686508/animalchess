package view;

import model.PlayerColor;

import javax.swing.*;
import java.awt.*;

public class RatChesscomponent extends AnimalChessComponent{
    private PlayerColor owner;

    private boolean selected;

    public RatChesscomponent(PlayerColor owner, int size) {
        this.owner = owner;
        this.selected = false;
        setSize(size/2, size/2);
        setLocation(0,0);
        setVisible(true);}
        // repaint();

    public boolean isSelected() {
        return selected;
    }
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        ImageIcon imageIcon = new ImageIcon("resource/红鼠.png");
        if (owner == PlayerColor.BLUE){
            imageIcon = new ImageIcon("resource/蓝鼠.png");
        }
        Image image = imageIcon.getImage();
        imageIcon = new ImageIcon(image.getScaledInstance(getWidth(),getHeight(),Image.SCALE_FAST));
        g2.drawImage(image,0,0,getWidth(),getHeight(),null,null);
        repaint();
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.GREEN);
            g.drawOval(0, 0, getWidth() , getHeight());

        }
    }
}

