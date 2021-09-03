package ru.job4j.pools;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class MergeSortTest {
    @Test
    public void whenMergeNotEmptyArrays() {
        int[] left = {1, 3, 5};
        int[] right = {2, 4};
        int[] result = MergeSort.merge(left, right);
        int[] expected = {1, 2, 3, 4, 5};
        assertThat(result, is(expected));
    }

    @Test
    public void whenMergeLeftArrayEmpty() {
        int[] left = {};
        int[] right = {1, 2};
        int[] result = MergeSort.merge(left, right);
        int[] expected = {1, 2};
        assertThat(result, is(expected));
    }

    @Test
    public void whenMergeRightArrayEmpty() {
        int[] left = {3, 4};
        int[] right = {};
        int[] result = MergeSort.merge(left, right);
        int[] expected = {3, 4};
        assertThat(result, is(expected));
    }

    @Test
    public void whenMergeAllArraysEmpty() {
        int[] left = {};
        int[] right = {};
        int[] result = MergeSort.merge(left, right);
        int[] expected = {};
        assertThat(result, is(expected));
    }

    @Test
    public void whenSortArray() {
        int[] arr = {1, 3, 2, 4, 6, 5};
        int[] result = MergeSort.sort(arr);
        int[] excepted = {1, 2, 3, 4, 5, 6};
        assertThat(result, is(excepted));
    }
}