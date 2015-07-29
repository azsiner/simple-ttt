package com.siner.ttt.player;

import com.siner.ttt.game.GameForPlayer;

/**
 * Interface for a Tic-Tac-Toe player
 *
 * @author asiner
 */

public interface Player
{
    String getName();

    void setGame(GameForPlayer game, String gameStartMsg);

    void yourTurn();

    void gameOver(String msg);
}
