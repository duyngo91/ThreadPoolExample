package examples;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class Worker implements Runnable {

    private BlockingQueue<CommandTask> queue;
    private List<TaskObserver> observers;
    private volatile boolean isRunning = true;

    public Worker(BlockingQueue<CommandTask> queue, List<TaskObserver> observers) {
        this.queue = queue;
        this.observers = observers;
    }

    @Override
    public void run() {
        try {
            while (isRunning || !queue.isEmpty()) {
                CommandTask task = queue.poll(1, TimeUnit.SECONDS);
                    try {
                        if(task != null) {
                            long start = System.nanoTime();
                            task.call();
                            long duration = System.nanoTime() - start;
                            notifyCompleted(task, duration / 1_000_000); // ms
                        }
                    } catch (Exception e) {
                        observers.forEach(o -> o.onTaskFailure(task, e));
                        throw new RuntimeException(e);
                    }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println(Thread.currentThread().getName() + " stopped.");
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

}
