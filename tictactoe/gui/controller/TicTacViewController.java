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

    TicTacMenuViewController menuController;
    TicTacToe ticTacToe; //Controller
    private static String TXT_PLAYER = "Player: ";
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

                    check();
                }
            }
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    @FXML
    private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;
    @FXML
    public void check() {
        Button[] buttons = {btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9};

        // Check X win conditions
        for (int[] winCondition : winConditions)
        {
            if (buttons[winCondition[0]].getText().equals("X") &&
                buttons[winCondition[1]].getText().equals("X") &&
                buttons[winCondition[2]].getText().equals("X"))
            {
                xWins(winCondition[0], winCondition[1], winCondition[2]);
                return;
            }
        }

        // Check O win conditions
        for (int[] winCondition : winConditions)
        {
            if (buttons[winCondition[0]].getText().equals("O") &&
                buttons[winCondition[1]].getText().equals("O") &&
                buttons[winCondition[2]].getText().equals("O"))
            {
                oWins(winCondition[0], winCondition[1], winCondition[2]);
                return;
            }
        }
    }

    private int[][] winConditions =
            {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // Rows
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // Columns
            {0, 4, 8}, {2, 4, 6} // Diagonals
            };
    public void xWins(int index1, int index2, int index3) {
        System.out.println("X WINS");
        // Change to boolean, if true, new window.
    }

    public void oWins(int index1, int index2, int index3) {
        System.out.println("O Wins");
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

        game = new GameBoard();
        setPlayer();
    }

    private void setPlayer()
    {
        lblPlayer.setText(TXT_PLAYER + game.getNextPlayer());
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



    public void setPlayerName(String player1)  {
        TXT_PLAYER = player1 + " ";
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
