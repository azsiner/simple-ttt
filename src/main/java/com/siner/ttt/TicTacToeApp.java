package com.siner.ttt;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.siner.ttt.player.Player;

/**
 * Main class for the (non-web) Tic-Tac-Toe Game
 *
 * @author asiner
 */

public class TicTacToeApp
{
    private static final Logger logger = Logger.getLogger(TicTacToeApp.class);

    public static void main(String[] args)
    {
        ApplicationContext context = new ClassPathXmlApplicationContext("/application-context.xml");

        TttService tttService = (TttService) context.getBean("tttService");

        Player player = tttService.registerPlayer(System.getProperty("user.name"));
        logger.debug("Registered player: " + player);

        tttService.playWithComputer(player);
    }
}
