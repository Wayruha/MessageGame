package com.wayruha;

import com.wayruha.event.ShutdownEvent;
import com.wayruha.event.ShutdownObserver;
import com.wayruha.player.*;
import com.wayruha.player.creator.AbstractPlayerCreator;
import com.wayruha.player.creator.AutorespondPlayerCreator;
import com.wayruha.player.creator.UserInputCreator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.concurrent.CountDownLatch;

/**
 * It`s the Main class of app.
 * Responsibilities.
 * This class creates 2 threads and pass them implementations of AbstractPlayerCreator,
 * so each player will be created and running in it`s own thread.
 * It also connects 2 player.
 * This class is responsible for stopping the app. It`s subscribed to catch ShutdownEvent.
*/
public class MessageGame implements ShutdownObserver {
    public static final int MAXIMUM_MSGS_COUNT = 10;
    private static final Logger log = (Logger) LogManager.getLogger(MessageGame.class);
    private PlayerThread thread1, thread2;

    public static void main(String[] args) {
        MessageGame game = new MessageGame();
        game.start();
    }

    public void start() {
        //Count down latch is used to notify  players when both become READY
        CountDownLatch latch = new CountDownLatch(2);

        //creates Creators
        AbstractPlayerCreator userInputCreator = new UserInputCreator();
        userInputCreator.setName("Roman");
        AbstractPlayerCreator autorespondCreator = new AutorespondPlayerCreator();
        autorespondCreator.setName("Vitaliy");


        thread1 = new PlayerThread(userInputCreator, latch);
        thread1.start();
        thread2 = new PlayerThread(autorespondCreator, latch);
        thread2.start();

        Player player1 = thread1.getPlayer();
        Player player2 = thread2.getPlayer();

        //wait until objects are created
        while (player1 == null || player2 == null) ;
        try {
            BaseClient.connect(player1, player2);
            log.debug("Players are connected");
        } catch (Exception e) {
            log.error("Can`t connect players");
            e.printStackTrace();
            shutdown();

        }
        ShutdownEvent.register(this);
        while(true);
    }

    private void shutdown() {
        thread1.close();
        thread2.close();
        System.exit(1);
    }

    @Override
    public void update() {
        log.error("Caught shutdown event");
        shutdown();
    }
}
