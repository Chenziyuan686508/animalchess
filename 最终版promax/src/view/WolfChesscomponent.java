package view;

import model.Cell;
import model.ChessboardPoint;
import model.PlayerColor;

import javax.swing.*;
import java.awt.*;

import static javax.swing.text.StyleConstants.setIcon;

public class WolfChesscomponent extends AnimalChessComponent {
    private PlayerColor owner;

    private boolean selected;

    public WolfChesscomponent(PlayerColor owner, int size) {
        this.owner = owner;
        this.selected = false;
        setSize(size/2, size/2);
        setLocation(0,0);
        setVisible(true);
        // repaint();
    }
    public boolean isSelected() {
        return selected;
    }
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    @Override
    protected void paintComponent(Graphics g) {
       // ImageIcon icon = new ImageIcon("resource/狼.png");
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
       //Image image=icon.getImage();
       // Image image1= image.getScaledInstance(.getWidth(),.getHeight(), Image.SCALE_FAST);
      //  icon=new ImageIcon(image1);
      //  g2.setIcon(icon);
       ImageIcon imageIcon = new ImageIcon("resource/红狼.png");
        if (owner == PlayerColor.BLUE){
            imageIcon = new ImageIcon("resource/蓝狼.png");
        }
        Image image = imageIcon.getImage();
        imageIcon = new ImageIcon(image.getScaledInstance(getWidth(),getHeight(),Image.SCALE_FAST));
        g2.drawImage(image,0,0,getWidth(),getHeight(),null,null);
        repaint();
        /*JLabel label = new JLabel(pic);
        //label.setSize(size,size);
        label.setVisible(true);
        label.setHorizontalAlignment(JLabel.CENTER);
        add(label);*/
       // label.setRequestFocusEnabled(true);

        //label.setSize(size, size);
        //bgLabel.setLocation(0, 0);
        /*g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Font font = new Font("楷体", Font.PLAIN, getWidth() / 2);
        g2.setFont(font);
        g2.setColor(owner.getColor());
        g2.drawString("狼", getWidth() / 4, getHeight() *5/7); // FIXME: Use library to find the correct offset.*/
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.GREEN);
            g.drawOval(0, 0, getWidth() , getHeight());
        }
    }
}


