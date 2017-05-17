package com.wayruha.player.creator;

import com.wayruha.player.Player;

/**
 * Abstraction whose implementations will be used to create different types of Player
 * Name can be set for a whole creator or passed as argument in method
 */
public abstract class AbstractPlayerCreator {
    protected String name;

    public AbstractPlayerCreator() {
    }

    abstract public Player createPlayer(String name);

    public Player createPlayer(){
        return createPlayer(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
