package examples;

public class ConsoleTaskLogger implements TaskObserver{

    @Override
    public void onTaskSuccess(CommandTask task) {
        System.out.println("✅ Task completed: " + task.getTaskName());
    }

    @Override
    public void onTaskFailure(CommandTask task, Exception  e) {
        System.err.println("❌ Task failed: " + task.getTaskName() + " | " + e.getMessage());
    }


}
