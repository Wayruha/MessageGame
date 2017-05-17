package com.wayruha.communication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wayruha.Message;
import com.wayruha.player.BaseClient;
import com.wayruha.player.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;

/**
 * Implementation of Communicator interface.
 * Class uses DataStreams over  PipedStreams to transport data between 2 endpoints.
 * It`s responsible to read input stream and write to output stream.
 * It uses Jackson ObjectMapper to serialize\deserialize Message
 */
public class CommunicatorImplement implements Communicator {

    private BaseClient client;
    private PipedOutputStream pipedOut;
    private DataInputStream dataIn;
    private DataOutputStream dataOut;
    private static final Logger log = (Logger) LogManager.getLogger(CommunicatorImplement.class);
    private ObjectMapper mapper;
    private Thread listeningThread;

    public CommunicatorImplement(BaseClient client) {
        this.client = client;
        pipedOut = new PipedOutputStream();
        dataOut = new DataOutputStream(pipedOut);
        mapper = new ObjectMapper();
    }


    /**
     * Constantly read the input stream in new thread
     * Once the message comes - deserialize it and notify player with new message
     */
    @Override
    public void listen() {
        Runnable runnable = () -> {
            while (true) {
                try {
                    String serMessage = dataIn.readUTF();
                    Message msg = mapper.readValue(serMessage, Message.class);
                    log.debug("Message was read  by player " + ((Player) client).getName());
                    notifyPlayerWithNewMessage(msg);
                } catch (IOException e) {
                    log.error("Can`t read from input pipe");
                    try {
                        dataIn.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    e.printStackTrace();
                    break;
                }
            }
        };
        listeningThread = new Thread(runnable);
        listeningThread.start();

    }

    private void notifyPlayerWithNewMessage(Message msg) throws IOException {
        client.newMessageReceived(msg);
    }



    public void write(Message msg) throws IOException {
        String serMsg = mapper.writeValueAsString(msg);
        dataOut.writeUTF(serMsg);
        log.debug("Message was written " + msg.getText() + " by player " + ((Player) client).getName());
    }

    /**
     * Make this Communicator listening parter`s Communicator
     */
    @Override
    public void pairWith(Communicator com) throws IOException {
        CommunicatorImplement communicator = (CommunicatorImplement) com;
        dataIn = new DataInputStream(new PipedInputStream(communicator.pipedOut));
        client.onSuccessfulConnection();
    }

    /**
     * Close listening thread, streams
     */
    @Override
    public void close() {
        try {
            if (listeningThread != null)
                listeningThread.interrupt();
            dataIn.close();
            dataOut.close();
        } catch (IOException e) {
            log.error("Couldn`t close streams. Player " + client);
            e.printStackTrace();
        }
    }

}
