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
import javafx.scene.control.Button;
import tictactoe.gui.TicTacToe;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 *
 * @author Stegger
 */
public class TicTacMenuViewController implements Initializable
{

    @FXML
    private Button btnNewGame;

    TicTacViewController gameController;
    TicTacToe ticTacToe; //Controller

    @FXML
    private void handleNewGame(ActionEvent event) throws IOException {
        gameController.setPlayerName("Test") ;
        ticTacToe.setWindowAndController(2); //Go to Game
    }

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {

    }

    public void setParentController(TicTacToe controller) {ticTacToe = controller;} //Reference tools
    public void setGameController(TicTacViewController controller) {gameController = controller;}
}
