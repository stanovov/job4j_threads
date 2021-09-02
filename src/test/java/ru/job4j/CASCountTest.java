package ru.job4j;

import org.junit.Test;

import java.util.stream.IntStream;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class CASCountTest {

    private class ThreadCount extends Thread {

        private final CASCount count;

        private final int times;

        private ThreadCount(CASCount count, int times) {
            this.count = count;
            this.times = times;
        }

        @Override
        public void run() {
            IntStream.range(0, times).forEach((i) -> count.increment());
        }
    }

    @Test
    public void whenExecute2Threads() throws InterruptedException {
        final CASCount count = new CASCount();
        Thread first = new ThreadCount(count, 1000);
        Thread second = new ThreadCount(count, 1000);
        first.start();
        second.start();
        first.join();
        second.join();
        assertThat(count.get(), is(2000));
    }

    @Test
    public void whenExecute3Threads() throws InterruptedException {
        final CASCount count = new CASCount();
        Thread first = new ThreadCount(count, 5000);
        Thread second = new ThreadCount(count, 5000);
        Thread third = new ThreadCount(count, 5000);
        first.start();
        second.start();
        third.start();
        first.join();
        second.join();
        third.join();
        assertThat(count.get(), is(15000));
    }
}