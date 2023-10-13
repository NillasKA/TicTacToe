/**
 * @author Anders, Daniel, Kasper og Nicklas
 **/
package tictactoe.gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import tictactoe.bll.SoundManager;
import tictactoe.gui.TicTacToe;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TicTacMenuViewController implements Initializable
{


    public HBox commonHeader;
    @FXML
    private StackPane stackPane;

    @FXML
    private BorderPane menuSetting, menuMain;

    @FXML
    private TextField txtPlayer1Name, txtPlayer2Name;

    @FXML
    private ImageView btnBackgroundMusicImg;

    private TicTacViewController gameController; //Controller
    private TicTacToe ticTacToe; //Controller
    private SoundManager soundManager; //Controller

    @Override
    public void initialize(URL url, ResourceBundle rb) {}

    @FXML
    private void handleNewGame(ActionEvent event) throws IOException {
        Button clickedButton = (Button) event.getSource(); // Get the button that triggered the event
        String buttonText = clickedButton.getText(); // Get the text of the button
        soundManager.startSound("ui");

        String player1Name = txtPlayer1Name.getText().isEmpty() ? "Player 1" : txtPlayer1Name.getText();
        String player2Name = txtPlayer2Name.getText().isEmpty() ? "Player 2" : txtPlayer2Name.getText();

        if (buttonText.equals("1 Player"))  {
            gameController.setPlayerName(player1Name, "Computer") ;
        }
        if (buttonText.equals("2 Player"))  {
            gameController.setPlayerName(player1Name, player2Name) ;
        }
        ticTacToe.setWindowAndController(2); //Go to Game
    }


    @FXML //Mute/Unmute button
    private void handleMuteUnmuteSound(ActionEvent event) {
        soundManager.muteUnmuteSound(btnBackgroundMusicImg);
    }

    public void start()   { //1 time setup
        soundManager.startMusic();
        newMenuView(menuMain);
        soundManager.muteUnmuteSoundUpdateImg(btnBackgroundMusicImg);
    }

    public void newMenuView (BorderPane  viewName)  { //Universal ways to change borderpane
        stackPane.getChildren().clear(); //Remove all from before
        stackPane.getChildren().add(viewName); //We set selected view
        viewName.setTop(commonHeader); //We set the top so the use the same
        viewName.requestFocus(); //Nothing is marked as a start
    }

    @FXML
    private void handleMenuSettings(ActionEvent event) throws IOException { //Change view to settings menu
        newMenuView(menuSetting);
        soundManager.startSound("ui");
    }
    @FXML
    public void handleMenuSettingsBack(ActionEvent actionEvent) { //Change view to main menu
        //↓ Check name is not too long
        if (txtPlayer1Name.getText().length() >= 20 || txtPlayer2Name.getText().length() >= 20) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            if (txtPlayer1Name.getText().length() >= 20 && txtPlayer2Name.getText().length() >= 20) {
                alert.setHeaderText("Both names are too long");
            } else if (txtPlayer1Name.getText().length() >= 20) {
                alert.setHeaderText("Player 1's name is too long");
            } else {
                alert.setHeaderText("Player 2's name is too long");
            }
            alert.setContentText("A name must only contains 20 characters");
            alert.showAndWait();
            soundManager.startSound("ui");
        }
        else newMenuView(menuMain); //Change view to main menu
    }

    public void setParentController(TicTacToe controller) {ticTacToe = controller;} //Reference tools
    public void setGameController(TicTacViewController controller) {gameController = controller;} //Reference tools
    public void setSoundManager(SoundManager soundManager) {this.soundManager = soundManager;} //Reference tools
}