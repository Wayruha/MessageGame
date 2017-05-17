package com.wayruha.player;

import com.wayruha.event.ShutdownEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Subclass of Player.
 * Extends the Player`s functionality letting real user to type message to send.
 * Uses console to read user`s input.
 * Unlike AutorespondPlayer it tries to read user input  after startCommunication was called and do it until the app stopped
 */
public class UserInputPlayer extends Player {
    public UserInputPlayer(String name) {
        super(name);

    }

    @Override
    public void sendMessage(String msg) {
        BufferedReader br = null;
        String input;
        try {
            br = new BufferedReader(new InputStreamReader(System.in));
            input = br.readLine();

        } catch (IOException e) {
            log.error("Can`t read user input");
            e.printStackTrace();
            System.exit(1);
            ShutdownEvent.raise();
            return;
        }
        super.sendMessage(input);
    }

    @Override
    protected void startCommunication() {
        startListening();
        System.out.println("Enter your messages here");
        while (true) {
            sendMessage("");

        }
    }
}
