package tictactoe.bll;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Anders, Daniel, Kasper og Nicklas
 **/

public class SoundManager {

    private boolean muteAll = false;

    private Map<String, AudioClip> soundMap = new HashMap<>();
    private MediaPlayer backgroundMusic;

    private static SoundManager instance;

    public boolean getMuteAll(){
        return muteAll;
    }

    //Make this object only get created one
    public static SoundManager getInstance() {
        if (instance == null) {
            instance = new SoundManager();
        }
        return instance;
    }

    private SoundManager() {
        // Initialize soundMap with keys and their associated AudioClip instances
        soundMap.put("placement", new AudioClip(Paths.get("gui/sounds/placementSound.mp3").toUri().toString()));
        soundMap.put("ui", new AudioClip(Paths.get("gui/sounds/uiSound.wav").toUri().toString()));

        backgroundMusic = new MediaPlayer(new Media(new File("gui/sounds/menuMainBackground.mp3").toURI().toString()));
    }

    // Start a sound based on the provided key
    public void startSound(String soundKey) {
        if (!muteAll) {
            AudioClip sound = soundMap.get(soundKey);
            if (sound != null) {
                sound.play();
            }
        }
    }



    /*
     ******************** MUSIC SECTION ********************
     */
    public void startMusic() {
        if(!muteAll){
            backgroundMusic.setCycleCount(MediaPlayer.INDEFINITE);
            backgroundMusic.play();
            backgroundMusic.setVolume(0.3);
        }
        else{
            backgroundMusic.play();
            backgroundMusic.setMute(true);
        }

    }
    public void stopMusic() {
        backgroundMusic.stop();
    }


    /*
     ******************** MUTe / UNMUTE SECTION ********************
     */

    // Update picture when new window loaed
    public void muteUnmuteSoundUpdateImg(ImageView img) {
    if (!muteAll == true)
        img.setImage(new Image("tictactoe/gui/images/mute.png"));
    else
        img.setImage(new Image("tictactoe/gui/images/unmute.png"));
    }

    // Mute all sounds in the sound map
    public void muteUnmuteSound(ImageView img) {

        if (!muteAll == true) {
            for (AudioClip sound : soundMap.values()) {
                if (sound != null) {
                    sound.setVolume(0.0);
                }
            }
            img.setImage(new Image("tictactoe/gui/images/unmute.png"));
            backgroundMusic.setMute(true); // Mute
            muteAll = true;
        } else {
            for (AudioClip sound : soundMap.values()) {
                if (sound != null) {
                    sound.setVolume(1.0);
                }
            }
            img.setImage(new Image("tictactoe/gui/images/mute.png"));
            backgroundMusic.setMute(false); // unMute
            muteAll = false;
        }
    }

}