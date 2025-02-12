package view;


import model.PlayerColor;

import javax.swing.*;
import java.awt.*;


/**
 * This is the equivalent of the ChessPiece class,
 * but this class only cares how to draw Chess on ChessboardComponent
 */
public class ElephantChessComponent extends AnimalChessComponent {
//public class ElephantChessComponent extends JFrame{
    private PlayerColor owner;
    int size;
    private boolean selected;
    public ElephantChessComponent(PlayerColor owner, int size) {
        this.owner = owner;
        this.selected = false;
        setSize(size/2, size/2);
        setLocation(0,0);
        setVisible(true);
    }

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
      ImageIcon imageIcon = new ImageIcon("resource/红象.png");
      if (owner == PlayerColor.BLUE){
          imageIcon = new ImageIcon("resource/蓝象.png");
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

}  /*    @Override
    public void paint(Graphics graphics){
        graphics.drawImage(.ln,getWidth() / 4,getHeight() * 5 / 8,getWidth()/2,getWidth()/2,this);
    }
    public static void main(String[] args){
        ElephantChessComponent elephantChessComponent= new ElephantChessComponent();
        elephantChessComponent.add();
    }*/



