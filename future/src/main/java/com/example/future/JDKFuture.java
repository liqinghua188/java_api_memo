package com.example.future;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class JDKFuture {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        // 1. 定义线程池
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        // 2. 定义任务
        //3. 将任务提交给线程池执行，返回结果即为Future
        Future<String> future = executorService.submit(() -> {
            try {
                Thread.sleep(10 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "I am finished!";
        });

        while (!future.isDone()) {
            Thread.sleep(10);
        }

        System.out.println(future.get());

        executorService.shutdown();
    }
}
