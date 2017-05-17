package com.wayruha.player.creator;

import com.wayruha.player.AutorespondPlayer;
import com.wayruha.player.Player;

/**
 * Produces AutorespondPlayer
 */
public class AutorespondPlayerCreator extends AbstractPlayerCreator {

    @Override
    public Player createPlayer(String name) {
        Player player = new AutorespondPlayer(name);
        return player;
    }

}
