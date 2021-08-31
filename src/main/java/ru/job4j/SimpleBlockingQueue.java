package ru.job4j;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();

    private final int limit;

    public SimpleBlockingQueue(int limit) {
        this.limit = limit;
    }

    public synchronized void offer(T value) {
        while (queue.size() == limit) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        if (queue.size() == 0) {
            notifyAll();
        }
        queue.offer(value);
    }

    public synchronized T poll() {
        if (queue.size() == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        if (queue.size() == limit) {
            notifyAll();
        }
        return queue.poll();
    }

    public static void main(String[] args) throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(5);
        Thread first = new Thread(() -> {
            System.out.println(queue.poll());
            System.out.println(queue.poll());
            System.out.println("FIRST");
        });
        first.start();
        TimeUnit.SECONDS.sleep(2);
        Thread second = new Thread(() -> {
            System.out.println("Second started");
            queue.offer(666);
        });
        second.start();
        TimeUnit.SECONDS.sleep(2);
        Thread fifth = new Thread(() -> {
            System.out.println(queue.poll());
            System.out.println("FIFTH");
        });
        TimeUnit.SECONDS.sleep(2);
        fifth.start();
        Thread third = new Thread(() -> {
            System.out.println("Third started");
            queue.offer(777);
            queue.offer(888);
        });
        third.start();
    }
}