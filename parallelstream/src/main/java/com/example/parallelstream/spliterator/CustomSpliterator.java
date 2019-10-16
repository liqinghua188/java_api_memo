package com.example.parallelstream.spliterator;

import java.util.Objects;
import java.util.Optional;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class CustomSpliterator {
    public static void main(String[] args) {
        String text = "I am a Chinese I love China";
        Optional.ofNullable(new MySpliteratorText(text).stream().count())
                .ifPresent(System.out::println);

        Optional.ofNullable(new MySpliteratorText(text).parallelStream().count())
                .ifPresent(System.out::println);
    }

    static class MySpliteratorText {
        private final String[] data;

        public MySpliteratorText(String text) {
            Objects.requireNonNull(text, "text can not be null");
            this.data = text.split(" ");
        }

        public Stream stream() {
            return StreamSupport.stream(new MySpliterator(), false);
        }

        public Stream parallelStream() {
            return StreamSupport.stream(new MySpliterator(), true);
        }

        class MySpliterator implements Spliterator<String> {

            private int start;
            private int end;

            public MySpliterator() {
                start = 0;
                end = MySpliteratorText.this.data.length;
            }

            public MySpliterator(int start, int end) {
                this.start = start;
                this.end = end;
            }

            @Override
            public boolean tryAdvance(Consumer<? super String> action) {
                if (start < end) {
                    action.accept(MySpliteratorText.this.data[start++]);
                    return true;
                }
                return false;
            }

            @Override
            public Spliterator<String> trySplit() {
                int mid = (end - start) / 2;
                if (mid <= 1) {
                    return null;
                }

                int left = start;
                int right = start + mid;
                start =  right;
                return new MySpliterator(left, right);
            }

            @Override
            public long estimateSize() {
                return end - start;
            }

            @Override
            public int characteristics() {
                return IMMUTABLE | SIZED | SUBSIZED;
            }
        }
    }
}
