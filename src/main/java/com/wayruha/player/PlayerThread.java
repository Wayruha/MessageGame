package com.wayruha.player;

import com.wayruha.player.creator.AbstractPlayerCreator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.CountDownLatch;

/**
 * Custom Thread implementation that keeps the information about player ad CountDownLatch
 */
public class PlayerThread extends Thread {
    final private Player player;
    final private CountDownLatch latch;
    private static final Logger log = (Logger) LogManager.getLogger(PlayerThread.class);

    public PlayerThread(AbstractPlayerCreator creator, CountDownLatch latch) {
        super();
        this.player = creator.createPlayer();
        this.latch = latch;

    }

    @Override
    public void run() {
        player.start(latch);
    }


    /*
      * close all players streams, threads and interrupt itself
     */
    public void close() {
        log.info("Player thread was interrupted");
        player.shutdown();
        this.interrupt();

    }

    public Player getPlayer() {
        return player;
    }
}
