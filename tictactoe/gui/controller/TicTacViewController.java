/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import tictactoe.bll.GameBoard;
import tictactoe.bll.IGameModel;
import tictactoe.gui.TicTacToe;

/**
 *
 * @author Stegger
 */
public class TicTacViewController implements Initializable
{

    @FXML
    private Label lblPlayer;

    @FXML
    private Button btnNewGame;

    @FXML
    private GridPane gridPane;
    @FXML
    private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;

    //private IGameModel game;
    private IGameModel game = new GameBoard(new Button[]{btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9});




    TicTacMenuViewController menuController;
    TicTacToe ticTacToe; //Controller
    private static String TXT_PLAYER1  = "Player 1", TXT_PLAYER2 = "Player 2";

    private IGameModel game;


    @FXML
    private void handleButtonAction(ActionEvent event)
    {
        try
        {
            game.getNextPlayer();
            Integer row = GridPane.getRowIndex((Node) event.getSource());
            Integer col = GridPane.getColumnIndex((Node) event.getSource());
            int r = (row == null) ? 0 : row;
            int c = (col == null) ? 0 : col;
            int player = game.getNextPlayer();
            System.out.println(c + " " + r);
            if (game.play(c, r))
            {
                if (game.isGameOver())
                {
                    int winner = game.getWinner();
                    displayWinner(winner);
                }
                else
                {
                    Button btn = (Button) event.getSource();
                    String xOrO = player == 0 ? "X" : "O";
                    btn.setText(xOrO);
                    setPlayer();

                }
            }
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

     @FXML
     private void handleNewGame(ActionEvent event)
    {
        game.newGame();
        setPlayer();
        clearBoard();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        Button[] buttonsArray = {btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9};
        game = new GameBoard(buttonsArray);
        setPlayer();
    }

    private void setPlayer()
    {
        if (game.getNextPlayer() == 0)
        lblPlayer.setText(TXT_PLAYER1);
        else
            lblPlayer.setText(TXT_PLAYER2);
    }

    private void displayWinner(int winner)
    {
        String message = "";
        switch (winner)
        {
            case -1:
                message = "It's a draw :-(";
                break;
            default:
                message = "Player " + winner + " wins!!!";
                break;
        }
        lblPlayer.setText(message);
    }



    public void setPlayerName(String player1, String player2)  {
        this.TXT_PLAYER1 = player1;
        this.TXT_PLAYER2 = player2;
        //Add something there remove all space and add 1

}


    private void clearBoard()
    {
        for(Node n : gridPane.getChildren())
        {
            Button btn = (Button) n;
            btn.setText("");
        }
    }

    public void handleMainMenu(ActionEvent actionEvent) throws IOException {
        ticTacToe.setWindowAndController(1); //Go to Game
    }

    public void setParentController(TicTacToe controller) {ticTacToe = controller;} //Reference tools
    public void setMenuController(TicTacMenuViewController controller) {menuController = controller;}
}
