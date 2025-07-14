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
                            task.call();
                            observers.forEach(o -> o.onTaskSuccess(task));
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


}
