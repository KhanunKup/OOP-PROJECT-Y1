package Main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class Sound {
    File soundFile;
    AudioInputStream audioStream;
    Clip clip;
    public void playSound(String filePath){
        try{
            soundFile = new File(filePath);
            audioStream = AudioSystem.getAudioInputStream(soundFile);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void play(){
        if (clip != null) {
            clip.setFramePosition(0);
            clip.start();
        }
    }
    public void loop(){
        if (clip != null) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }
    public void stop(){
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }
}
