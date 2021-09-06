package ru.job4j.pools;

import org.junit.Test;

import java.util.concurrent.ExecutionException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class RolColSumTest {

    @Test
    public void whenCalculatedSequentiallyAndSquareMatrix() {
        int[][] matrix = {
                {1, 2},
                {3, 4}
        };
        RolColSum.Sums[] result = RolColSum.sum(matrix);
        RolColSum.Sums[] excepted = {
                new RolColSum.Sums(3, 4),
                new RolColSum.Sums(7, 6)
        };
        assertThat(result, is(excepted));
    }

    @Test
    public void whenCalculatedAsynchronouslyAndSquareMatrix()
            throws ExecutionException, InterruptedException {
        int[][] matrix = {
                {9, 8, 7},
                {6, 5, 4},
                {3, 2, 1}
        };
        RolColSum.Sums[] result = RolColSum.asyncSum(matrix);
        RolColSum.Sums[] excepted = {
                new RolColSum.Sums(24, 18),
                new RolColSum.Sums(15, 15),
                new RolColSum.Sums(6, 12)
        };
        assertThat(result, is(excepted));
    }

}