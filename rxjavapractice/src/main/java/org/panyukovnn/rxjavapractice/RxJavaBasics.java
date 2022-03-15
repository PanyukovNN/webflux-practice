package org.panyukovnn.rxjavapractice;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

import java.util.Collections;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class RxJavaBasics {

    public static void main(String[] args) throws InterruptedException {
//        basicExample();
//        creationWays();
//        intervalExample();
//        subscriptionExample();
        zipExample();

        Thread.sleep(5000);
    }

    public static void basicExample() {
        Observable<String> observable = Observable.create(
                sub -> {
                    sub.onNext("Hello, reactive world!");
                    sub.onCompleted();
                }
        );

        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                System.out.println("Done!");
            }

            @Override
            public void onError(Throwable e) {
                System.err.println(e);
            }

            @Override
            public void onNext(String s) {
                System.out.println(s);
            }
        };

        observable.subscribe(subscriber);
        observable.subscribe(subscriber);
        observable.subscribe(subscriber);
    }

    public static void shortVersion() {
        Observable.create(
                sub -> {
                    sub.onNext("Hello, reactive world!");
                    sub.onCompleted();
                }
        ).subscribe(
                System.out::println,
                System.err::println,
                () -> System.out.println("Done!")
        );
    }

    public static void creationWays() {
        Observable.just("1", "2", "3");
        Observable.from(new String[]{"A", "B", "C"});
        Observable.from(Collections.emptyList());

        Observable<String> hello = Observable.fromCallable(() -> "Hello ");
        Future<String> future = Executors.newCachedThreadPool().submit(() -> "World");
        Observable<String> world = Observable.from(future);

        // concat example
        Observable.concat(hello, world, Observable.just("!"))
                .forEach(System.out::print);

        // Будет выведено Hello World!

        System.out.println();
    }

    public static void intervalExample() {
        Observable.interval(1, TimeUnit.SECONDS)
                .subscribe(e -> System.out.println("Received: " + e));
    }

    public static void subscriptionExample() throws InterruptedException {
        Subscription subscription = Observable
                .interval(100, TimeUnit.MILLISECONDS)
                .subscribe(System.out::println);

        Thread.sleep(1000);
        subscription.unsubscribe();
    }

    public static void zipExample() {
        Observable.zip(
                Observable.just("A", "B", "C"),
                Observable.just("1", "2", "3"),
                (x, y) -> x + y
        ).forEach(System.out::println);
    }
}
