package view;

import controller.GameController;
import model.Chessboard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Begining extends JFrame {
   ChessGameFrame chessGameFrame;
   public Begining(){
        setTitle("斗兽棋");
        setSize(500,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBackground(Color.CYAN);
        setLayout(null);
        addBeginButton();
        addExitButton();
        addAIButton();

       ChessGameFrame chessGameFrame = new ChessGameFrame(1100, 810);
       GameController gameController = new GameController(chessGameFrame.getChessboardComponent(),new Chessboard());
       this.chessGameFrame = chessGameFrame;
       chessGameFrame.beginFrame=this;

        Image image = new ImageIcon("resource/背景图片1.jpg").getImage();
        image = image.getScaledInstance(500, 600,Image.SCALE_DEFAULT);
        ImageIcon icon = new ImageIcon(image);
        JLabel bg = new JLabel(icon);
        bg.setSize(500, 600);
        bg.setLocation(0, 0);
        add(bg);
    }
    private void addBeginButton(){
       JButton button=new JButton("START");
        button.setSize(120,60);
        button.setFont(new Font("方正舒体", Font.BOLD, 20));
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setLocation(190,150);
        add(button);
        button.addActionListener(e -> {
            button.setVisible(false);
            chessGameFrame.setLocation(750, 80);
            chessGameFrame.repaint();
            chessGameFrame.getChessboardComponent().initiateChessComponent(new Chessboard() );
            chessGameFrame.setVisible(true);
            chessGameFrame.beginFrame.dispose();
        });
    }
    private void addExitButton(){
       JButton button=new JButton("EXIT");
       button.setSize(120,60);
        button.setFont(new Font("方正舒体", Font.BOLD, 20));
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setLocation(190,450);
        add(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chessGameFrame.beginFrame.dispose();
            }
        });
    }
    private void addAIButton(){
        JButton button=new JButton("AI");
        button.setSize(120,60);
        button.setFont(new Font("方正舒体", Font.BOLD, 20));
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setLocation(190,300);
        add(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });

    }

}
