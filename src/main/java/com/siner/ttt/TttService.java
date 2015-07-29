package com.siner.ttt;

import com.siner.ttt.player.Player;

/**
 * Service to register a player, and start a game with the computer
 */
public interface TttService
{
    /** Register player's name, and returns a Player object representing the player **/
    Player registerPlayer(String name);

    /** Start a game with the computer. */
    void playWithComputer(Player player);
}
