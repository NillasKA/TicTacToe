/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe.gui;

import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import tictactoe.gui.controller.TicTacMenuViewController;
import tictactoe.gui.controller.TicTacViewController;


/**
 * @author Stegger
 */
public class TicTacToe extends Application {
    private Stage primaryStage; // Declare a private Stage field
    private FXMLLoader menuLoader;

    public static void main(String[] args) {
        Application.launch();
    }

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

        // Set references
        gameController.setMenuController(menuController);
        menuController.setGameController(gameController);

        if (Choice == 0) { //First time setup
            Scene scene = new Scene(menuRoot);
            menuRoot.requestFocus(); //Nothing is marked as a start
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.setTitle("Tic Tac Toe");
            primaryStage.centerOnScreen();
            primaryStage.getIcons().add(new Image("tictactoe/gui/images/icon.png"));
            primaryStage.show();
        } else if (Choice == 1) { //Change to Menu window
            primaryStage.getScene().setRoot(menuRoot);
            menuRoot.requestFocus(); //Nothing is marked as a start
        } else if (Choice == 2) { //Change to Game window
            primaryStage.getScene().setRoot(gameRoot);
            gameRoot.requestFocus(); //Nothing is marked as a start
        } else {
            primaryStage.getScene().setRoot(menuRoot);

        }

    }


}