package examples;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Worker extends CommandTask {

    private BlockingQueue<CommandTask> queue;
    private List<TaskObserver> observers;



    private volatile boolean isRunning = true;

    public Worker(BlockingQueue<CommandTask> queue, List<TaskObserver> observers, TaskStats stats) {
        this.queue = queue;
        this.observers = observers;
        this.stats = stats;
    }

    @Override
    public void run() {
        try {
            while (isRunning || !queue.isEmpty()) {
                CommandTask task = queue.poll(1, TimeUnit.SECONDS);
                    try {
                        if(task != null) {
                            long start = System.nanoTime();
                            task.run();
                            long duration = System.nanoTime() - start;
                            notifyCompleted(task, duration / 1_000_000); // ms
                            stats.incrementSuccess();
                        }
                    } catch (Exception e) {
                        observers.forEach(o -> o.onTaskFailure(task, e));
                        stats.incrementFail();
                        throw new RuntimeException(e);
                    }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        observers.forEach(o -> o.onFinish(this));

    }


    public void stop(){
        isRunning = false;
    }

    private void notifyCompleted(CommandTask task, long durationMillis) {
        for (TaskObserver observer : observers) {
            if (observer instanceof TimedTaskObserver) {
                ((TimedTaskObserver) observer).onTaskCompleted(task, durationMillis);
            } else {
                observer.onTaskSuccess(task); // fallback nếu không hỗ trợ thời gian
            }
        }
    }

    @Override
    public String getTaskName() {
        return Thread.currentThread().getName();
    }
}
