package com.example.completablefuture;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class JDKCompletableFuture4 {
    public static void main(String[] args) {

        ExecutorService executor = Executors.newFixedThreadPool(2, r -> {
            Thread t = new Thread(r);
            t.setDaemon(false);
            return t;
        });

        List<Integer> ids = Arrays.asList(1, 2, 3, 4, 5);
        List<Double> result = ids
                .stream()
                .map(i -> CompletableFuture.supplyAsync(JDKCompletableFuture4::doTask, executor))
                .map(c -> c.thenApply(JDKCompletableFuture4::nextTask))
                .map(CompletableFuture::join)
                .collect(Collectors.toList());

        System.out.println(result);

        executor.shutdown();
    }

    public static double doTask() {
        Random random = new Random(System.currentTimeMillis());
        try {
            Thread.sleep(10 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        double result = random.nextDouble();
        System.out.println(result);
        return result;
    }

    public static double nextTask(double value) {
        try {
            Thread.sleep(3 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return value * 10d;
    }
}
