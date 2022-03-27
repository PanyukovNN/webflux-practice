package org.panyukovnn.projectreactor;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

@Slf4j
public class OwnSubscribers {

    public static void main(String[] args) {
        Flux<String> flux = Flux.just("Hello", "world", "!");
        flux.subscribe(simpleSubscriber());
        flux.subscribe(createBaseSubscriber());
    }

    private static Subscriber<String> simpleSubscriber() {
        return new Subscriber<String>() {
            private volatile Subscription subscription;

            @Override
            public void onSubscribe(Subscription s) {
                subscription = s;
                log.info("initial request for 1 element");
                subscription.request(1);
            }

            @Override
            public void onNext(String s) {
                log.info("onNext: {}", s);
                log.info("initial request for 1 element");
                subscription.request(1);
            }

            @Override
            public void onError(Throwable t) {
                log.warn("onError: {}", t.getMessage());
            }

            @Override
            public void onComplete() {
                log.info("onComplete");
            }
        };
    }

    private static Subscriber<String> createBaseSubscriber() {
        return new MySubscriber<>();
    }

    private static class MySubscriber<T> extends BaseSubscriber<T> {
        @Override
        public void hookOnSubscribe(Subscription subscription) {
            log.info("initial request for 1 element");
            request(1);
        }

        @Override
        public void hookOnNext(T value) {
            log.info("onNext: {}", value);
            log.info("requesting 1 more element");
            request(1);
        }
    }
}
