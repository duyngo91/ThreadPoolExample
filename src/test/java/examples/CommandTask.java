package examples;

import java.util.concurrent.Callable;

public interface CommandTask extends Callable<Void> {

    String getTaskName();
}
