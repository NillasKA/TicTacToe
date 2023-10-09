/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe.bll;
import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Stegger
 */
public class GameBoard implements IGameModel
{
    private int nextPlayer;
    List scenario = new ArrayList<>();
    private Button[] buttons;

    public GameBoard(Button[] buttons) {
        this.buttons = buttons;
    }
    private boolean isDraw = false;

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
        String playedScenario = Integer.toString(col) + row;
        if(!scenario.contains(playedScenario)){
            scenario.add(playedScenario);
            System.out.println(scenario);
            return true;}
        else
            return false;
    }

    /**
     * Tells us if the game has ended either by draw or by meeting the winning
     * condition.
     *
     * @return true if the game is over, else it will return false.
     */
    public boolean isGameOver()
    {
        // Check X win conditions
        for (int[] winCondition : winConditions) {
            if (buttons[winCondition[0]].getText().equals("X") &&
                buttons[winCondition[1]].getText().equals("X") &&
                buttons[winCondition[2]].getText().equals("X")) {

                xWins(winCondition[0], winCondition[1], winCondition[2]);
                return true;
            }
        }

        // Check O win conditions
        for (int[] winCondition : winConditions) {
            if (buttons[winCondition[0]].getText().equals("O") &&
                buttons[winCondition[1]].getText().equals("O") &&
                buttons[winCondition[2]].getText().equals("O")) {

                oWins(winCondition[0], winCondition[1], winCondition[2]);
                return true;
            }
        }
            if (scenario.size() == buttons.length* buttons.length)
            {
            isDraw = true;
            System.out.println("DRAW");
            return true;
            }

        return false;
    }

    /**
     * Gets the id of the winner, -1 if its a draw.
     *
     * @return int id of winner, or -1 if draw.
     */

    public int getWinner()
    {
        //TODO Implement this method
        if (isDraw == true){
            return -1;
        }
        return  nextPlayer == 0 ? 1 : 0;
    }

    /**
     * Resets the game to a new game state.
     */
    public void newGame()
    {
        scenario.clear(); // Clears the Scenario list
        nextPlayer = 0;
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
}
