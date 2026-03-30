package org.example;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ComplexTaskExecutor {

    ConcurrentHashMap<Integer, Integer> results = new ConcurrentHashMap<>();

    private final int poolSize;

    public ComplexTaskExecutor(int poolSize) {
        this.poolSize = poolSize;
    }

    public void executeTasks(int numberOfTasks) {

        CyclicBarrier barrier = new CyclicBarrier(numberOfTasks,() -> {
            int sum = results.values().stream().mapToInt(x -> x).sum();
            System.out.println("Combined result: " + sum);
        } );
        ExecutorService executor = Executors.newFixedThreadPool(numberOfTasks);

        try {
            for (int i = 0; i < numberOfTasks; i++) {
                final int taskId = i;
                executor.submit(() -> {
                    try {
                        int part = new ComplexTask(taskId).execute();
                        results.put(taskId, part);
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
