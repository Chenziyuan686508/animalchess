package view;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.io.File;
public class addMusic {
    private final String file;//输入音乐的路径
    JButton stopPlayMusic = new JButton ("StopMusic");//按键后停止播放
    public addMusic(String file){
        this.file = file;
    }
    public void playMusic(String musicName) {
        try {
            File playPath = new File(file + musicName);
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

}
