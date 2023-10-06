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
 *
 * @author Stegger
 */
public class TicTacToe extends Application
{
    private Stage primaryStage; // Declare a private Stage field


    public static void main(String[] args) {
        Application.launch();}
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage; // Set the primaryStage
        FXMLLoader loader = new FXMLLoader(getClass().getResource("views/TicTacMenuView.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Tic Tac Toe");
        primaryStage.centerOnScreen();
        primaryStage.getIcons().add(new Image("tictactoe/gui/images/icon.png"));
        TicTacMenuViewController ticTacMenuViewController = loader.getController(); //Reference
        ticTacMenuViewController.setParentController(this);
        primaryStage.show();
    }



    public void switchToView1() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("views/TicTacMenuView.fxml"));
        Parent root = loader.load();
        TicTacMenuViewController ticTacMenuViewController = loader.getController(); //Reference
        ticTacMenuViewController.setParentController(this);
        primaryStage.getScene().setRoot(root);
    }

    public void switchToView2() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("views/TicTacView.fxml"));
        Parent root = loader.load();
        TicTacViewController ticTacViewController = loader.getController(); //Reference
        ticTacViewController.setParentController(this);
        primaryStage.getScene().setRoot(root);
    }

}
