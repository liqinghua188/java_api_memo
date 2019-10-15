package com.example.stream;

import java.util.stream.Stream;

public class StreamFlatMap {
    public static void main(String[] args) {
        String str = "Hello World";
        Stream.of(str)
                .map(s -> s.split(""))
                .flatMap(strArray -> {
                    System.out.println(strArray);
                    return Stream.of(strArray);
                })
                .distinct()
                .forEach(System.out::println);
    }
}
