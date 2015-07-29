package com.siner.ttt.player;

import java.util.Random;

import org.apache.log4j.Logger;


/**
 * Player whose moves are generated automatically (no user input/output used)
 *
 *  @author asiner
 */
public class ComputerPlayer extends AbstractPlayer
{
    private static final String COMPUTER_NAME = "__COMPUTER_PLAYER___";
    private  final Logger logger = Logger.getLogger(ComputerPlayer.class);
    private final Random random;

    public ComputerPlayer() {
        super(COMPUTER_NAME);
        random = new Random(System.currentTimeMillis());
    }

    /** Randomly check one of free squares */
    @Override
    protected int getNextMove() {
        int[] freeSquares = myGame.getFreeSquares();
        int i = random.nextInt(freeSquares.length);
        return freeSquares[i];
    }

    @Override
    protected void output(String msg)
    {
        logger.debug(msg);
    }

    @Override
    protected void cleanup()
    {
        // Nothing to do
    }
}
