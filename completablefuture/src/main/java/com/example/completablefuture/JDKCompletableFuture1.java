package com.example.completablefuture;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class JDKCompletableFuture1 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();

        new Thread(() -> {
            String result = doTask();
            completableFuture.complete(result);
        }).start();
        System.out.println("main continue do things.");

        completableFuture.whenComplete((r,t) -> {
           Optional.ofNullable(r).ifPresent(System.out::println);
            Optional.ofNullable(t).ifPresent(System.out::println);
        });
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
