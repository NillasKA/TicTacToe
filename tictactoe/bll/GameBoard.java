/**
 * @author Anders, Daniel, Kasper og Nicklas
 **/
package tictactoe.bll;

import javafx.scene.control.Button;
import java.util.ArrayList;
import java.util.List;

public class GameBoard implements IGameModel
{
    private int nextPlayer;
    List<Object> scenario = new ArrayList<>();
    private final Button[] buttons;
    private static int xScore = 0;
    private static int oScore = 0;
    private int latestWinner = 1;

    public GameBoard(Button[] buttons) {
        this.buttons = buttons;
    }

    /**
     * Attempts to let the current player play at the given coordinates. It the
     * attempt is succesfull the current player has ended his turn and it is the
     * next players turn.
     *
     * @param col column to place a marker in.
     * @param row row to place a marker in.
     * @return true if the move is accepted, otherwise false. If gameOver == true
     * this method will always return false.
     */
    public boolean play(int col, int row)
    {
        String playedScenario = Integer.toString(col) + row; //Converts played field to a string
        if(!scenario.contains(playedScenario)){ // Checks if played field is in a list
            scenario.add(playedScenario);
            System.out.println(scenario);
            return true;}  //Allows the player to place their mark on the field.
        else
            return false;
    }

    /**
     * Resets the game to a new game state.
     */
    public void newGame()
    {
        scenario.clear(); // Clears the Scenario list
        if (latestWinner == 0) //Latest winner in  start first time it always X
            nextPlayer = 1;
        else
            nextPlayer = 0;
    }

    /*
     ********************* GETTERS *********************
     */
    /**
     * Returns 0 for player 0, 1 for player 1.
     *
     * @return int Id of the next player.
     */
    public int getNextPlayer()
    {
        if(nextPlayer == 0){
            nextPlayer = 1;
            return 0;
        }
        else{
            nextPlayer = 0;
            return 1;
        }
    }

    /**
     * @return int id of winner
     */

    public int getWinner()
    {
        if (nextPlayer== 0) {
            oScore++;
            latestWinner = 1;
        }
        else {
            xScore++;
            latestWinner = 0;
        }
        return nextPlayer;
    }

    /*
     ********************* WIN SECTION *********************
     */
    private final int[][] winConditions =
            {
                    {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // Rows
                    {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // Columns
                    {0, 4, 8}, {2, 4, 6} // Diagonals
            };

    /**
     * @return true if the game is over, else it will return false.
     */
    public boolean isGameOver()
    {
        // Check X win conditions
        for (int[] winCondition : winConditions) {
            if (buttons[winCondition[0]].getText().equals("X") &&
                buttons[winCondition[1]].getText().equals("X") &&
                buttons[winCondition[2]].getText().equals("X")) {

                return true;
            }
        }

        // Check O win conditions
        for (int[] winCondition : winConditions) {
            if (buttons[winCondition[0]].getText().equals("O") &&
                buttons[winCondition[1]].getText().equals("O") &&
                buttons[winCondition[2]].getText().equals("O")) {

                return true;
            }
        }

        return false;
    }


    /*
     ********************* SCORE SECTION *********************
     */
    public static String getXScore() {
        return Integer.toString(xScore);
    }
    public static String getOScore() {
        return Integer.toString(oScore);
    }

    public static void resetScore() {
        oScore = 0;
        xScore = 0;
    }
}