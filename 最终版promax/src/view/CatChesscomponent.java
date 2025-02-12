package view;

import model.PlayerColor;

import javax.swing.*;
import java.awt.*;

public class CatChesscomponent extends AnimalChessComponent {
    private PlayerColor owner;


    private boolean selected;

    public CatChesscomponent(PlayerColor owner, int size ) {

        this.owner = owner;
        this.selected = false;
        setSize(size/2, size/2);
        setLocation(0,0);
        setVisible(true);
        repaint();
    }
    public boolean isSelected() {
        return selected;
    }

    public void setSelected
            (boolean selected) {
        this.selected = selected;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        /*g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Font font = new Font("楷体", Font.PLAIN, getWidth() / 2);
        g2.setFont(font);
        g2.setColor(owner.getColor());
        g2.drawString("猫", getWidth() / 4, getHeight() *5/7); // FIXME: Use library to find the correct offset.*/
        ImageIcon imageIcon = new ImageIcon("resource/红猫.png");
        if (owner == PlayerColor.BLUE){
            imageIcon = new ImageIcon("resource/蓝猫.png");
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

