package com.wayruha.communication;

import com.wayruha.Message;

import java.io.IOException;

/**
 * Main responsibility is to take care of message transportation.
 * Different implementations can use different transport protocols, different ways of communication
 */
public interface Communicator {

    //static void connect(com.wayruha.communication.Communicator com1, com.wayruha.communication.Communicator com2) throws IOException {};
    void listen();
    void write(Message msg) throws IOException;
    void pairWith(Communicator communicator) throws IOException;
    void close();
    //PipedOutputStream getOutputStream();
   // void listen(PipedOutputStream pipedOut) throws IOException;
    //void notifyPlayerWithNewMessage();
    //void connect(com.wayruha.player.Player a,com.wayruha.player.Player b) throws IOException;
}
