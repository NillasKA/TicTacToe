/**
 * @author Anders, Daniel, Kasper og Nicklas
 **/
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

public class SoundManager {
    private boolean muteAll = false;
    private final Map<String, AudioClip> soundMap = new HashMap<>();
    private final MediaPlayer backgroundMusic;
    private static SoundManager instance;


    //Make this object only get created one
    public static SoundManager getInstance() {
        if (instance == null) {
            instance = new SoundManager();
        }
        return instance;
    }


    // Initialize soundMap with their associated AudioClip instances + the music
    private SoundManager() {
        soundMap.put("placement", new AudioClip(Paths.get("gui/sounds/placementSound.mp3").toUri().toString()));
        soundMap.put("ui", new AudioClip(Paths.get("gui/sounds/uiSound.wav").toUri().toString()));
        backgroundMusic = new MediaPlayer(new Media(new File("gui/sounds/menuMainBackground.mp3").toURI().toString()));
    }


    // Start a sound fx. soundManager.startSound("name");
    public void startSound(String soundKey) {
        if (!muteAll) {
            AudioClip sound = soundMap.get(soundKey);
            if (sound != null) {
                sound.play();
            }
        }
    }


    /*
     ********************** MUSIC SECTION **********************
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


    /*
     ******************** MUTE/UNMUTE SECTION ********************
     */

    // Update picture when new window loaded
    public void muteUnmuteSoundUpdateImg(ImageView img) {
    if (!muteAll)
        img.setImage(new Image("tictactoe/gui/images/mute.png"));
    else
        img.setImage(new Image("tictactoe/gui/images/unmute.png"));
    }


    // Mute all sounds in the sound map
    public void muteUnmuteSound(ImageView img) {
        if (!muteAll) {
            for (AudioClip sound : soundMap.values()) {
                if (sound != null)
                    sound.setVolume(0.0);
            }
            img.setImage(new Image("tictactoe/gui/images/unmute.png"));
            backgroundMusic.setMute(true); // Mute
            muteAll = true;
        } else {
            for (AudioClip sound : soundMap.values()) {
                if (sound != null)
                    sound.setVolume(1.0);
            }
            img.setImage(new Image("tictactoe/gui/images/mute.png"));
            backgroundMusic.setMute(false); // unMute
            muteAll = false;
        }
    }
}