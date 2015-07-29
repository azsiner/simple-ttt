package com.siner.ttt;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.siner.ttt.game.GameForAdmin;
import com.siner.ttt.game.TttGame;
import com.siner.ttt.player.ComputerPlayer;
import com.siner.ttt.player.ConsolePlayer;
import com.siner.ttt.player.Player;
import com.siner.ttt.utils.MsgHandler;

@Service("tttService")
public class TttServiceImpl implements TttService {

    private static final Logger logger = Logger.getLogger(TttServiceImpl.class);

    private final ConcurrentMap<String, Player> players = new ConcurrentHashMap<>();
    private final ConcurrentMap<Player, GameForAdmin> playersGames = new ConcurrentHashMap<>();

    @Override
    public Player registerPlayer(String name) {
        Player regPlayer = new ConsolePlayer(name);
        Player player = players.putIfAbsent(regPlayer.getName(), regPlayer);
        if (player != null) {
            logger.warn("Player " + player + " already registered");
            return player;
        }

        return regPlayer;
    }

    @Override
    public void playWithComputer(Player player) {

        Player otherPlayer = new ComputerPlayer();
        GameForAdmin game = new TttGame(player, otherPlayer);
        GameForAdmin currGame = playersGames.putIfAbsent(player, game);
        if (currGame != null) {
            logger.warn("Player " + player + " already playing game " + game);
        }

        String gameStartMsg = MsgHandler.msg("game-start-msg");

        player.setGame(game, gameStartMsg);
        otherPlayer.setGame(game, gameStartMsg);

        logger.debug("Game starting between " + player + " and " + otherPlayer);
        game.startGame();
    }
}
