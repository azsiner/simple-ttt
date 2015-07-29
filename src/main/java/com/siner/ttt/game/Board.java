package com.siner.ttt.game;

import java.util.Optional;


/**
 * Represents a Tic-Tac-Toe board
 *
 * @author asiner
 */
public interface Board
{
    /**
     * Sets the square as checked, and determines if the game is over
     * @param squareNum
     * @param mark
     * @return true/false to show if game is over
     *
     * @throws GameException if a square is out-of-bounds or already taken
     */
    boolean checkBox(int squareNum, MarkType mark);

    /**
     * Returns the winner, if there is none.
     * @return Optional.empty() is the game is not yet over.
     *
     * If no one won the completed game, Optional.of(NO_MARK) is returned.
     *
     * Otherwise Optional contains X_MARK or O_MARK.
     */
    Optional<MarkType> getWinner();


    /**
     * Returns the marktype for the given square
     * @param squareNum
     * @return
     */
    MarkType getMarkType(int squareNum);

    /**
     * Returns the indices of the free squares
     * @return indices of the free squares
     */
    public int[] getFreeSquares();
}
