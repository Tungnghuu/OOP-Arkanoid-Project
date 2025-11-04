package app;

import java.net.URL;
import javax.sound.sampled.*;

public class SoundManager {
    Clip bgmClip;
    Clip sfxClip;
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

    public void playBGM(int i) {
        stopBGM();
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            bgmClip = AudioSystem.getClip();
            bgmClip.open(ais);
            bgmClip.loop(Clip.LOOP_CONTINUOUSLY);
            bgmClip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopBGM() {
        if (bgmClip != null) {
            if (bgmClip.isRunning()) bgmClip.stop();
            bgmClip.flush();
            bgmClip.close();
            bgmClip = null;
        }
    }

    public void playSFX(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            sfxClip = AudioSystem.getClip();
            sfxClip.open(ais);
            sfxClip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setVolume(float value) {
        setClipVolume(bgmClip, value);
        setClipVolume(sfxClip, value);
    }

    private void setClipVolume(Clip clip, float value) {
        if (clip == null)
            return;
        try {
            FloatControl gain = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            if (value < 0.0001f) value = 0.0001f;

            float dB = (float) (20.0 * Math.log10(value));
            if (dB < gain.getMinimum()) dB = gain.getMinimum();
            if (dB > gain.getMaximum()) dB = gain.getMaximum();

            gain.setValue(dB);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}