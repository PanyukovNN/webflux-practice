package org.panyukovnn.rxjavapractice.service;

import org.panyukovnn.rxjavapractice.model.Temperature;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import rx.Subscriber;

import java.io.IOException;

/**
 * Реактивный эмиттер
 */
public class RxSseEmitter extends SseEmitter {

    private static final long SSE_SESSION_TIMEOUT = 30 * 60 * 1000L;
    private final Subscriber<Temperature> subscriber;

    public RxSseEmitter() {
        super(SSE_SESSION_TIMEOUT);

        this.subscriber = new Subscriber<>() {
            @Override
            public void onCompleted() {
                // Не делаем ничего, отписываться не надо
            }

            @Override
            public void onError(Throwable e) {
                // Мы знаем что в нашей реализации ошибки не возникнет
            }

            @Override
            public void onNext(Temperature temperature) {
                try {
                    RxSseEmitter.this.send(temperature);
                } catch (IOException e) {
                    unsubscribe();
                }
            }
        };

        // задаем эмиттеру поведение в случае завершения потока данных или ошибки по таймауту
        onCompletion(subscriber::unsubscribe);
        onTimeout(subscriber::unsubscribe);
    }

    public Subscriber<Temperature> getSubscriber() {
        return subscriber;
    }
}
