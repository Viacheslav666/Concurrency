package org.example;

public class ComplexTask {

    private final int taskId;

    public ComplexTask(int taskId) {
        this.taskId = taskId;
    }
    public int getTaskId() {
        return taskId;
    }

    public int execute(){
        try {
            Thread.sleep(50 +  taskId * 20);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return 0;
        }
        return  (taskId + 1);
    }


}
