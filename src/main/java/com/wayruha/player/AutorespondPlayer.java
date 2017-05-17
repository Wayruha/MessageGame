package com.wayruha.player;

import com.wayruha.exception.CommunicationException;
import com.wayruha.Message;

import java.util.Random;

/**
 * Subclass of Player.
 * Differs from Player because it will automatically reply to sender with one of the preset phrases
 * It doesn`t try to send message after start, it waits.
 */
public class AutorespondPlayer extends Player {

    String[] possibleAnswers=new String[]{"Thank you for your message. ", "Hi!", "It was interesting to read" };

    private Random rand;
    public AutorespondPlayer(String name) {
        super(name);
        rand = new Random();
    }

    @Override
    public void newMessageReceived(Message msg) {
        super.newMessageReceived(msg);
        String newText=possibleAnswers[rand.nextInt(possibleAnswers.length)];
        //TODO add message counter
        Message newMsg=null;
        try {
            newMsg=new Message(newText+" ["+msg.getText()+"]");
            newMsg.setPrevMsg(msg);
            sendMessage(newMsg);
        } catch (CommunicationException e) {
            log.error("Player "+name+" failed to send message "+newMsg.toString());
            e.printStackTrace();
        }
    }

    @Override
    protected void startCommunication() {
        startListening();
    }
}
