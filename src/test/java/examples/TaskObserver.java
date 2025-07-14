package examples;

import java.util.concurrent.ExecutorService;

public interface TaskObserver {
    void onTaskSuccess(CommandTask task);
    void onTaskFailure(CommandTask task, Exception e);
}
