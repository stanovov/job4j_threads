package ru.job4j.pools;

import java.util.Objects;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelSearch<T> extends RecursiveTask<Integer> {

    private final T[] array;

    private final T item;

    private final int from;

    private final int to;

    private ParallelSearch(T[] array, T item, int from, int to) {
        this.array = array;
        this.item = item;
        this.from = from;
        this.to = to;
    }

    public static <T> int search(T[] array, T item) {
        if (array.length <= 10) {
            return linearSearch(array, item);
        }
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(
                new ParallelSearch<>(array, item, 0, array.length - 1)
        );
    }

    private static <T> int linearSearch(T[] array, T item) {
        for (int i = 0; i < array.length; i++) {
            if (Objects.equals(array[i], item)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected Integer compute() {
        if (from == to) {
            return Objects.equals(array[from], item) ? from : -1;
        }
        int mid = (from + to) / 2;
        ParallelSearch<T> leftSearch = new ParallelSearch<>(array, item, from, mid);
        ParallelSearch<T> rightSearch = new ParallelSearch<>(array, item, mid + 1, to);
        leftSearch.fork();
        rightSearch.fork();
        int left = leftSearch.join();
        int right = rightSearch.join();
        return left != -1 ? left : right;
    }
}
