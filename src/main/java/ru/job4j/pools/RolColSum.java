package ru.job4j.pools;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {

    public static class Sums {

        private final int rowSum;

        private final int colSum;

        public Sums(int rowSum, int colSum) {
            this.rowSum = rowSum;
            this.colSum = colSum;
        }

        @Override
        public String toString() {
            return "Sums{"
                    + "rowSum=" + rowSum
                    + ", colSum=" + colSum
                    + '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Sums sums = (Sums) o;
            return rowSum == sums.rowSum && colSum == sums.colSum;
        }

        @Override
        public int hashCode() {
            return Objects.hash(rowSum, colSum);
        }
    }

    public static Sums[] sum(int[][] matrix) {
        validateMatrix(matrix);
        int size = Math.max(matrix.length, matrix[0].length);
        final Sums[] sums = new Sums[size];
        for (int i = 0; i < sums.length; i++) {
            sums[i] = getSums(matrix, i);
        }
        return sums;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        validateMatrix(matrix);
        final int size = Math.max(matrix.length, matrix[0].length);
        Map<Integer, CompletableFuture<Sums>> futures = new HashMap<>();
        for (int i = 0; i < size; i++) {
            futures.put(i, getTask(matrix, i));
        }
        final Sums[] sums = new Sums[size];
        for (Integer key : futures.keySet()) {
            sums[key] = futures.get(key).get();
        }
        return sums;
    }

    private static void validateMatrix(int[][] matrix) {
        if (matrix.length == 0) {
            throw new IllegalArgumentException();
        }
        if (matrix[0].length == 0) {
            throw new IllegalArgumentException();
        }
        int len = matrix[0].length;
        for (int i = 1; i < matrix.length; i++) {
            if (matrix[i].length != len) {
                throw new IllegalArgumentException();
            }
        }
    }

    private static Sums getSums(int[][] matrix, int i) {
        int rowSum = 0;
        if (i < matrix.length) {
            for (int j = 0; j < matrix[i].length; j++) {
                rowSum += matrix[i][j];
            }
        }
        int colSum = 0;
        for (int j = 0; j < matrix.length; j++) {
            if (i < matrix[j].length) {
                colSum += matrix[j][i];
            }
        }
        return new Sums(rowSum, colSum);
    }

    private static CompletableFuture<Sums> getTask(int[][] matrix, int i) {
        return CompletableFuture.supplyAsync(() -> getSums(matrix, i));
    }
}
