package com.example.parallelstream.spliterator;

import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.IntStream;

public class IntStreamSpliterator {
    public static void main(String[] args) {
        Spliterator.OfInt spliterator = IntStream.rangeClosed(1, 10).spliterator();
        Consumer<Integer> tConsumer = i -> System.out.println(i);
        spliterator.forEachRemaining(tConsumer);
//        spliterator.forEachRemaining(new IntConsumer() {
//            @Override
//            public void accept(int value) {
//                System.out.println(value);
//            }
//        });
    }
}
