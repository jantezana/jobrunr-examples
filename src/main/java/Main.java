import org.jobrunr.configuration.JobRunr;
import org.jobrunr.scheduling.BackgroundJob;
import org.jobrunr.scheduling.cron.Cron;
import org.jobrunr.storage.InMemoryStorageProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;

/**
 * Main class
 *
 * @author jantezana
 * @since 2022/08/01
 */
public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws InterruptedException {
        logger.info("[JobRunr - examples]");

        JobRunr.configure()
               .useStorageProvider(new InMemoryStorageProvider())
               .useBackgroundJobServer()
               .useDashboard()
               .initialize();

        BackgroundJob.enqueue(() -> System.out.println("BackgroundJob.enqueue"));

        BackgroundJob.schedule(Instant.now().plusSeconds(10), () -> System.out.println("BackgroundJob.schedule in 10 seconds"));

        BackgroundJob.scheduleRecurrently(Cron.every15seconds(), () -> System.out.println("BackgroundJob.scheduleRecurrently each 15 seconds"));

        // keep dashboard running by blocking the main thread
        Thread.currentThread().join();
    }
}
