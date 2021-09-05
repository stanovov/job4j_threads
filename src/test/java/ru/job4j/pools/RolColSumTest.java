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
    public void whenCalculatedSequentiallyAndRectangularMatrixOption1() {
        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6}
        };
        RolColSum.Sums[] result = RolColSum.sum(matrix);
        RolColSum.Sums[] excepted = {
                new RolColSum.Sums(6, 5),
                new RolColSum.Sums(15, 7),
                new RolColSum.Sums(0, 9)
        };
        assertThat(result, is(excepted));
    }

    @Test
    public void whenCalculatedSequentiallyAndRectangularMatrixOption2() {
        int[][] matrix = {
                {1, 2},
                {3, 4},
                {5, 6}
        };
        RolColSum.Sums[] result = RolColSum.sum(matrix);
        RolColSum.Sums[] excepted = {
                new RolColSum.Sums(3, 9),
                new RolColSum.Sums(7, 12),
                new RolColSum.Sums(11, 0)
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

    @Test
    public void whenCalculatedAsynchronouslyAndRectangularMatrixOption1()
            throws ExecutionException, InterruptedException {
        int[][] matrix = {
                {1, 2,  3,  4 },
                {5, 6,  7,  8 },
                {9, 10, 11, 12}
        };
        RolColSum.Sums[] result = RolColSum.asyncSum(matrix);
        RolColSum.Sums[] excepted = {
                new RolColSum.Sums(10, 15),
                new RolColSum.Sums(26, 18),
                new RolColSum.Sums(42, 21),
                new RolColSum.Sums(0, 24)
        };
        assertThat(result, is(excepted));
    }

    @Test
    public void whenCalculatedAsynchronouslyAndRectangularMatrixOption2()
            throws ExecutionException, InterruptedException {
        int[][] matrix = {
                {1,  2,  3 },
                {4,  5,  6 },
                {7,  8,  9 },
                {10, 11, 12}
        };
        RolColSum.Sums[] result = RolColSum.asyncSum(matrix);
        RolColSum.Sums[] excepted = {
                new RolColSum.Sums(6, 22),
                new RolColSum.Sums(15, 26),
                new RolColSum.Sums(24, 30),
                new RolColSum.Sums(33, 0)
        };
        assertThat(result, is(excepted));
    }
}