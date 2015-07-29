package com.siner.ttt.game;

import com.siner.ttt.player.Player;

/**  Game interface used by Players */
public interface GameForPlayer
{
    /** Returns the indices of the free squares */
    int[] getFreeSquares();

    /** Returns a Stringified snapshot of the current game */
    String getScreenShot();

    /** Checks the selected box of the TTT board.
     *  Returns true if this move caused the game to end.
     */
    boolean checkBox(Player p, int squareNum) throws GameException;
}
