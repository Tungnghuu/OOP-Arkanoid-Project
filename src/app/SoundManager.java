package app;

<<<<<<< HEAD
=======
// import java.net.URI;
>>>>>>> 203eb359ee12691195911728a97c851371129747
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundManager {
    Clip clip;
    URL soundURL[] = new URL[30];

    public SoundManager() {
        soundURL[0] = getClass().getResource("/assets/sounds/earth-converted.wav");
        soundURL[1] = getClass().getResource("/assets/sounds/lose-converted.wav");
        soundURL[2] = getClass().getResource("/assets/sounds/neon-converted.wav");
        soundURL[3] = getClass().getResource("/assets/sounds/space-converted.wav");
        soundURL[4] = getClass().getResource("/assets/sounds/underwater-converted.wav");
        soundURL[5] = getClass().getResource("/assets/sounds/volcano-converted.wav");
        soundURL[6] = getClass().getResource("/assets/sounds/win-converted.wav");
        soundURL[7] = getClass().getResource("/assets/sounds/paddle_hit-converted.wav");
        soundURL[8] = getClass().getResource("/assets/sounds/brick_hit-converted.wav");
        soundURL[9] = getClass().getResource("/assets/sounds/wall_hit-converted.wav");
    }

    public void setFile(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (Exception e) {
            
        }
    }

    public void play() {
        clip.start();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        clip.stop();
    }
}