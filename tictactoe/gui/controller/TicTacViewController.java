/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;
import javafx.scene.media.AudioClip;
import tictactoe.bll.GameBoard;
import tictactoe.bll.IGameModel;
import tictactoe.gui.TicTacToe;

import javax.swing.*;

/**
 *
 * @author Stegger
 */
public class TicTacViewController implements Initializable
{
    AudioClip placement = new AudioClip(Paths.get("gui/sounds/placementSound.mp3").toUri().toString());
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

    private Button clickedButton;


    TicTacMenuViewController menuController;
    TicTacToe ticTacToe; //Controller
    private static String TXT_PLAYER1  = "Player 1", TXT_PLAYER2 = "Player 2";
    private String draggedItem;
    private int tictoctacCounter = 0;




    @FXML
    private void onDragDetected(MouseEvent event) {
        clickedButton = (Button) event.getSource(); // Get the button that triggered the event
        draggedItem = clickedButton.getText(); // Get the text of the button

        String draggedItemChecker = null;

        int player = game.getNextPlayer();
        System.out.println(game.getNextPlayer());
        if (tictoctacCounter >= 6 && !draggedItem.equals("")) { //If there is a item and all 6 is set

            if (player == 0) {
                draggedItemChecker = "X";
            }
            if (player == 1) {
                draggedItemChecker = "O";
            }


            if (draggedItemChecker.equals(draggedItem)) {
                placement.play();
                Dragboard db = clickedButton.startDragAndDrop(TransferMode.MOVE); //Gets the moved value
                ClipboardContent content = new ClipboardContent(); //Create a clipboard
                content.putString(draggedItem); //Put the item in the clipboard that mean its string
                db.setContent(content);
                event.consume();

            }
        }
    }


    @FXML
    private void onDragOver(DragEvent event) {
        if (event.getGestureSource() != event.getSource() && event.getDragboard().hasString()) {
            event.acceptTransferModes(TransferMode.MOVE);
        }
        event.consume();
    }

    @FXML
    private void onDragDropped(DragEvent event) {
        Button targetButton = (Button) event.getSource(); // Find out what button is the target of the drop
        Dragboard db = event.getDragboard(); // Get the data from the clipboard

        boolean success = false;

        if (targetButton.getText().isEmpty())   {

        if (db.hasString()) { // Check if the data is a string
            String draggedText = db.getString();  // Get the text from the clipboard
            targetButton.setText(draggedText); // Add the dragged text to the target button
            placement.play();
            // Clear the text from the source button (assuming sourceButton is another Button)
            clickedButton.setText("");
            setPlayer();
            success = true;
        }
        }

        if (game.isGameOver())
        {
            int winner = game.getWinner();
            displayWinner(winner);
        }

        event.setDropCompleted(success);
        event.consume();
    }



    private void makeComputerMove() { // Get a list of empty cells on the board
    List<Button> emptyCells = new ArrayList<>();
    for (Node node : gridPane.getChildren()) {
        if (node instanceof Button) {
            Button button = (Button) node;
            if (button.getText().isEmpty()) {
                emptyCells.add(button);
            }
        }
    }

    if (!emptyCells.isEmpty()) {  // Randomly select an empty cell and make the move
        Random random = new Random();
        int randomIndex = random.nextInt(emptyCells.size());
        Button computerMove = emptyCells.get(randomIndex);
        computerMove.setText("O"); // Computer is always 0
        tictoctacCounter++;
        setPlayer();

        if (tictoctacCounter == 6) {
            game.getNextPlayer();
        }
    }
}




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


                if (game.play(c, r)) {
                    if (game.isGameOver()) {
                        int winner = game.getWinner();
                        displayWinner(winner);
                    } else {


                        if (tictoctacCounter < 6) {
                            Button btn = (Button) event.getSource();
                            String xOrO = player == 0 ? "X" : "O";
                            tictoctacCounter++;
                            btn.setText(xOrO);
                            setPlayer();
                            placement.play();
                            if (tictoctacCounter == 6) {
                                game.getNextPlayer();
                            }
                        }
                        if (TXT_PLAYER2.equals("Computer")) {
                            makeComputerMove(); // AI's turn

                        }
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
        tictoctacCounter = 0;
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
        String winnerPlayer;

        switch (winner)
        {
            case -1:
                message = "It's a draw :-(";
                break;
            default:
                if (winner == 0)
                    winnerPlayer = TXT_PLAYER1;
                else
                    winnerPlayer = TXT_PLAYER2;

                message = winnerPlayer + " wins!!!";
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
