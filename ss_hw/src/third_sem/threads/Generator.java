package third_sem.threads;

import java.util.Random;

public class Generator extends Thread {
    private final ResourceManager manager;
    private int resourceMax;
    private Random random = new Random();

    Generator(ResourceManager manager, int resourceMax) {
        this.manager = manager;
        this.resourceMax = resourceMax;
    }

    @Override
    public void run() {
        super.run();

        int i = 1;


        while (manager.isAlive()) {
            new Worker(i).start();
            i++;
            try {
                sleep(random.nextInt(1000)); //hardcoded max sleep time
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        Thread.getAllStackTraces()
                .keySet()
                .stream()
                .filter(t ->
                        t.getName().startsWith("worker") && t.isAlive())
                .forEach(t -> {
                    try {
                        t.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
    }

    private class Worker extends Thread {
        private String workerSign;

        Worker(int number) {
            super("Worker #" + number);
            workerSign = "Worker #" + number + " ";
        }

        @Override
        public void run() {
            super.run();

            System.out.println(workerSign + "started");

            Random random = new Random();

            int amountOfResourcesNeed = random.nextInt(resourceMax);

            if (manager.getResource(amountOfResourcesNeed)) {
                System.out.println(workerSign + "get " + amountOfResourcesNeed);

                try {
                    sleep(random.nextInt(1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                manager.returnResource(amountOfResourcesNeed);

                System.out.println(workerSign + "resource returned");

            } else {
                System.out.println(workerSign + "requires too much resources");
            }
        }
    }
}
