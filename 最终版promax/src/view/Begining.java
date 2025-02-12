package view;

import controller.GameController;
import model.Chessboard;
import controller.CountdownTimer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Begining extends JFrame {
   ChessGameFrame chessGameFrame;
    addMusic addmusic=new addMusic();
   public Begining(){
        setTitle("王炸斗兽棋promax");
        setSize(500,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBackground(Color.CYAN);
        setLayout(null);
        addBeginButton();
        addExitButton();
        addAIButton();


       // new AudioPlayer();

       ChessGameFrame chessGameFrame = new ChessGameFrame(1100, 810);
       GameController gameController = new GameController(chessGameFrame.getChessboardComponent(),new Chessboard());
       this.chessGameFrame = chessGameFrame;
       chessGameFrame.beginFrame=this;

        Image image = new ImageIcon("resource/游戏主界面.jpg").getImage();
        image = image.getScaledInstance(500, 600,Image.SCALE_DEFAULT);
        ImageIcon icon = new ImageIcon(image);
        JLabel bg = new JLabel(icon);
        bg.setSize(500, 600);
        bg.setLocation(0, 0);
        add(bg);
    }


    private void addBeginButton(){

       ImageIcon icon = new ImageIcon("resource/START.png");
        final JButton button=new JButton();
        button.setSize(120,66);
        //icon = icon.getImage().getScaledInstance(button.getWidth(), button.getHeight(), icon.getImage().SCALE_DEFAULT);
       Image image=icon.getImage();
       Image image1= image.getScaledInstance(button.getWidth(),button.getHeight(), Image.SCALE_FAST);
       icon=new ImageIcon(image1);
        button.setIcon(icon);

        button.setFont(new Font("方正舒体", Font.BOLD, 20));
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setLocation(190,150);
        //button.setSize(null);
        add(button);
        button.addActionListener(e -> {
           // chessGameFrame.statusLabel0.setVisible(true);
          //  chessGameFrame.statusLabel0.setLocation(HEIGHT,HEIGHT/10+50);
            this.setVisible(false);
            CountdownTimer.time = 30;
            if (GameController.timer == null){
                GameController.timer = new CountdownTimer(chessGameFrame.getChessboardComponent().gameController);
                GameController.timer.start();
            }
            chessGameFrame.setLocation(120, 80);
            chessGameFrame.repaint();
        //    chessGameFrame.getChessboardComponent().initiateChessComponent(new Chessboard() );
            chessGameFrame.setVisible(true);
            chessGameFrame.beginFrame.dispose();
            chessGameFrame.timeLabel.setVisible(true);
            chessGameFrame.getChessboardComponent().gameController.restart();
           // addmusic.stopMusic();
        });
    }
    private void addExitButton(){
       JButton button=new JButton();
        button.setSize(120,66);
        ImageIcon icon = new ImageIcon("resource/EXIT.png");
        Image image=icon.getImage();
        Image image1= image.getScaledInstance(button.getWidth(),button.getHeight(), Image.SCALE_FAST);
        icon=new ImageIcon(image1);
       button.setIcon(icon);
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

        JButton button=new JButton();
        button.setSize(120,66);
        ImageIcon icon = new ImageIcon("resource/AI.png");
        Image image=icon.getImage();
        Image image1= image.getScaledInstance(button.getWidth(),button.getHeight(), Image.SCALE_FAST);
        icon=new ImageIcon(image1);
        button.setIcon(icon);
        button.setFont(new Font("方正舒体", Font.BOLD, 20));
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setLocation(190,300);
        add(button);

        button.addActionListener(e -> {
                chessGameFrame.getChessboardComponent().gameController.AIPlaying = true;
            this.setVisible(false);
            CountdownTimer.time = 30;
            if (GameController.timer == null){
                GameController.timer = new CountdownTimer(chessGameFrame.getChessboardComponent().gameController);
                GameController.timer.start();
            }
            chessGameFrame.setLocation(120, 80);
            chessGameFrame.repaint();
            //    chessGameFrame.getChessboardComponent().initiateChessComponent(new Chessboard() );
            chessGameFrame.setVisible(true);
            chessGameFrame.beginFrame.dispose();
            chessGameFrame.timeLabel.setVisible(true);
            chessGameFrame.getChessboardComponent().gameController.restart();
            // addmusic.stopMusic();
               // addmusic.stopMusic();
              //  addmusic.playMusic("resource/BGM3.wav");
            });
        }

    }


