package it.polimi.ingsw.view.cli;

import java.util.concurrent.atomic.AtomicBoolean;

public class NotMyTurn implements Runnable{
    private Thread thread;
    private final AtomicBoolean running = new AtomicBoolean(false);
    private int interval;


    public void start() {
        thread = new Thread(this);
        thread.start();
    }

    public void stop() {
        running.set(false);
    }

    public void run() {
        running.set(true);
        while (running.get()) {
            try {
                Thread.sleep(interval);
            } catch (InterruptedException e){
                Thread.currentThread().interrupt();
                System.out.println(
                        "Thread was interrupted, Failed to complete operation");
            }
            // do something here
        }
    }
}
