package com.example.completablefuture;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class CustomCompletableFuture {
    public static void main(String[] args) throws InterruptedException {
        Future<String> future = invoke(
                //定义任务
                () -> {
                    try {
                        Thread.sleep(10 * 1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return "I am finished!";
                });

        // 定义回调逻辑
        future.setCompletable(new Completable<String>() {
            @Override
            public void complete(String s) {
                System.out.println("success, " + s );
            }

            @Override
            public void exception(Throwable cause) {
                    System.out.println("error");
            }
        });

        System.out.println("continue do task...");
    }

    public static <T> Future<T> invoke(final Callable<T> callable) {

        AtomicReference<T> result = new AtomicReference<>();
        AtomicBoolean isDone  = new AtomicBoolean(false);

        //直接返回Future
        Future<T> future = new Future<T>() {

            private Completable<T> completable;

            @Override
            public T get() {
                return result.get();
            }

            @Override
            public boolean isDone() {
                return isDone.get();
            }

            @Override
            public Completable<T> getCompletable() {
                return completable;
            }

            @Override
            public void setCompletable(Completable<T> completable) {
                this.completable = completable;
            }
        };

        // 开启新线程执行任务
        new Thread(() -> {
            try {
                T t = callable.action();
                result.set(t);
                isDone.set(true);

                // 任务成功完成后执行成功回调逻辑
                if (future.getCompletable() != null) {
                    future.getCompletable().complete(t);
                }
            } catch (Exception e) {
                // 任务成功失败后执行失败回调逻辑
                if (future.getCompletable() != null) {
                    future.getCompletable().exception(e);
                }
            }

        }).start();

        return future;
    }


    interface Completable<T> {
        void complete(T t);             //成功时回调逻辑
        void exception(Throwable cause);        //异常时回调逻辑
    }

    interface Future<T> {
        T get();
        boolean isDone();
        Completable<T> getCompletable();                            //获得回调逻辑
        void setCompletable(Completable<T> completable);            //设置回调逻辑
    }

    interface Callable<T> {
        T action();                     //任务执行
    }
}

