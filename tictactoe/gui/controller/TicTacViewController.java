/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;
import javafx.scene.media.AudioClip;
import tictactoe.bll.GameBoard;
import tictactoe.bll.IGameModel;
import tictactoe.gui.TicTacToe;

/**
 * @author Anders, Daniel, Kasper og Nicklas
 **/
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
    private boolean winnerFound = false; //U only can win once until game restart
    @FXML
    private ImageView btnBackgroundMusicImg;



    @FXML
    private void onDragDetected(MouseEvent event) {
        clickedButton = (Button) event.getSource(); // Get the button that triggered the event
        draggedItem = clickedButton.getText(); // Get the text of the button
        System.out.println(tictoctacCounter);
        String draggedItemChecker = null;

        int player = game.getNextPlayer();
        System.out.println("Hvem spiller nu " + game.getNextPlayer());
        if (tictoctacCounter == 6 && !draggedItem.equals("")) { //If there is a item and all 6 is set

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


            if (targetButton.getText().isEmpty()) { // Check if the data is a string
            String draggedText = db.getString();  // Get the text from the clipboard
            targetButton.setText(draggedText); // Add the dragged text to the target button
            placement.play();
            clickedButton.setText(""); //Clear the text from the button there was dragged from
            setPlayer();
            success = true;
        }


        if (game.isGameOver())
        {
            int winner = game.getWinner();
            displayWinner(winner);
        }

        else if (TXT_PLAYER2.equals("Computer") && success) {
            makeComputerMove(); // AI's turn
        }

        event.setDropCompleted(success);
        event.consume();
    }

    /** This can be done more intelligent or stupid with some random **/

    private void makeComputerMove() {
        int oCount = 0;
        List<Button> oButtons = new ArrayList<>();
        List<Button> emptyCells = new ArrayList<>();

        for (Node node : gridPane.getChildren()) { //This code check where empty cells is and count O
            if (node instanceof Button) {
                Button button = (Button) node;
                if (button.getText().equals("O")) {
                    oCount++;
                    oButtons.add(button);
                } else if (button.getText().isEmpty()) {
                    emptyCells.add(button);
                }
            }
        }

        /** This can be done more intelligent sometimes it remove a
         * not strategic brick so it dont win if it can or let the player win**/
        if (oCount >= 3) { // Randomly choose one O to remove to make space for a new one
            Random rand = new Random();
            int randomIndex = rand.nextInt(Math.min(oCount, 3)); // Dont  index out of bounds
            oButtons.get(randomIndex).setText("");
        }

        // Check if the AI can win in the next move
        Button winningMove = findWinningMove(emptyCells, "O");
        if (winningMove != null) {
            int destRow = Optional.ofNullable(GridPane.getRowIndex(winningMove)).orElse(0);
            int destCol = Optional.ofNullable(GridPane.getColumnIndex(winningMove)).orElse(0);
            game.play(destCol, destRow);
            winningMove.setText("O");
        } else {
            // Check if the player is about to win and block the player
            Button blockingMove = findWinningMove(emptyCells, "X");
            if (blockingMove != null) {
                int destRow = Optional.ofNullable(GridPane.getRowIndex(blockingMove)).orElse(0);
                int destCol = Optional.ofNullable(GridPane.getColumnIndex(blockingMove)).orElse(0);
                game.play(destCol, destRow);
                blockingMove.setText("O");
            } else {
                // If no winning or blocking moves, make a random move from the empty cells
                Random rand = new Random();
                if (!emptyCells.isEmpty()) {
                    int randomIndex = rand.nextInt(emptyCells.size());
                    int destRow = Optional.ofNullable(GridPane.getRowIndex(emptyCells.get(randomIndex))).orElse(0);
                    int destCol = Optional.ofNullable(GridPane.getColumnIndex(emptyCells.get(randomIndex))).orElse(0);
                    game.play(destCol, destRow);
                    emptyCells.get(randomIndex).setText("O");
                }
            }

        }

        if (tictoctacCounter <= 5) {
            tictoctacCounter++;
            setPlayer();
        }

        if (tictoctacCounter == 6) {
            game.getNextPlayer();
        }

        if (game.isGameOver()) {
            int winner = game.getWinner();
            displayWinner(winner);
        }
    }


    private Button findWinningMove(List<Button> emptyCells, String marker) {
        for (Button cell : emptyCells) {
            // Temporarily set the marker to check for winning move for both player
            cell.setText(marker);

            if (game.isGameOver()) {
                // Reset the move and return the winning cell
                cell.setText("");
                return cell;
            }

            // Reset the move
            cell.setText("");
        }
        return null; // No winning move found
    }

    @FXML
    private void handleMuteUnmuteSound(ActionEvent event) { //Make it more general soundmanager? And it should start on the same as main menu
        if (placement.getVolume() == 0.0) {
            btnBackgroundMusicImg.setImage(new Image("tictactoe/gui/images/mute.png"));
            placement.setVolume(0.9); // Mute by setting the volume to 0.0
        } else {
            btnBackgroundMusicImg.setImage(new Image("tictactoe/gui/images/unmute.png"));
            placement.setVolume(0.0); // Unmute by setting the volume to the desired level (e.g., 0.9)
        }
    }


    @FXML
    private void handleButtonAction(ActionEvent event)
    {
        try
        {
            if (!winnerFound) {
                game.getNextPlayer();
                Integer row = GridPane.getRowIndex((Node) event.getSource());
                Integer col = GridPane.getColumnIndex((Node) event.getSource());
                int r = (row == null) ? 0 : row;
                int c = (col == null) ? 0 : col;
                int player = game.getNextPlayer();
                System.out.println(c + " " + r);

                if (game.play(c, r)) {
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
                    if (game.isGameOver()) {
                        game.getNextPlayer();
                        int winner = game.getWinner();
                        displayWinner(winner);
                    } else if (TXT_PLAYER2.equals("Computer") && tictoctacCounter < 6) {
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
        winnerFound  = false;
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
        tictoctacCounter = 10; //Disable drag and drop and placement

        if (!winnerFound) {
            if (winner == 0)
                winnerPlayer = TXT_PLAYER1;
            else
                winnerPlayer = TXT_PLAYER2;

            message = winnerPlayer + " wins!!!";
            }
            winnerFound = true;
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
