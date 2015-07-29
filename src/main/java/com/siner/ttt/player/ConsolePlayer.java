package com.siner.ttt.player;

import static com.siner.ttt.utils.MsgHandler.msg;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Player who is playing on the command line
 *
 * @author asiner
 */
public class ConsolePlayer extends AbstractPlayer
{
    private final Scanner scanner = new Scanner(System.in);

    public ConsolePlayer(String name) {
        super(name);
    }

    @Override
    protected int getNextMove() {
        int square = -1;
        while (true) {
            output(myGame.getScreenShot());
            print( msg("game-pick-free-square") + " " + Arrays.toString(myGame.getFreeSquares()) + ": ");
            String input = scanner.nextLine();
            try {
                square = Integer.parseInt(input);
                return square;
            }
            catch (Exception e) {
                output( msg("bad-input", input) );
            }
        }
    }

    @Override
    protected void output(String msg) {
        print(msg + "\n");
    }

    private void print(String msg) {
        System.out.print(msg);
    }

    @Override
    protected void cleanup()
    {
        scanner.close();
    }
}
