package com.siner.ttt.game;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import com.siner.ttt.player.ConsolePlayer;
import com.siner.ttt.player.Player;
import com.siner.ttt.utils.MsgHandler;

public class TttGameTest
{

    @Autowired
    private MsgHandler msgHandler;

    private Player p1;
    private Player p2;
    private GameForAdmin tttGame;

    @Before
    public void setUp() throws Exception
    {
        MockitoAnnotations.initMocks( this );
        p1 = Mockito.mock(ConsolePlayer.class);
        p2 = Mockito.mock(ConsolePlayer.class);
        tttGame = Mockito.spy(new TttGame(p1, p2));
    }

    @Test(expected = GameException.class)
    public void testStartGameTwice()
    {
        tttGame.startGame();
        tttGame.startGame();
    }

    @Test(expected = GameException.class)
    public void testCheckBoxGameNotStarted()
    {
        tttGame.checkBox(p1, 0);
    }

    @Test(expected = GameException.class)
    public void testNotPlayerTurn()
    {
        tttGame.startGame();
        tttGame.checkBox(p2, 0);
    }

    @Test(expected = GameException.class)
    public void testSamePlayerTwice()
    {
        tttGame.startGame();
        tttGame.checkBox(p2, 0);
        tttGame.checkBox(p2, 1);
    }

    @Test(expected = GameException.class)
    public void testUnknownPlayer()
    {
        Player fakeP = Mockito.mock(ConsolePlayer.class);
        tttGame.startGame();
        tttGame.checkBox(fakeP, 0);
    }
}
