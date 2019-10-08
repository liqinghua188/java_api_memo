package com.example.future;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class CustomFuture {
    public static void main(String[] args) throws InterruptedException {
        Future<String> future = invoke(() -> {
            try {
                Thread.sleep(10 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "I am finished!";
        });

        System.out.println(future.get());
        System.out.println(future.get());
        System.out.println(future.get());
        // ...
        // ...
        // ...
        while (!future.isDone()) {
            Thread.sleep(100);
        }

        System.out.println(future.get());
    }

    public static <T> Future<T> invoke(Callable<T> callable) {

        AtomicReference<T> result = new AtomicReference<>();
        AtomicBoolean isDone = new AtomicBoolean();

        new Thread(() -> {
            T t = callable.action();
            result.set(t);
            isDone.set(true);
        }).start();

        return new Future<T>() {
            @Override
            public T get() {
                return result.get();
            }

            @Override
            public boolean isDone() {
                return isDone.get();
            }
        };
    }

    interface Future<T> {
        T get();
        boolean isDone();
    }

    interface Callable<T> {
        T action();
    }
}
