package com.example.completablefuture;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

public class JDKCompletableFuture2 {
    public static void main(String[] args) throws InterruptedException {
        AtomicBoolean finished = new AtomicBoolean(false);
        CompletableFuture.supplyAsync(JDKCompletableFuture2::doTask)
                .whenComplete((r,t) -> {
                    Optional.ofNullable(r).ifPresent(System.out::println);
                    Optional.ofNullable(t).ifPresent(System.out::println);
                    finished.set(true);
                });
        System.out.println("main continue do things.");
        while (!finished.get()) {
            Thread.sleep(10);
        }
    }

    public static String doTask() {
        try {
            Thread.sleep(10 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "async task finished";
    }
}
