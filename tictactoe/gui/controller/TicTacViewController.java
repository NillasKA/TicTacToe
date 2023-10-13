/**
 * @author Anders, Daniel, Kasper og Nicklas
 **/
package tictactoe.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;
import tictactoe.bll.GameBoard;
import tictactoe.bll.IGameModel;
import tictactoe.bll.SoundManager;
import tictactoe.gui.TicTacToe;

public class TicTacViewController implements Initializable
{
    @FXML
    private Label lblPlayer, lblXScore, lblOScore;
    @FXML
    private GridPane gridPane;
    @FXML
    private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;

    //private IGameModel game;
    private IGameModel game = new GameBoard(new Button[]{btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9});
    private Button clickedButton;
    private TicTacMenuViewController menuController; //Controller
    private TicTacToe ticTacToe; //Controller
    private SoundManager soundManager; //Controller
    private static String TXT_PLAYER1  = "Player 1", TXT_PLAYER2 = "Player 2";
    private int tictoctacCounter = 0;
    private boolean winnerFound = false; //U only can win once until game restart
    @FXML
    private ImageView btnBackgroundMusicImg;
    private boolean lockBotFroMStart = false;


    /*
     ******************** DRAG/DROP SECTION ********************
     */
    @FXML
    private void onDragDetected(MouseEvent event) {
        clickedButton = (Button) event.getSource(); // Get the button that triggered the event
        String draggedItem = clickedButton.getText(); // Get the text of the button
        System.out.println(tictoctacCounter);
        String draggedItemChecker = null;

        int player = game.getNextPlayer();
        System.out.println("Current player : " + game.getNextPlayer());
        if (tictoctacCounter == 6 && !draggedItem.isEmpty()) { //If there is an item and all 6 is set

            if (player == 0) {
                draggedItemChecker = "X";
            }
            if (player == 1) {
                draggedItemChecker = "O";
            }


            if (draggedItemChecker.equals(draggedItem)) {
                soundManager.startSound("placement");
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
            soundManager.startSound("placement");
            // Clear the text from the source button (assuming sourceButton is another Button)
            clickedButton.setText("");
            setPlayer();
            success = true;
        }

        if (game.isGameOver())
        {
            int winner = game.getWinner();
            if (winner == 1) {
                lockBotFroMStart = true; //Player win but player should always start in 1 player mode
            }

            displayWinner(winner);
        }

        else if (TXT_PLAYER2.equals("Computer") && success) {
            makeComputerMove(); // AI's turn
        }

        event.setDropCompleted(success);
        event.consume();
    }


    /*
     ******************** BOT SECTION ********************
     */

    /** This can be done more intelligent or stupid with some random **/
    private void makeComputerMove() {
        int oCount = 0;
        List<Button> oButtons = new ArrayList<>();
        List<Button> emptyCells = new ArrayList<>();

        for (Node node : gridPane.getChildren()) {
            if (node instanceof Button button) {
                if (button.getText().equals("O")) {
                    oCount++;
                    oButtons.add(button);
                } else if (button.getText().isEmpty()) {
                    emptyCells.add(button);
                }
            }
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

        if (oCount >= 3) {
            // Step 1: Find O markers with blocking potential
            List<Button> blockingOButtons = findOsWithBlockingPotential(oButtons, emptyCells);

            // Step 2: Check if there are "O" markers that are not part of the blocking list
            List<Button> nonBlockingOButtons = new ArrayList<>(oButtons);
            nonBlockingOButtons.removeAll(blockingOButtons);

            Random rand = new Random();
            int randomIndex;

            // Step 3: Remove an "O" marker that is not part of the blocking list if available
            if (!nonBlockingOButtons.isEmpty()) {
                randomIndex = rand.nextInt(nonBlockingOButtons.size());
                nonBlockingOButtons.get(randomIndex).setText("");
            } else {
                // Step 4: If there are no non-blocking "O" markers, remove a random "O"
                randomIndex = rand.nextInt(oButtons.size());
                oButtons.get(randomIndex).setText("");
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


    private boolean playerIsAboutToWin(String marker) {
        for (Node node : gridPane.getChildren()) {
            if (node instanceof Button) {
                Button button = (Button) node;
                if (button.getText().isEmpty()) {
                    String originalText = button.getText(); // Store the original text

                    // Temporarily set the empty cell to the marker and check if the player is about to win
                    button.setText(marker);
                    if (game.isGameOver()) {
                        // Reset the cell back to its original text
                        button.setText(originalText);
                        return true;
                    }

                    // Reset the cell back to its original text for the next iteration
                    button.setText(originalText);
                }
            }
        }
        return false; // No winning move found
    }


    List<Button> blockingOButtons = new ArrayList<>();
    private List<Button> findOsWithBlockingPotential(List<Button> oButtons, List<Button> emptyCells) {

        for (Button oButton : oButtons) {
            if (oButton.getText().equals("O")) {
                // Temporarily set the empty cells to "X" and check if the player is about to win
                String originalText = oButton.getText(); // Store the original text
                oButton.setText(""); // Temporarily remove the "O"

                for (Button emptyCell : emptyCells) {
                    if (emptyCell.getText().isEmpty()) {
                        emptyCell.setText("X");
                        if (playerIsAboutToWin("X")) {
                            // Player is about to win, so set this "O" to prevent it
                            blockingOButtons.add(emptyCell);
                        }
                        emptyCell.setText(""); // Reset the cell
                    }
                }

                // Reset the original "O"
                oButton.setText(originalText);
            }
        }

        return blockingOButtons;
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


    /*
     ******************** OTHER SECTION ********************
     */
    @FXML
    private void handleButtonAction(ActionEvent event) {
        try {
            if (!winnerFound) {
                game.getNextPlayer(); //Checks for next player
                Integer row = GridPane.getRowIndex((Node) event.getSource()); //Finds the rows for the board
                Integer col = GridPane.getColumnIndex((Node) event.getSource()); //Finds the columns for the board
                int r = (row == null) ? 0 : row;
                int c = (col == null) ? 0 : col;
                int player = game.getNextPlayer(); // Assigns "Player" to the next player

                if (game.play(c, r)) {
                    if (tictoctacCounter < 6) {
                        Button btn = (Button) event.getSource();
                        String xOrO = player == 0 ? "X" : "O";
                        tictoctacCounter++;
                        btn.setText(xOrO);
                        setPlayer();
                        soundManager.startSound("placement");
                        if (tictoctacCounter == 6) {
                            game.getNextPlayer();
                        }
                    }
                    if (game.isGameOver()) {
                        game.getNextPlayer();
                        int winner = game.getWinner();
                        if (winner == 1) {
                            lockBotFroMStart = true; //Player win but player should always start in 1 player mode
                        }
                        displayWinner(winner);
                    } else if (TXT_PLAYER2.equals("Computer") && tictoctacCounter < 6) {
                        makeComputerMove(); // AI's turn
                    }
                }
            }
            } catch(Exception e)
            {
                System.out.println(e.getMessage());
            }
        }


     @FXML
     private void handleNewGame(ActionEvent event)
    {
        soundManager.startSound("ui");
        game.newGame();
        setPlayer();
        clearBoard();
        tictoctacCounter = 0;
        winnerFound  = false;

        if (lockBotFroMStart && TXT_PLAYER2.equals("Computer")) {
            setPlayer();
            lockBotFroMStart = false;
        }

    }


    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        Button[] buttonsArray = {btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9}; //Array meant to give gameboard access to buttons
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
            if (winner == 1) {
                winnerPlayer = TXT_PLAYER1;

            }
            else {
                winnerPlayer = TXT_PLAYER2;
            }

            lblXScore.setText(GameBoard.getXScore());
            lblOScore.setText(GameBoard.getOScore());

            message = winnerPlayer + " wins!!!";
            }
            winnerFound = true;
            lblPlayer.setText(message);
    }


    public void setPlayerName(String player1, String player2)  {
        this.TXT_PLAYER1 = player1;
        this.TXT_PLAYER2 = player2;
    }


    private void clearBoard()
    {
        for(Node n : gridPane.getChildren())
        {
            Button btn = (Button) n;
            btn.setText("");
        }
    }


    public void start() { //1 time setup
        clearBoard();
        GameBoard.resetScore();
        soundManager.muteUnmuteSoundUpdateImg(btnBackgroundMusicImg);
    }


    public void handleMainMenu(ActionEvent actionEvent) throws IOException {
        ticTacToe.setWindowAndController(1); //Go to Game
        soundManager.startSound("ui");
    }


    @FXML //Mute/Unmute button
    private void handleMuteUnmuteSound(ActionEvent event) {
        soundManager.muteUnmuteSound (btnBackgroundMusicImg);
    }


    public void setParentController(TicTacToe controller) {ticTacToe = controller;} //Reference tools
    public void setMenuController(TicTacMenuViewController controller) {menuController = controller;} //Reference tools
    public void setSoundManager(SoundManager soundManager) {this.soundManager = soundManager;} //Reference tools
}