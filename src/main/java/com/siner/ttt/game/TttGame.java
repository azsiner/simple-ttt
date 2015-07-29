package com.siner.ttt.game;

import static com.siner.ttt.game.MarkType.O_MARK;
import static com.siner.ttt.game.MarkType.X_MARK;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.log4j.Logger;

import com.siner.ttt.player.Player;
import com.siner.ttt.utils.MsgHandler;

/**
 * Represents one instance of a Tic-Tac-Toe game.  It has two Players and a Board.
 *
 * This class is thread-safe.  It can support two Players running on separate threads.
 *
 * @author asiner
 */

public class TttGame implements GameForAdmin
{
    private  final Logger logger = Logger.getLogger(TttGame.class);

    private final Player xPlayer;
    private final Player oPlayer;
    private final Board tttBoard;

    private final AtomicBoolean gameInProgress = new AtomicBoolean(false);
    private final AtomicReference<Player> currPlayer;

    public static GameForAdmin createGame(Player xPlayer, Player oPlayer) {
        return new TttGame(xPlayer, oPlayer);
    }

    public TttGame(Player xPlayer, Player oPlayer) {
        this.xPlayer = xPlayer;
        this.oPlayer = oPlayer;

        currPlayer = new AtomicReference<>(xPlayer);
        tttBoard = new TttBoard();
    }

    @Override
    public void startGame() {
        if (! gameInProgress.compareAndSet(Boolean.FALSE, Boolean.TRUE)) {
            throw new GameException("Game already in progress");
        }

        xPlayer.yourTurn();
    }

    @Override
    public int[] getFreeSquares() {
        return tttBoard.getFreeSquares();
    }

    @Override
    public String getScreenShot() {
        return tttBoard.toString();
    }

    @Override
    public boolean checkBox(Player player, int squareNum) throws GameException {

        if (! gameInProgress.get()) {
            throw new GameException("Game not in progress");
        }

        if (! player.equals(currPlayer.get())) {
            throw new GameException("Not your turn to play, " + player);
        }

        MarkType markType = getMarkForPlayer(player);
        boolean gameOver = false;
        gameOver = tttBoard.checkBox(squareNum, markType);
        logger.debug("" + player + " marked " + markType + " on square " + squareNum);

        if (gameOver) {
            MarkType winner = tttBoard.getWinner().orElse(MarkType.NO_MARK);
            String gameOverMsg =
                    winner == MarkType.NO_MARK ? MsgHandler.msg("game-over-nowinner")
                                               : MsgHandler.msg("game-over", winner);

            logger.debug(gameOverMsg);
            terminate(gameOverMsg);
        }
        else {
            Player nextPlayer = currPlayer.updateAndGet(p -> currPlayer.get().equals(xPlayer) ? oPlayer : xPlayer);
            nextPlayer.yourTurn();
        }

        return true;
    }

    private void terminate(String msg) {
        boolean justEnded = gameInProgress.getAndSet(false);
        if ( justEnded) {
            xPlayer.gameOver(msg);
            oPlayer.gameOver(msg);
        }
    }

    private MarkType getMarkForPlayer(Player player) {
        if (player.equals(xPlayer)) return X_MARK;
        else if (player.equals(oPlayer)) return O_MARK;
        else throw new IllegalArgumentException("Unknown player: " + player);
    }

    @Override
    public String toString()
    {
        return "Game [xPlayer=" + xPlayer + ", oPlayer=" + oPlayer + ", tttBoard=" + tttBoard
                + ", gameInProgress=" + gameInProgress + ", currPlayer=" + currPlayer + "]";
    }
}
