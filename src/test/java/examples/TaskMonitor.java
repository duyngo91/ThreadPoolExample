package examples;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TaskMonitor {
    private final BlockingQueue<?> queue;
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public TaskMonitor(BlockingQueue<?> queue) {
        this.queue = queue;
    }

    public void start() {
        scheduler.scheduleAtFixedRate(() -> {
            System.out.println("🕒 Task còn lại: " + queue.size());
        }, 0, 1, TimeUnit.SECONDS);
    }

    public void stop() {
        scheduler.shutdown();
    }
}
