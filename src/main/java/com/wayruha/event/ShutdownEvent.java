package com.wayruha.event;

import java.util.ArrayList;
import java.util.List;

/**
 * Event is used to notify ShutdownObservers that the app should be stopped.
 */
public class ShutdownEvent   {
    static List<ShutdownObserver> observers = new ArrayList<>();

    public static void raise(){
        System.out.println("Time to stop the app");
        notifyAllObs();
    }
    public static void register(ShutdownObserver obs ){
        observers.add(obs);
    }
    public static void notifyAllObs(){
        observers.stream().forEach(obs->obs.update());
    }
}
