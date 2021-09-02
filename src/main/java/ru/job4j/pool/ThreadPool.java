package ru.job4j.pool;

import ru.job4j.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

public class ThreadPool {

    private final List<Thread> threads = new LinkedList<>();

    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(5);

    private boolean shutdown;

    private class InnerThread extends Thread {
        @Override
        public void run() {
            while (!isInterrupted()) {
                try {
                    Runnable runnable = tasks.poll();
                    if (runnable != null) {
                        runnable.run();
                    }
                } catch (InterruptedException e) {
                    interrupt();
                }
            }
        }
    }

    public ThreadPool() {
        int size = Runtime.getRuntime().availableProcessors();
        IntStream.range(0, size).forEach(v -> threads.add(new InnerThread()));
        threads.forEach(Thread::start);
    }

    public void work(Runnable job) throws InterruptedException {
        if (shutdown) {
            throw new RuntimeException("Thread pool completed");
        }
        tasks.offer(job);
    }

    public void shutdown() {
        shutdown = true;
        threads.forEach(Thread::interrupt);
    }
}