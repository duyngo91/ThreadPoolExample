package examples;

public class ConsoleTaskLogger implements TimedTaskObserver{

    @Override
    public void onTaskSuccess(CommandTask task) {
        System.out.println("✅ Task completed: " + task.getTaskName());
    }

    @Override
    public void onTaskFailure(CommandTask task, Exception  e) {
        System.err.println("❌ Task failed: " + task.getTaskName() + " | " + e.getMessage());
    }


    @Override
    public void onTaskCompleted(CommandTask task, long durationMillis) {
        System.out.println("✅ Task completed: " + task.getTaskName() + " | Time: " + durationMillis + "ms");
    }
}
