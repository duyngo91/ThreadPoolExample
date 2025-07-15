package examples;

import java.util.Random;

public class Task extends CommandTask {

    private int id;

    public Task(int id) {
        this.id = id;
    }


    @Override
    public void run() {
        Random random = new Random();
        int randomNumber = random.nextInt(10) + 1;
        try {
            Thread.sleep(randomNumber * 100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public String getTaskName() {
        return "Task id : " + id;
    }

}
