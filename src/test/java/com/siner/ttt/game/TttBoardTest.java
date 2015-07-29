package com.siner.ttt.game;

import static com.siner.ttt.game.MarkType.O_MARK;
import static com.siner.ttt.game.MarkType.X_MARK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.function.IntUnaryOperator;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TttBoardTest
{
    private static final Logger logger = Logger.getLogger(TttBoardTest.class);

    private TttBoard tttBoard;

    @Before
    public void setUp()
    {
        tttBoard = new TttBoard();
    }

    @After
    public void tearDown()
    {
        tttBoard = null;
    }

    @Test
    public void testRow1Win()
    {
        checkForWin(0, X_MARK, tttBoard.rowIncrement);
    }

    @Test
    public void testRow2Win()
    {
        checkForWin(3, O_MARK, tttBoard.rowIncrement);
    }

    @Test
    public void testRow3Win()
    {
        checkForWin(6, X_MARK, tttBoard.rowIncrement);
    }

    @Test
    public void testCol1Win()
    {
        checkForWin(0, O_MARK, tttBoard.colIncrement);
    }

    @Test
    public void testCol2Win()
    {
        checkForWin(1, X_MARK, tttBoard.colIncrement);
    }

    @Test
    public void testCol3Win()
    {
        checkForWin(1, O_MARK, tttBoard.colIncrement);
    }

    @Test
    public void testOhUpDiagWin()
    {
        checkForWin(2, O_MARK, tttBoard.upDiagIncrement);
    }

    @Test
    public void testExDownDiagWin()
    {
        checkForWin(0, X_MARK, tttBoard.downDiagIncrement);
    }

    @Test
    public void testforNonWin()
    {
        IntUnaryOperator evenIncrement = (i) -> i + 2;
        checkForFail(0, X_MARK, evenIncrement);
    }

    @Test
    public void testFreeBoxesCount()
    {
        int numFreeSquares = TttBoard.TTT_NUM_SQUARES;
        MarkType mark = X_MARK;

        for (int i = 0; i < TttBoard.TTT_NUM_SQUARES; i++)
        {
            mark = mark == X_MARK ? O_MARK : X_MARK;
            tttBoard.checkBox(i, mark);
            assertEquals(--numFreeSquares, tttBoard.getFreeSquares().length);
        }
    }

    private void checkForWin(int startSquare, MarkType mark, IntUnaryOperator increment)
    {
        doThreeChecks(startSquare, mark, increment, true);
    }

    private void checkForFail(int startSquare, MarkType mark, IntUnaryOperator increment)
    {
        doThreeChecks(startSquare, mark, increment, false);
    }

    private void doThreeChecks(int startSquare, MarkType mark, IntUnaryOperator increment,
            boolean expectedResult)
    {
        boolean done = tttBoard.checkBox(startSquare, mark);
        assertFalse(done);

        int nextSquare = increment.applyAsInt(startSquare);
        done = tttBoard.checkBox(nextSquare, mark);
        assertFalse(done);

        nextSquare = increment.applyAsInt(nextSquare);
        done = tttBoard.checkBox(nextSquare, mark);

        logger.info("\n" + tttBoard.toString());
        if (expectedResult)
        {
            assertTrue(done);
        } else
        {
            assertFalse(done);
        }
    }
}
