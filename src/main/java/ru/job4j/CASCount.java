package ru.job4j;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {
    private final AtomicReference<Integer> count = new AtomicReference<>(0);

    public void increment() {
        Integer expected;
        do {
            expected = count.get();
        } while (!count.compareAndSet(expected, expected + 1));
    }

    public int get() {
        return count.get();
    }
}