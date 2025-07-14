package examples;

public interface TimedTaskObserver extends TaskObserver {
    void onTaskCompleted(CommandTask task, long durationMillis);
}