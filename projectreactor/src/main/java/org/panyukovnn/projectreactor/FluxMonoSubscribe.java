package org.panyukovnn.projectreactor;

import lombok.extern.slf4j.Slf4j;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Slf4j
public class FluxMonoSubscribe {

    public static void main(String[] args) throws InterruptedException {
        dispose();
    }

    private static void simpleSubscribe() {
        Flux.just("A", "B", "C")
                .subscribe(
                        data -> log.info("onNext: {}", data),
                        err -> { /* игнорируется */ },
                        () -> log.info("onComplete")
                );
    }

    private static void subscriptionWithManualControl() {
            Flux.range(1, 100)
                    .subscribe(
                            data -> log.info("onNext: {}", data),
                            err -> { /* игнорируется */ },
                            () -> log.info("onComplete"),
                            subscription -> {
                                subscription.request(4);
                                subscription.cancel();
                            }
                    );
    }

    private static void dispose() throws InterruptedException {
        Disposable disposable = Flux.interval(Duration.ofMillis(50))
                .subscribe(data -> log.info("onNext: {}", data));

        Thread.sleep(200);
        disposable.dispose();
    }
}
