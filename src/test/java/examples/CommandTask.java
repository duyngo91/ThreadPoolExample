package examples;

public abstract class CommandTask implements Runnable {
    protected TaskStats stats;
    abstract String getTaskName();
}
