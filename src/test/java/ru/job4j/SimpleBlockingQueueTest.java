package ru.job4j;

import org.junit.Test;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class SimpleBlockingQueueTest {

    @Test
    public void whenFirstAddToQueueThenRetrieve() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(5);
        List<Integer> result = new ArrayList<>();
        Thread producer = new Thread(
                () -> {
                    queue.offer(1);
                    queue.offer(2);
                    queue.offer(3);
                    queue.offer(4);
                    queue.offer(5);
                }
        );
        Thread consumer = new Thread(
                () -> {
                    try {
                        result.add(queue.poll());
                        result.add(queue.poll());
                        result.add(queue.poll());
                        result.add(queue.poll());
                        result.add(queue.poll());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Thread.currentThread().interrupt();
                    }
                }
        );
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        List<Integer> expected = List.of(1, 2, 3, 4, 5);
        assertThat(result, is(expected));
    }

    @Test
    public void whenAddAndCollectWithDelay() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(3);
        List<Integer> result = new ArrayList<>();
        Thread producer = new Thread(
                () -> {
                    try {
                        for (int i = 1; i <= 5; i++) {
                            queue.offer(i);
                            TimeUnit.MILLISECONDS.sleep(50);
                        }
                    } catch (InterruptedException ignored) {
                    }
                }
        );
        Thread consumer = new Thread(
                () -> {
                    try {
                        TimeUnit.MILLISECONDS.sleep(150);
                        for (int i = 1; i <= 5; i++) {
                            result.add(queue.poll());
                            TimeUnit.MILLISECONDS.sleep(250);
                        }
                    } catch (InterruptedException ignored) {
                    }
                }
        );
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        List<Integer> expected = List.of(1, 2, 3, 4, 5);
        assertThat(result, is(expected));
    }
}