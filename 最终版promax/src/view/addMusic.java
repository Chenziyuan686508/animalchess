package view;
import javax.sound.sampled.*;
import javax.swing.*;
import java.io.File;
import java.security.PrivateKey;


public class addMusic {
    public Clip clip;
        public void playMusic(String musicLocation) {
            try { File musicPath = new File(musicLocation);
                if (musicPath.exists()) {
                    AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                     clip = AudioSystem.getClip();
                                clip.open(audioInput);
                                clip.start();
                                clip.loop(Clip.LOOP_CONTINUOUSLY);
                        }
                } catch (Exception ex) {
                        ex.printStackTrace();
                }
        }
        public void stopMusic() {
            //File musicPath = new File(musicLocation);
            // if (musicPath.exists()) {
            // AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                //Clip clip = AudioSystem.getClip();
               //  Clip clip=AudioSystem.getClip();
                if( clip.isOpen()){
                    clip.stop();
                    clip.close();
                }


                // AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                //clip = AudioSystem.getClip();
                // clip.open(audioInput);
            }


        }

        /*// 获取音频输入流
        AudioInputStream inputStream = AudioSystem.getAudioInputStream(addMusic.class.getResourceAsStream("resource/BGM3.wav"));
        // 获取音频格式
        AudioFormat format = inputStream.getFormat();
        // 创建数据行信息对象
        DataLine.Info info = new DataLine.Info(Clip.class, format);
        // 获取数据行
        Clip clip = (Clip) AudioSystem.getLine(info);
        // 打开数据行
        clip.open(inputStream);
        // 循环播放
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        // 播放音频
        clip.start();
        } catch (Exception e) {
        e.printStackTrace();
        }
        }*/

/*public class addMusic {
    private final String file;
    //.JButton stopPlayMusic = new JButton ("StopMusic");//按键后停止播放
    public addMusic(String file){
        this.file = file;
    }
    public void playMusic1(String musicName) {
        try {
            File playPath = new File("resource/BGM1.mp3");
            if (playPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(playPath);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                System.out.println("没有找到播放路径");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void playMusic2(String musicName){
        try {
            File playPath = new File("resource/BGM2.mp3");
            if (playPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(playPath);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                System.out.println("没有找到播放路径");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}*/
