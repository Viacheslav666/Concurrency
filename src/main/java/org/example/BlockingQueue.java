package org.example;

public class BlockingQueue<T> {
    private final T[] buffer;
    private final int capacity;
    private int head;
    private int tail;
    private int count;

    public BlockingQueue(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException();
        }
        this.capacity = capacity;
        this.buffer = (T[]) new Object[capacity];
    }

    public synchronized void enqueue(T item) throws InterruptedException {
        while (count == capacity) {
            wait();
        }
        buffer[tail] = item;
        tail = (tail + 1) % capacity;
        count++;
        notify();
    }

    public synchronized T dequeue() throws InterruptedException {
        while (count == 0) {
            wait();
        }
        T item = buffer[head];
        buffer[head] = null;
        head = (head + 1) % capacity;
        count--;
        notifyAll();
        return item;
    }

    public synchronized int size() {
        return count;
    }
}
