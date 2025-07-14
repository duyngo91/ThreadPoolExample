package examples;

import java.util.Set;
import java.util.concurrent.ExecutorService;

public class Test {
    public static void main(String[] args) {
        TaskManager.start();
        TaskManager.addObserver(new TaskObserver() {
            @Override
            public void onTaskSuccess(CommandTask task) {
                System.out.println("✅ Task completed: " + task.getTaskName());
            }

            @Override
            public void onTaskFailure(CommandTask task, Exception  e) {
                System.err.println("❌ Task failed: " + task.getTaskName() + " | " + e.getMessage());
            }
        });
        for (int i = 0 ;i < 50; i++){
            TaskManager.submit(new Task(i));
        }


        TaskManager.shutdown();
    }
}
