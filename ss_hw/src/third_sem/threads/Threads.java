package third_sem.threads;

import java.io.IOException;
import java.util.Properties;

public class Threads {
    private static final String propertiesName = "thread_managing.properties";

    public static void main(String[] args) {
        //get properties
        Properties properties = new Properties();
        try {
            properties.load(Threads.class.getResourceAsStream(propertiesName));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }

        int resourceMax = Integer.parseInt(properties.getProperty("resource_max"));
        String logs = properties.getProperty("logfile");
        int workingTime = Integer.parseInt(properties.getProperty("working_time"));

        ResourceManager manager = new ResourceManager(resourceMax, logs, workingTime);

        Generator generator = new Generator(manager, resourceMax);

        manager.start();
        generator.start();


        try {
            generator.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
