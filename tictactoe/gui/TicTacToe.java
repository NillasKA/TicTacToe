/**
 * @author Anders, Daniel, Kasper og Nicklas
 **/
package tictactoe.gui;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import tictactoe.bll.SoundManager;
import tictactoe.gui.controller.TicTacMenuViewController;
import tictactoe.gui.controller.TicTacViewController;

public class TicTacToe extends Application {
    private Stage primaryStage; // Declare a private Stage field


    public static void main(String[] args) {
        Application.launch();
    }
    SoundManager soundManager = SoundManager.getInstance();
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage; // Set the primaryStage
        setWindowAndController(0); //First time setup
    }

    public void setWindowAndController(int Choice) throws IOException {
        // Load the menu controller
        FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("views/TicTacMenuView.fxml"));
        Parent menuRoot = menuLoader.load();
        TicTacMenuViewController menuController = menuLoader.getController();
        menuController.setParentController(this);

        // Load the game controller
        FXMLLoader gameLoader = new FXMLLoader(getClass().getResource("views/TicTacView.fxml"));
        Parent gameRoot = gameLoader.load();
        TicTacViewController gameController = gameLoader.getController();
        gameController.setParentController(this);

        // Set references for controller
        gameController.setMenuController(menuController);
        menuController.setGameController(gameController);
        menuController.setSoundManager(soundManager);
        gameController.setSoundManager(soundManager);

        if (Choice == 0) { //First time setup
            Scene scene = new Scene(menuRoot);
            menuRoot.requestFocus(); //Nothing is marked as a start
            menuController.start(); //Method to startup runs
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.setTitle("Tic Tac Toe");
            primaryStage.centerOnScreen();
            primaryStage.getIcons().add(new Image("tictactoe/gui/images/icon.png"));
            primaryStage.show();
        } else if (Choice == 1) { //Change to Menu window
            primaryStage.getScene().setRoot(menuRoot);
            menuRoot.requestFocus(); //Nothing is marked as a start
            menuController.start(); //Method to startup runs
        }
        else if (Choice == 2)   { //Change to Game window
            primaryStage.getScene().setRoot(gameRoot);
            gameRoot.requestFocus(); //Nothing is marked as a start
            gameController.start(); //Method to startup runs
        } else {
            primaryStage.getScene().setRoot(menuRoot);

        }
    }
}