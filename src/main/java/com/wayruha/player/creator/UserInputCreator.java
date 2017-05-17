package com.wayruha.player.creator;

import com.wayruha.player.Player;
import com.wayruha.player.UserInputPlayer;

/**
 * Produces UserInputPlayer
 */
public class UserInputCreator extends AbstractPlayerCreator {

    @Override
    public Player createPlayer(String name) {
        Player player = new UserInputPlayer(name);
        return player;
    }
}
