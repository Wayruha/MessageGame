package com.wayruha.player;

import com.wayruha.exception.CommunicationException;
import com.wayruha.Message;
import com.wayruha.MessageGame;
import com.wayruha.event.ShutdownEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
* Class-abstraction of player (user). Extends BaseClient, and overrides its methods.
 * Can send messages and receive them. Doesn`t have to worry about details of message transportation.
 * To represent message Message object is used.
 * Can save all messages in  List history
 * Can raise ShutdownEvent if received some count of messages
 */
public  class Player extends BaseClient {
    protected String name;
    private List<Message> history;
    private int countSent;
    protected static final Logger log = (Logger) LogManager.getLogger(Player.class);

    public Player(String name) {
        this.name = name;
        countSent = 0;
        history = new ArrayList<>();
    }

    @Override
    public void sendMessage(Message msg) {
        countSent++;
        msg.setAuthor(name);
        msg.setAuthorsMessageindex(countSent);
        try {
            super.sendMessage(msg);
            log.info("Player " + name + " has sent a "+msg.toString());
        }catch (CommunicationException ex){
            log.error("Player "+name+ " failed sending a message");
        }


    }

    @Override
    public void newMessageReceived(Message msg) {
        log.info("Player " + name + " received a " +msg.toString());
        history.add(msg);
        if (history.size() >= MessageGame.MAXIMUM_MSGS_COUNT)
            ShutdownEvent.raise();

    }

    @Override
    protected void startCommunication() {
        startListening();
        sendMessage("Random message");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
