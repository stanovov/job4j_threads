package ru.job4j.pools;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class ParallelMergeSortTest {
    @Test
    public void whenParallelSortArray() {
        int[] arr = {1, 3, 2, 4, 6, 5};
        int[] result = ParallelMergeSort.sort(arr);
        int[] excepted = {1, 2, 3, 4, 5, 6};
        assertThat(result, is(excepted));
    }
}