package org.example;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ComplexTaskExecutor {

    private final int poolSize;

    public ComplexTaskExecutor(int poolSize) {
        this.poolSize = poolSize;
    }

    public void executeTasks(int numberOfTasks) {

        CyclicBarrier barrier = new CyclicBarrier(numberOfTasks);
        ExecutorService executor = Executors.newFixedThreadPool(numberOfTasks);

        try {
            for (int i = 0; i < numberOfTasks; i++) {
                final int taskId = i;
                executor.submit(() -> {
                    try {
                        new ComplexTask(taskId).execute();
                        barrier.await();
                    } catch (Exception e) {
                        Thread.currentThread().interrupt();
                    }
                });

            }

        } finally {
            executor.shutdown();
        }
    }
}
