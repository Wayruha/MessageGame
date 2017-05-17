package com.wayruha.player;

import com.wayruha.exception.CommunicationException;
import com.wayruha.Message;
import com.wayruha.communication.Communicator;
import com.wayruha.communication.CommunicatorImplement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
/**
 * Abstraction that represents the user-endpoint in communication process.
 * Has possible states READY and NOT_READY.
 * Implement some basic functional to send and receive messages using Communicator object
 */
public abstract class BaseClient {
    private Communicator communicator;
    private BaseClient partner;
    private State state;
    protected CountDownLatch latch;
    private static final Logger log = (Logger) LogManager.getLogger(BaseClient.class);

    public BaseClient() {
        communicator = new CommunicatorImplement(this);
        state = State.NOT_READY;
    }

    /**
     * Makes the communicator listening its input stream
     */
    public void startListening() {
        communicator.listen();
    }

    public void sendMessage(String text) {
        Message msg = new Message(text);
        sendMessage(msg);
    }

    public void sendMessage(Message msg) {
        try {
            communicator.write(msg);
        } catch (IOException e) {
            CommunicationException exception = new CommunicationException("Couldn`t send a message");
            exception.setStackTrace(e.getStackTrace());
            throw exception;

        }
    }

    /**
     * Connect 2 BaseClients and set up communication between them
     */
    public static void connect(BaseClient client1, BaseClient client2) throws IOException {
        client1.partner = client2;
        client2.partner = client1;
        client1.communicator.pairWith(client2.communicator);
        client2.communicator.pairWith(client1.communicator);
    }

    /**
     * This method will be called after communicator successfully reads a message
     */
    public abstract void newMessageReceived(Message msg);

    /**
     * Used to tell that everything have been set up and it can start communication
     */
    protected abstract void startCommunication();

    /**
     * After this player becomes ready  - wait for his partner and start communication
     */
    public void onSuccessfulConnection() {
        state = State.READY;
        latch.countDown();
    }

    public void start(CountDownLatch latch) {
        this.latch = latch;
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        startCommunication();

    }

    //close everything that is closeable
    public void shutdown() {
        state = State.NOT_READY;
        communicator.close();
        log.info("Players listening thread was interrupted and streams were closed");
    }

    public Communicator getCommunicator() {
        return communicator;
    }

    public State getState() {
        return state;
    }

    public void setLatch(CountDownLatch latch) {
        this.latch = latch;
    }
}



