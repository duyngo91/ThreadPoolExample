package examples;

public class Test {
    public static void main(String[] args) {
        TaskManager.addObserver(new ConsoleTaskLogger());
        TaskManager.addObserver(new FileTaskLogger());
        TaskManager.start();

        for (int i = 0 ;i < 50; i++){
            TaskManager.submit(new Task(i));
        }


        TaskManager.shutdown();
    }
}
