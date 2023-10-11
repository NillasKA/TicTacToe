package tictactoe.bll;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.nio.file.Paths;

public class SoundManager {
    AudioClip placementSound = new AudioClip(Paths.get("gui/sounds/placementSound.mp3").toUri().toString());
    AudioClip uiSound = new AudioClip(Paths.get("gui/sounds/uiSound.wav").toUri().toString());
    MediaPlayer backgroundMusic = new MediaPlayer(new Media(new File("gui/sounds/menuMainBackground.mp3").toURI().toString()));


    /*
     ******************** MUSIC SECTION ********************
     */
    public void startMusic() {
        backgroundMusic.setCycleCount(MediaPlayer.INDEFINITE);
        backgroundMusic.play();
        backgroundMusic.setVolume(0.3);
    }


    public void stopMusic() {
        backgroundMusic.stop();
    }

    //Handles muting and unmuting sound, as well as replacing the mute and unmute picture.
    public void muteUnmuteMusic(ImageView img) {
        if (backgroundMusic.isMute()) {
            img.setImage(new Image("tictactoe/gui/images/mute.png"));
            backgroundMusic.setMute(false); // Unmute
        } else {
            img.setImage(new Image("tictactoe/gui/images/unmute.png"));
            backgroundMusic.setMute(true); // Mute
        }
    }

    /*
     ******************** PLACEMENT SECTION ********************
     */

    public void startPlacement() {
        placementSound.play();
    }

    public void muteUnmutePlacement(ImageView img) {
        if (placementSound.getVolume() == 0.0) {
            img.setImage(new Image("tictactoe/gui/images/mute.png"));
            placementSound.setVolume(0.9); // Unmute by setting the volume to the desired level (e.g., 0.9)
        } else {
            img.setImage(new Image("tictactoe/gui/images/unmute.png"));
            placementSound.setVolume(0.0); // Mute by setting the volume to 0.0
        }
    }


    /*
     ******************** BUTTON CLICK SECTION ********************
     */

    public void startUISound() {
        uiSound.play();
    }

    public void muteUnmuteUI(ImageView img) {
        if (uiSound.getVolume() == 0.0) {
            img.setImage(new Image("tictactoe/gui/images/mute.png"));
            uiSound.setVolume(0.9); // Unmute by setting the volume to the desired level (e.g., 0.9)
        } else {
            img.setImage(new Image("tictactoe/gui/images/unmute.png"));
            uiSound.setVolume(0.0); // Mute by setting the volume to 0.0
        }
    }
}