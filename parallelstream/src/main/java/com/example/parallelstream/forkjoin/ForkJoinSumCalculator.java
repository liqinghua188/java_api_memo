package com.example.parallelstream.forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

public class ForkJoinSumCalculator extends RecursiveTask<Long> {

    private final long[] numbers;
    private final int start;
    private final int end;

    public static final long THRESHOLD = 10_000;

    public ForkJoinSumCalculator(long[] numbers, int start, int end) {
        this.numbers = numbers;
        this.start = start;
        this.end = end;
    }

    public ForkJoinSumCalculator(long[] numbers) {
        this(numbers, 0, numbers.length);
    }

    protected Long compute() {
        int length = end - start;
        if (length < THRESHOLD) {
            return calculateSequenctially();
        } else {
            ForkJoinSumCalculator leftTask = new ForkJoinSumCalculator(numbers, start, start + length/2);
            leftTask.fork();
            ForkJoinSumCalculator rightTask = new ForkJoinSumCalculator(numbers, start + length/2, end);
            long rightResult = rightTask.compute();
            long leftResult = leftTask.join();
            return leftResult + rightResult;
        }
    }

    private long calculateSequenctially() {
        long sum = 0L;
        for (int i=start; i<end; i++) {
            sum += numbers[i];
        }
        return sum;
    }

    public static void main(String[] args) {
        long[] numbers = LongStream.rangeClosed(1, 100_000).toArray();
        ForkJoinSumCalculator forkJoinSumCalculator = new ForkJoinSumCalculator(numbers);
        Long result = new ForkJoinPool().invoke(forkJoinSumCalculator);
        System.out.println("result:" + result);
    }
}