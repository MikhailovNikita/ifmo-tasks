package third_sem.threads;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.concurrent.atomic.AtomicInteger;

public class ResourceManager extends Thread {
    private AtomicInteger resourceCount;
    private int workingTime;
    private PrintStream outputStream;
    private long startTime;

    public ResourceManager(int resourceCount, String logs, int workingTime) {
        this.resourceCount = new AtomicInteger(resourceCount);
        this.workingTime = workingTime;
        try {
            outputStream = new PrintStream(new FileOutputStream(logs));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public synchronized boolean getResource(int amount) {
        if (resourceCount.get() < amount) {
            outputStream.println("Can't give " + amount + " resources");
            log();
            return false;
        } else {
            resourceCount.getAndAdd(-amount);
            outputStream.println("Giving " + amount + " res");
            log();
            return true;
        }

    }

    @Override
    public void run() {
        super.run();

        outputStream.println("Go");
        startTime = System.currentTimeMillis();
        outputStream.println("Free " + resourceCount + " res");
        try {
            sleep(workingTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        outputStream.println("Stop");
        outputStream.close();
    }

    public synchronized void returnResource(int amount) {
        resourceCount.getAndAdd(amount);
        outputStream.println("Returning " + amount + " res");
        log();
    }

    private synchronized void log() {
        outputStream.println("Time: " + (System.currentTimeMillis() - startTime) + " | Resource: " + resourceCount);
    }
}