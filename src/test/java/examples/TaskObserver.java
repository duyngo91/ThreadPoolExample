package examples;

import java.util.concurrent.ExecutorService;

public interface TaskObserver {
    void onTaskSuccess(CommandTask task);
    void onFinish(CommandTask worker);
    void onTaskFailure(CommandTask task, Exception e);
}
