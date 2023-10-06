/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe.bll;

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
    public boolean isGameOver()
    {
        //TODO Implement this method
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
        return -1;
    }

    /**
     * Resets the game to a new game state.
     */
    public void newGame()
    {
        //TODO Implement this method
    }

}
