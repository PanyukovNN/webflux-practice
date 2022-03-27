package org.panyukovnn.projectreactor;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.Callable;

public class FluxMonoCreation {

    public static void main(String[] args) {
    }

    private static void createFlux() {
        Flux<String> stream1 = Flux.just("Hello", "world");
        Flux<Integer> stream2 = Flux.fromArray(new Integer[]{1, 2, 3});
        Flux<Integer> stream3 = Flux.fromIterable(Arrays.asList(9, 8, 7));
        Flux<Integer> stream4 = Flux.range(2010, 9);
    }

    private static void createMono() {
        Mono<String> stream5 = Mono.just("One");
        Mono<String> stream6 = Mono.justOrEmpty(null);
        Mono<String> stream7 = Mono.justOrEmpty(Optional.empty());
        Mono<String> stream8 = Mono.fromCallable(() -> httpRequest());
    }

    private static void createEmpty() {
        Flux<String> empty = Flux.empty();
        Mono<String> emptyMono = Mono.empty();
        Flux<String> never = Flux.never();
        empty.subscribe();
    }

    private static void createFromError() {
        Mono<String> error = Mono.error(new RuntimeException("some exception"));
    }

    private static String httpRequest() {
        return "some string from server";
    }
}
