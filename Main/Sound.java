package Main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;

public class Sound {
    File soundFile;
    AudioInputStream audioStream;
    Clip clip;
    private boolean playedOnce = false;

    public Sound(float level, String path) {
        playSound(path);
        setVolume(level);
    }
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


    public void playOnce() {
        if (!playedOnce) {
            play();
            playedOnce = true;
        }
    }

    public void resetPlayOnce() {
        playedOnce = false;
    }

    public void setVolume(float level) {
        if (clip != null) {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float range = gainControl.getMaximum() - gainControl.getMinimum();
            float gain = (range * level) + gainControl.getMinimum();
            gainControl.setValue(gain);
        } else {
            System.out.println("Error: Clip is null, cannot set volume.");
        }
    }

}
