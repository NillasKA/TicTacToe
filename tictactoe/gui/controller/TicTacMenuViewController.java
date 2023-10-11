/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe.gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import tictactoe.bll.SoundManager;
import tictactoe.gui.TicTacToe;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
/**
 *
 * @author Stegger
 */
public class TicTacMenuViewController implements Initializable
{

    private SoundManager soundManager = new SoundManager();
    public HBox commonHeader;
    @FXML
    private StackPane stackPane;

    @FXML
    private BorderPane menuSetting, menuMain;

    @FXML
    private TextField txtPlayer1Name, txtPlayer2Name;

    @FXML
    private ImageView btnBackgroundMusicImg;

    private String player2Name , player1Name;



    TicTacViewController gameController;
    TicTacToe ticTacToe; //Controller


    @FXML
    private void handleNewGame(ActionEvent event) throws IOException {
        Button clickedButton = (Button) event.getSource(); // Get the button that triggered the event
        String buttonText = clickedButton.getText(); // Get the text of the button
        soundManager.startUISound();
        soundManager.stopMusic();

        if (txtPlayer1Name.getText().isEmpty())
            player1Name = "Player 1";
        else this.player1Name = txtPlayer1Name.getText();

        if (txtPlayer2Name.getText().isEmpty())
            player2Name = "Player 2";
        else this.player2Name = txtPlayer2Name.getText();

        if (buttonText.equals("1 Player"))  {
            gameController.setPlayerName(player1Name, "Computer") ;
        }
        if (buttonText.equals("2 Player"))  {
            gameController.setPlayerName(player1Name, player2Name) ;
        }
        ticTacToe.setWindowAndController(2); //Go to Game
    }

    public void handletxtfieldName(ActionEvent actionEvent) {
        System.out.println("12");
            if (txtPlayer1Name.getText().length() >= 20 ) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("Too long name");
                alert.setContentText("A name must only have 20 signs");
                alert.showAndWait();
            }
    }

    @FXML
    private void handleMuteUnmuteSound(ActionEvent event) {
        soundManager.muteUnmuteMusic(btnBackgroundMusicImg);
        soundManager.muteUnmuteUI(btnBackgroundMusicImg);
        System.out.println(soundManager.getMuteAll());
    }



    public void start()   {
        soundManager.startMusic();
        stackPane.getChildren().clear();
        stackPane.getChildren().add(menuMain);
        menuMain.setTop(commonHeader);
    }


    @Override
    public void initialize(URL url, ResourceBundle rb)
    {}

    @FXML
    private void handleMenuSettings(ActionEvent event) throws IOException { //Change view to settings menu
        stackPane.getChildren().clear();
        stackPane.getChildren().add(menuSetting);
        menuSetting.setTop(commonHeader); //We set the top so the use the same
        menuSetting.requestFocus(); //Nothing is marked as a start
        soundManager.startUISound();
    }
    @FXML
    public void handleMenuSettingsBack(ActionEvent actionEvent) { //Change view to main menu
        stackPane.getChildren().clear();
        stackPane.getChildren().add(menuMain);
        menuMain.setTop(commonHeader); //We set the top so the use the same
        menuMain.requestFocus(); //Nothing is marked as a start
        soundManager.startUISound();

      /**  if (txtPlayer1Name.getText().length() >= 20 || txtPlayer2Name.getText().length() >= 20 ) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Too long name");
            alert.setContentText("A name must only have 20 signs");
            alert.showAndWait();
        } **/
    }



    public void setParentController(TicTacToe controller) {ticTacToe = controller;} //Reference tools
    public void setGameController(TicTacViewController controller) {gameController = controller;} //Reference tools


}
