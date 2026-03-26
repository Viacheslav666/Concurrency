package org.example;


import java.util.concurrent.LinkedBlockingQueue;

public class Main {
    public static void main(String[] args) throws InterruptedException {


        BlockingQueue<Integer> blockingQueue = new BlockingQueue<>(2);

        Thread producer = new Thread(() -> {
            try {
                blockingQueue.enqueue(1);
                blockingQueue.enqueue(2);
                System.out.println("producer: положил 1, 2");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        Thread consumer = new Thread(() -> {
            try {
                System.out.println("consumer: взял " + blockingQueue.dequeue());
                System.out.println("consumer: взял " + blockingQueue.dequeue());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        consumer.start();
        Thread.sleep(100);
        producer.start();
        producer.join();
        consumer.join();
        
    }
}
