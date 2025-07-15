package examples;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class TaskManager {
    private static BlockingQueue<CommandTask> queue = new LinkedBlockingQueue<>();
    private static final List<TaskObserver> observers = new CopyOnWriteArrayList<>();
    private static List<Worker> workers = new ArrayList<>();
    private static ExecutorService executors;
    private static TaskMonitor monitor;
    private final static int NUM_WORKER = 3;
    private static volatile boolean isShutdown = false;

    public static synchronized void start(){
        executors = Executors.newFixedThreadPool(NUM_WORKER);
        monitor = new TaskMonitor(queue, new TaskStats());
        monitor.start();

        for(int i = 0 ; i < NUM_WORKER ; i++){
            Worker w = new Worker(queue, observers, new TaskStats());
            executors.submit(w);
            workers.add(w);
        }
    }

    public static void addObserver(TaskObserver observer){
        observers.add(observer);
    }
    public static void submit(CommandTask task){
        if (!isShutdown) {
            queue.add(task);
        } else {
            System.out.println("❌ Không nhận task mới: hệ thống đang shutdown.");
        }
    }


    public static void shutdown() {
        System.out.println("📣 Bắt đầu shutdown...");
        isShutdown = true;

        // Yêu cầu từng worker dừng
        workers.forEach(Worker::stop);



        // Chờ cho ExecutorService hoàn tất các task hiện có
        executors.shutdown();

        try {
            if (!executors.awaitTermination(1, TimeUnit.HOURS)) {
                System.out.println("⚠️ Timeout! Forcing shutdown...");
                executors.shutdownNow();
            }
        } catch (InterruptedException e) {
            executors.shutdownNow();
            Thread.currentThread().interrupt();
        }

        monitor.stop();
        System.out.println("✅ Tất cả worker đã dừng.");
    }


}
