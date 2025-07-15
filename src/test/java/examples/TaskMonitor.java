package examples;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class TaskMonitor implements TimedTaskObserver{
    private final BlockingQueue<?> queue;
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private TaskStats stats;
    public TaskMonitor(BlockingQueue<?> queue, TaskStats stats) {
        this.queue = queue;
        this.stats = stats;
    }

    public void start() {
        scheduler.scheduleAtFixedRate(() -> {
            System.out.println("🕒 Task còn lại: " + queue.size());
        }, 0, 1, TimeUnit.SECONDS);
    }

    public void stop() {
        scheduler.shutdown();
    }

    @Override
    public void onTaskCompleted(CommandTask task, long durationMillis) {
        stats.incrementSuccess();
    }

    @Override
    public void onTaskSuccess(CommandTask task) {
        stats.incrementSuccess();
    }

    @Override
    public void onFinish(CommandTask worker) {
        System.out.println("✅ Finish: %s" + worker.stats.getSummary());
    }

    @Override
    public void onTaskFailure(CommandTask task, Exception e) {
        stats.incrementFail();
    }
}
