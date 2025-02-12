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
    JLabel background;
    public final JLabel BG;
    private final int ONE_CHESS_SIZE;
    public GameController gameController;
    private ChessboardComponent chessboardComponent;

    public ChessGameFrame(int width, int height) {
        setTitle("斗兽棋"); //设置标题
        this.WIDTH = width;
        this.HEIGHT = height;
        this.ONE_CHESS_SIZE = (HEIGHT * 4 / 5) / 9;
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);
        addChessboard();
        addHelloButton();
        addRegretButton();
        addMUSICButton();
        addLoadButton();
        addSaveButton();
        addLabel();
        addBackButton();
        Image image = new ImageIcon("resource/背景图片.jpg").getImage();
        image = image.getScaledInstance(1100, 810,Image.SCALE_DEFAULT);
        ImageIcon icon = new ImageIcon(image);
        BG = new JLabel(icon);
        BG.setSize(1100, 810);
        BG.setLocation(0, 0);
        background=BG;
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
            chessboardComponent = new ChessboardComponent(ONE_CHESS_SIZE);
            chessboardComponent.setLocation(HEIGHT / 5, HEIGHT / 10);
            add(chessboardComponent);
        }

        /**
         * 在游戏面板中添加标签
         */
        private void addLabel () {

            JLabel statusLabel = new JLabel("游戏中");
            statusLabel.setLocation(HEIGHT, HEIGHT / 10);
            statusLabel.setSize(200, 60);
            statusLabel.setFont(new Font("方正舒体", Font.BOLD, 30));
            add(statusLabel);
            statusLabel.setForeground(Color.WHITE);
            JLabel statusLabel1 = new JLabel("红方回合");
            JLabel statusLabel2 = new JLabel("蓝方回合");
            statusLabel1.setForeground(Color.RED);
            statusLabel1.setFont(new Font("方正舒体", Font.BOLD, 30));
            statusLabel2.setForeground(Color.BLUE);
            statusLabel2.setFont(new Font("方正舒体", Font.BOLD, 30));
           /** for (int t=0;t<=1000;t++){
        if (){
            statusLabel1.setLocation(HEIGHT,HEIGHT/10+60);
            add(statusLabel);
        }else{
            statusLabel2.setLocation(HEIGHT,HEIGHT/10+60);
            add(statusLabel2);
        }
        remove(statusLabel2);
        remove(statusLabel1);
        }*/
        }

        /**
         * 在游戏面板中增加一个按钮，如果按下的话就会显示Hello, world!
         */

        public void addHelloButton () {
        //    this.gameController=gameController;
            JButton button = new JButton("开始游戏");
            button.addActionListener((e) -> JOptionPane.showMessageDialog(this, "游戏开始！！"));
            button.setLocation(HEIGHT, HEIGHT / 10 + 120);
            button.setSize(200, 60);
            button.setFont(new Font("方正舒体", Font.BOLD, 30));
            button.setBackground(Color.WHITE);
            button.setForeground(Color.PINK);
            add(button);
           // button.addActionListener(e -> {

           // });
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    gameController.restart();
            }
        });
        }

        private void addRegretButton () {
            JButton button = new JButton("悔棋");
            button.setLocation(HEIGHT, HEIGHT / 10 + 240);
            button.setSize(200, 60);
            button.setFont(new Font("方正舒体", Font.BOLD, 30));
            button.setBackground(Color.WHITE);
            button.setForeground(Color.GREEN);
            add(button);
            button.addActionListener(e -> {
            });
        }

    /*private void restart() {
            model.initGrid();
        model.initPieces();
        view.removeChessComponent();
        view.initiateChessComponent(model);
        view.repaint();
        view.revalidate();
        currentPlayer = PlayerColor.BLUE;
        selectedPoint = null;
        winner = null;
        model.redDead = new ArrayList<>();
        model.blueDead = new ArrayList<>();
    }*/


    private void addMUSICButton () {
            int radius = 50;
            JButton button = new JButton("音乐");
            button.setLocation(HEIGHT, HEIGHT / 10 + 600);
            button.setFont(new Font("方正舒体", Font.BOLD, 12));
            button.setSize(radius + 20, radius);
            button.setBackground(Color.WHITE);
            button.setForeground(Color.BLUE);
            add(button);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new addMusic("");
                }
            });
    }
    private void addBackButton(){
        int radius = 50;
        JButton button = new JButton("返回");
        button.setLocation(HEIGHT+130, HEIGHT / 10 + 600);
        button.setFont(new Font("方正舒体", Font.BOLD, 12));
        button.setSize(radius + 20, radius);
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLUE);
        add(button);
        button.addActionListener(e -> {
            System.out.println("Click back");
            this.setVisible(false);
            beginFrame.setVisible(true);
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
            JButton button = new JButton("Load");
            button.setLocation(HEIGHT, HEIGHT / 10 + 360);
            button.setSize(200, 60);
            button.setFont(new Font("方正舒体", Font.BOLD, 30));
            button.setBackground(Color.WHITE);
            button.setForeground(Color.RED);
            add(button);
            button.addActionListener(e -> {
                System.out.println("Click load");
                String path = JOptionPane.showInputDialog(this, "Input Path here");
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
        private void addSaveButton () {
            JButton button = new JButton("Save");
            button.setLocation(HEIGHT, HEIGHT / 10 + 480);
            button.setSize(200, 60);
            button.setFont(new Font("方正舒体", Font.BOLD, 30));
            button.setBackground(Color.WHITE);
            button.setForeground(Color.BLUE);
            add(button);
            button.addActionListener((e) -> {
                JOptionPane.showMessageDialog(this, "保存成功！！");
                saveFileToDocuments();
            });
        }


        }


