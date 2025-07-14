package examples;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public class FileTaskLogger implements TaskObserver{
    private final String logFile = "target/task-log.txt";

    @Override
    public void onTaskSuccess(CommandTask task) {
        logToFile("[DONE] " + task.getTaskName());
    }

    @Override
    public void onTaskFailure(CommandTask task, Exception e) {
        logToFile("[FAILED] " + task.getTaskName() + " – " + e.getMessage());
    }

    private void logToFile(String content) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(logFile, true))) {
            writer.println(LocalDateTime.now() + " | " + content);
        } catch (IOException e) {
            System.err.println("❌ Ghi log thất bại: " + e.getMessage());
        }
    }

}
