package app;

// import java.net.URI;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundManager {
    Clip clip;
    URL soundURL[] = new URL[30];

    public SoundManager() {
        soundURL[0] = getClass().getResource("/assets/sounds/earth.wav");
        soundURL[1] = getClass().getResource("/assets/sounds/lose.wav");
        soundURL[2] = getClass().getResource("/assets/sounds/neon.wav");
        soundURL[3] = getClass().getResource("/assets/sounds/space.wav");
        soundURL[4] = getClass().getResource("/assets/sounds/underwater.wav");
        soundURL[5] = getClass().getResource("/assets/sounds/volcano.wav");
        soundURL[6] = getClass().getResource("/assets/sounds/win.wav");
    }

    public void setFile(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (Exception e) {
            // TODO: handle exception
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
