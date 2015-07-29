package com.siner.ttt.player;

import static com.siner.ttt.utils.MsgHandler.msg;

import com.siner.ttt.game.GameException;
import com.siner.ttt.game.GameForPlayer;

public abstract class AbstractPlayer implements Player
{
    private final String name;
    protected GameForPlayer myGame;

    protected AbstractPlayer(String name) {
        this.name = name;
    }

    @Override
    public void setGame(GameForPlayer game, String gameStartMsg) {
        this.myGame = game;
        output(gameStartMsg);
    }

    @Override
    public void yourTurn()
    {
        boolean goodMove = false;
        int squareNum = -1;

        while (! goodMove) {
            squareNum = getNextMove();

            try {
                goodMove = myGame.checkBox(this, squareNum);
            }
            catch (GameException e) {
                output( msg("bad-input", e.getMessage()) );
            }
        }
    }

    @Override
    public void gameOver(String msg)
    {
        // Print out snapshot of final screen;
        output(myGame.getScreenShot());
        output(msg);
        cleanup();
        this.myGame = null;
    }


    @Override
    public String getName()
    {
        return name;
    }

    /** Pick the next square number to play */
   protected abstract int getNextMove();

   /** Output message to the player */
   protected abstract void output(String msg);

   /** Do any post-game clean-up*/
   protected abstract void cleanup();

    @Override
    public String toString()
    {
        return name;
    }
}
