package com.example.completablefuture;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class JDKCompletableFuture3 {
    public static void main(String[] args) {

        ExecutorService executor = Executors.newFixedThreadPool(2, r -> {
            Thread t = new Thread(r);
            t.setDaemon(false);
            return t;
        });

        CompletableFuture.supplyAsync(JDKCompletableFuture3::doTask, executor)
                .thenApply(JDKCompletableFuture3::nextTask)
                .whenComplete((r,t) -> {
                    Optional.ofNullable(r).ifPresent(System.out::println);
                    Optional.ofNullable(t).ifPresent(System.out::println);
                });

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
