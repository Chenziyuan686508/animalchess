package view;

import controller.GameController;
import model.Constant;
import model.PlayerColor;

import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;



/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class ChessGameFrame extends JFrame {
    //    public final Dimension FRAME_SIZE ;
    public Begining beginFrame;
    private final int WIDTH;
    private final int HEIGHT;
    public JLabel background;
    public JLabel statusLabel;
    public JLabel timeLabel;
    public final JLabel BG2;
    public final JLabel BG1;
    private final int ONE_CHESS_SIZE;
    addMusic addmusic=new addMusic();

    public PlayerColor currentplayer;
    private ChessboardComponent chessboardComponent;
    public PlayerColor playerColor;

    public ChessGameFrame(int width, int height) {
        setTitle("王炸斗兽棋 最强无敌版"); //设置标题
        this.WIDTH = width;
        this.HEIGHT = height;
        this.ONE_CHESS_SIZE = (HEIGHT * 4 / 5) / 9;
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);
        addTimeLabel();
        addStatusLabel();
        addChessboard();
        addHelloButton();
        addRegretButton();
        addMUSICButton();
        addLoadButton();
        addSaveButton();
        addLabel();
        addBackButton();
        addReplayButton();
        addChangeButton();

        //addmusic.playMusic("resource/BGM2.wav");
        //addmusic.stopMusic();
        //addmusic.playMusic("resource/BGM2.wav");

        Image mage = new ImageIcon("resource/游戏里的背景2.jpg").getImage();
        mage = mage.getScaledInstance(1100, 810,Image.SCALE_DEFAULT);
        ImageIcon con = new ImageIcon(mage);
        BG1 = new JLabel(con);
        BG1.setSize(1100, 810);
        BG1.setLocation(0, 0);

        Image age = new ImageIcon("resource/游戏里的背景1.jpg").getImage();
        age = age.getScaledInstance(1100, 810,Image.SCALE_DEFAULT);
        ImageIcon on = new ImageIcon(age);
        BG2 = new JLabel(on);
        BG2.setSize(1100, 810);
        BG2.setLocation(0, 0);

        background=BG2;
        add(background);
    //    addchessGameFrame();
     //   repaint();
     //   addlabel();
    }
    /*public void addlabel(){
            JFrame jf = new JFrame();
            ImageIcon bg = new ImageIcon("resource/背景图片.jpg");
            JLabel label = new JLabel(bg);
            label.setSize(bg.getIconWidth(), bg.getIconHeight());
            jf.getLayeredPane().add(label, new Integer[Integer.MIN_VALUE]);
            JPanel panel = (JPanel) jf.getContentPane();
            panel.setOpaque(false);
            panel.setLayout(new FlowLayout());
            jf.setSize(bg.getIconWidth(),bg.getIconHeight());
            jf.setVisible(true);
            jf.add(label);
   }*/

    /*    @Override
        public void paint(Graphics graphics){
            graphics.drawImage(viewutils.ln,0,0,WIDTH,HEIGHT,this);
        }
        public void (){
            ChessGameFrame chessGameFrame=new ChessGameFrame(0,0);
            add(chessGameFrame);
        }*/

        public ChessboardComponent getChessboardComponent () {
            return chessboardComponent;
        }

        public void setChessboardComponent (ChessboardComponent chessboardComponent){
            this.chessboardComponent = chessboardComponent;
        }

        /**
         * 在游戏面板中添加棋盘
         */
        private void addChessboard () {
            chessboardComponent = new ChessboardComponent(ONE_CHESS_SIZE,statusLabel,timeLabel);
            chessboardComponent.setLocation(HEIGHT / 5, HEIGHT / 10);
            add(chessboardComponent);
        }

        /**
         * 在游戏面板中添加标签
         */
        public void addLabel () {
            JLabel statusLabel1 = new JLabel("游戏中");
            statusLabel1.setLocation(HEIGHT - 646, HEIGHT / 30);
            statusLabel1.setForeground(Color.getHSBColor(3,6,96));
            statusLabel1.setSize(200, 60);
            statusLabel1.setFont(new Font("方正舒体", Font.BOLD, 30));
            statusLabel1.setVisible(true);
            add(statusLabel1);}
            //}
       /* public void addtimeLabel(){
            timeLabel=new JLabel("时间"+time)
        }*/
        public void addStatusLabel(){
            statusLabel=new JLabel("蓝方回合1");
            statusLabel.setForeground(Color.BLUE);
            statusLabel.setLocation(HEIGHT - 500 , HEIGHT / 30);
            statusLabel.setSize(200, 60);
            statusLabel.setFont(new Font("方正舒体", Font.BOLD, 30));
            add(statusLabel);

        }
        public void addTimeLabel(){
            timeLabel = new JLabel("剩余时间: 30");
            timeLabel.setForeground(Color.CYAN);
            timeLabel.setLocation(HEIGHT - 300, HEIGHT / 30);
            timeLabel.setSize(200, 60);
            timeLabel.setFont(new Font("方正舒体", Font.BOLD, 30));
            add(timeLabel);
        }
        /*if ( playerColor==PlayerColor.BLUE ){
            remove(statusLabel);
        }else{
            remove(statusLabel2);
        }*/


        /**
         * 在游戏面板中增加一个按钮，如果按下的话就会显示Hello, world!
         */

        public void addHelloButton () {
        //    this.gameController=gameController;
            JButton button = new JButton();
            button.setSize(200, 60);
            ImageIcon icon = new ImageIcon("resource/开始游戏1.png");
            Image image=icon.getImage();
            Image image1= image.getScaledInstance(button.getWidth(),button.getHeight(), Image.SCALE_FAST);
            icon=new ImageIcon(image1);
            button.setIcon(icon);
            button.addActionListener((e) -> JOptionPane.showMessageDialog(this, "游戏开始！！"));
            button.setLocation(HEIGHT, HEIGHT / 10 + 80);
            button.setFont(new Font("方正舒体", Font.BOLD, 30));
            button.setBackground(Color.WHITE);
            button.setForeground(Color.PINK);
            add(button);
            button.addActionListener(e -> {
                chessboardComponent.gameController.restart();
                //statusLabel.add(chessboardComponent,0);
            });
            //button.addActionListener(new ActionListener() {
           //     @Override
           //     public void actionPerformed(ActionEvent e) {
            //        gameController.restart();
                 //   JOptionPane.showMessageDialog(chessboardComponent, "游戏开始！！");
       //     }
       // });
        }
    private void addRegretButton () {
        JButton button = new JButton();
        button.setSize(200, 60);
        ImageIcon icon = new ImageIcon("resource/悔棋.png");
        Image image=icon.getImage();
        Image image1= image.getScaledInstance(button.getWidth(),button.getHeight(), Image.SCALE_FAST);
        icon=new ImageIcon(image1);
        button.setIcon(icon);
        button.setLocation(HEIGHT, HEIGHT / 10 + 160);
        button.setFont(new Font("方正舒体", Font.BOLD, 30));
        button.setBackground(Color.WHITE);
        button.setForeground(Color.GREEN);
        add(button);
        button.addActionListener(e -> {
            chessboardComponent.gameController.regret();
        });
    }
    private void addReplayButton () {
        JButton button = new JButton();
        button.setSize(200, 60);
        ImageIcon icon = new ImageIcon("resource/棋局回放.png");
        Image image=icon.getImage();
        Image image1= image.getScaledInstance(button.getWidth(),button.getHeight(), Image.SCALE_FAST);
        icon=new ImageIcon(image1);
        button.setIcon(icon);
        button.setLocation(HEIGHT, HEIGHT / 10 + 240);
        button.setFont(new Font("方正舒体", Font.BOLD, 30));
        button.setBackground(Color.WHITE);
        button.setForeground(Color.GREEN);
        add(button);
        button.addActionListener(e -> {
            chessboardComponent.gameController.playback();
        });
    }

    private void addMUSICButton () {
        addmusic.playMusic("resource/BGM2.wav");
            int radius = 50;
            JButton button = new JButton();
        button.setSize(radius + 20, radius);
        ImageIcon icon = new ImageIcon("resource/音乐.png");
        Image image=icon.getImage();
        Image image1= image.getScaledInstance(button.getWidth(),button.getHeight(), Image.SCALE_FAST);
        icon=new ImageIcon(image1);
        button.setIcon(icon);
            button.setLocation(HEIGHT, HEIGHT / 10 + 600);
            button.setFont(new Font("方正舒体", Font.BOLD, 12));
            button.setBackground(Color.WHITE);
            button.setForeground(Color.BLUE);
            add(button);
          button.addActionListener(e -> {
              addmusic.stopMusic();
              addmusic.playMusic("resource/BGM1.wav");
            });
            }

    private void addBackButton(){
        int radius = 50;
        JButton button = new JButton();
        button.setSize(radius + 20, radius);
        ImageIcon icon = new ImageIcon("resource/返回.png");
        Image image=icon.getImage();
        Image image1= image.getScaledInstance(button.getWidth(),button.getHeight(), Image.SCALE_FAST);
        icon=new ImageIcon(image1);
        button.setIcon(icon);
        button.setLocation(HEIGHT+130, HEIGHT / 10 + 600);
        button.setFont(new Font("方正舒体", Font.BOLD, 12));
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLUE);
        add(button);
        button.addActionListener(e -> {
            System.out.println("Click back");
            this.setVisible(false);
            beginFrame.setVisible(true);  addmusic.stopMusic();
            chessboardComponent.gameController.restart();
            addmusic.stopMusic();
            addmusic.playMusic("resource/BGM2.wav");
        });
    }

        /*  button.addActionListener(new ActionListener() {
          @Override
            public void actionPerformed(ActionEvent e) {});
        }button.addActionListener(e -> {
                clickCount++;

                if (clickCount % 2 == 1) {
                    // Play music
                    try {
                        // Load the music file
                        File musicFile = new File("path/to/music/file.wav");
                        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(musicFile);

                        // Get the clip from the audio input stream
                        clip = AudioSystem.getClip();
                        clip.open(audioInputStream);

                        // Start playing the music
                        clip.start();

                        System.out.println("Playing music");
                    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                        System.out.println("Error playing music: " + ex.getMessage());
                    }
                } else {
                    // Pause music
                    if (clip != null && clip.isRunning()) {
                        clip.stop();
                        System.out.println("Pausing music");
                    }
                }
            });*/

        private void addLoadButton () {
            JButton button = new JButton();
            button.setSize(200, 60);
            ImageIcon icon = new ImageIcon("resource/加载.png");
            Image image=icon.getImage();
            Image image1= image.getScaledInstance(button.getWidth(),button.getHeight(), Image.SCALE_FAST);
            icon=new ImageIcon(image1);
            button.setIcon(icon);
            button.setLocation(HEIGHT, HEIGHT / 10 + 320);
            button.setFont(new Font("方正舒体", Font.BOLD, 30));
            button.setBackground(Color.WHITE);
            button.setForeground(Color.RED);
            add(button);
            button.addActionListener(e -> {
                if (chessboardComponent.gameController.loadGame()) {
                    new LoadFrame();
                }
                System.out.println("Click load");
                String path = "祝你生活愉快！";
                // Find and process file in "Document" folder
                try {
                    String documentsFolderPath = System.getProperty("user.region") + "/Documents/";
                    File documentsFolder = new File(documentsFolderPath);
                    File targetFile = new File(documentsFolder, path);
                    if (targetFile.exists() && targetFile.isFile()) {
                        // Read file and process it
                        BufferedReader reader = new BufferedReader(new FileReader(targetFile));
                        String line;
                        while ((line = reader.readLine()) != null) {
                            // Process each line of the file
                            System.out.println(line);
                            // Perform necessary operations with the line
                            }
                        reader.close();
                    } else {
                        System.out.println("File not found: " + path);
                    }
                } catch (IOException ex) {
                    System.out.println("Error reading file: " + ex.getMessage());
                }
            });
        }
        /*public class FileLoaderExample extends JFrame {
            private JButton loadButton;

            public FileLoaderExample() {
                loadButton = new JButton("LoadButton");
                loadButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        JFileChooser fileChooser = new JFileChooser();
                        int option = fileChooser.showOpenDialog(FileLoaderExample.this);
                        if (option == JFileChooser.APPROVE_OPTION) {
                            File selectedFile = fileChooser.getSelectedFile();
                            String filePath = selectedFile.getAbsolutePath();

                            if (selectedFile.exists()) {
                                try {
                                    FileReader fileReader = new FileReader(filePath);
                                    BufferedReader bufferedReader = new BufferedReader(fileReader);

                                    // 读取文件内容或执行其他操作
                                    String line;
                                    while ((line = bufferedReader.readLine()) != null) {
                                        System.out.println(line);
                                    }

                                    bufferedReader.close();
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                            } else {
                                JOptionPane.showMessageDialog(FileLoaderExample.this, "File does not exist!");
                            }
                        }
                    }
                });

                add(loadButton);
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                pack();
                setVisible(true);
            }

            public static void main(String[] args) {
                new FileReader("");
            }
        }*/


        private void saveFileToDocuments() {
                String content = ""; // 替换为要保存的文件内容
                String targetFolderPath = System.getProperty("region") + "/Documents/"; // "Document"文件夹的路径
                String fileName = "saved_file.txt"; // 保存的文件名
                File targetFolder = new File(targetFolderPath);

                // 创建文件并写入内容
              try{  FileWriter writer = new FileWriter(targetFolderPath);
                writer.write(content);
                writer.close();
                System.out.println("结果已保存至"+targetFolderPath);
            } catch (IOException ex) {
                System.out.println("保存文件时出现错误");
            }
        }
    private void addSaveButton() {
        JButton button = new JButton();
        button.setSize(200, 60);
        ImageIcon icon = new ImageIcon("resource/保存.png");
        Image image=icon.getImage();
        Image image1= image.getScaledInstance(button.getWidth(),button.getHeight(), Image.SCALE_FAST);
        icon=new ImageIcon(image1);
        button.setIcon(icon);
        button.setLocation(HEIGHT, HEIGHT / 10 + 400);
        button.setFont(new Font("方正舒体", Font.BOLD, 30));
        button.setBackground(Color.WHITE);
        button.setForeground(Color.RED);
        add(button);
        button.addActionListener(e -> {
            System.out.println("Click save");
            String path = JOptionPane.showInputDialog("存档名");
            while (path.equals("Document")){
                JOptionPane.showMessageDialog(null, "存档名不能为空");
                path = JOptionPane.showInputDialog("存档名");
            }
            chessboardComponent.gameController.saveGame(path);
            new LoadFrame();
        });
    }
    private void addChangeButton() {
        JButton button = new JButton();
        button.setSize(200, 60);
        ImageIcon icon = new ImageIcon("resource/更换背景.png");
        Image image=icon.getImage();
        Image image1= image.getScaledInstance(button.getWidth(),button.getHeight(), Image.SCALE_FAST);
        icon=new ImageIcon(image1);
        button.setIcon(icon);
        button.setLocation(HEIGHT, HEIGHT / 10 + 480);
        button.setFont(new Font("方正舒体", Font.BOLD, 30));
        button.setBackground(Color.WHITE);
        button.setForeground(Color.RED);
        add(button);
        button.addActionListener(e -> {
            System.out.println("Click change theme");
            if(background == BG1){
                remove(background);
                background = BG2;
                add(background);
            }
            else {
                remove(background);
                background = BG1;
                add(background);
            }
            repaint();
            revalidate();
        });
    }
}


