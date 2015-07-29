package com.siner.ttt.game;

import static com.siner.ttt.game.MarkType.NO_MARK;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.IntUnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.siner.ttt.utils.MsgHandler;

@Component
@Scope("prototype")
public class TttBoard implements Board
{
    public static final int TTT_NUM_SQUARES = 9;
    public static final int TTT_SIZE = 3;

    private static final Logger logger = Logger.getLogger(TttBoard.class);

    protected final IntUnaryOperator rowIncrement = (i) -> i + 1;
    protected final IntUnaryOperator colIncrement = (i) -> i + 3;
    protected final IntUnaryOperator upDiagIncrement = (i) -> i + 2;
    protected final IntUnaryOperator downDiagIncrement = (i) -> i + 4;

    private List<MarkType> tttGrid;
    private Optional<MarkType> winner = Optional.empty();

    public TttBoard() {
        initBoard();
    }

    private void initBoard() {
        MarkType[] marks = new MarkType[TTT_NUM_SQUARES];
        Arrays.fill(marks, NO_MARK);
        tttGrid = Arrays.asList(marks);
    }


    @Override
    public boolean checkBox(int squareNum, MarkType mark) {
        validateSquareNum(squareNum);
        tttGrid.set(squareNum, mark);
        return isGameOver();
    }

    @Override
    public Optional<MarkType> getWinner() {
        return winner;
    }

    @Override
    public MarkType getMarkType(int squareNum) {
        validateSquareNum(squareNum);
        return tttGrid.get(squareNum);
    }

    @Override
    public int[] getFreeSquares() {
        int[] indices = IntStream.range(0, tttGrid.size())
                .filter(i->tttGrid.get(i) == NO_MARK)
                .toArray();

        logger.debug("Indices of free squares : " + Arrays.toString(indices));
        return indices;
    }


    /**
     *  Determines if the game is over, and whether or not there is a winner.
     *  Has a side effect of setting the winner variable.
     *  If the board is full and nobdy has won, set the winner variable to NO_MARK.
     *
     * @return true is the game is over (someone has won, or the board is full)
     */
    private boolean isGameOver() {
        boolean hasWinner = false;

        Optional<MarkType> winnerMark =
                Stream.of(hasWinner(0, rowIncrement),
                          hasWinner(3, rowIncrement),
                          hasWinner(6, rowIncrement),
                          hasWinner(0, colIncrement),
                          hasWinner(1, colIncrement),
                          hasWinner(2, colIncrement),
                          hasWinner(2, upDiagIncrement),
                          hasWinner(0, downDiagIncrement))
               .filter(Optional::isPresent)
               .map(Optional::get)
               .findFirst();

        if (winnerMark.isPresent()) {
              logger.debug("Found winner: " + winnerMark.orElse(NO_MARK));
              hasWinner = true;
              winner = winnerMark;
        }
        else if (tttGrid.stream().noneMatch(m->m == NO_MARK)) {
                winner = Optional.of(NO_MARK);
                hasWinner = true;
            }

        return hasWinner;
    }

    /*
     *  Uses the input parameters to determine the group to be tested (i.e., a certain row, column or diagonal).
     *  If that group has only one type of Mark, and it's not NO_MARK, then there is a winner.
     *  Returns the Optional<MarkType> of the winner
     */
    private Optional<MarkType> hasWinner(int n, IntUnaryOperator increment) {

        // Get group of MarkTypes to test (e.g., a certain row or column)
         List<MarkType> markGroup = getGroup(n, increment);

        Set<MarkType> markSet = markGroup
                .stream()
                .collect(Collectors.toSet());

        boolean hasWon = markSet.size() == 1 && markGroup.get(0) != NO_MARK;

        return hasWon ? Optional.of(markGroup.get(0)) : Optional.empty();
    }

    // Uses the input parameters to determine the group to be tested (i.e., a certain row, column or diagonal)
    public List<MarkType> getGroup(int startSquare, IntUnaryOperator increment) {

        List<MarkType> marks = new ArrayList<>(TTT_SIZE);
        for (int i = 0; i < TTT_SIZE; i++) {
            marks.add(tttGrid.get(startSquare));
            startSquare = increment.applyAsInt(startSquare);
        }

        return marks;
    }

    private void validateSquareNum(int squareNum)
    {
        if (squareNum < 0 || squareNum >= tttGrid.size()) {
            throw new GameException( MsgHandler.msg("square-illegal", squareNum) ) ;
        }

        if (tttGrid.get(squareNum) != NO_MARK) {
            throw new GameException( MsgHandler.msg("square-taken", squareNum, tttGrid.get(squareNum)) );
        }
    }

    /** Returns a Stringified picture of the current board */
    @Override
    public String toString()
    {
        StringBuilder buf = new StringBuilder();
        int n = 0;
        for (int row = 0; row < TTT_SIZE; row++) {
            for (int col = 0; col < TTT_SIZE; col++) {
                buf.append(tttGrid.get(n++) + " ");
            }
            buf.append("\n");
        }

        return buf.toString();
    }
}
