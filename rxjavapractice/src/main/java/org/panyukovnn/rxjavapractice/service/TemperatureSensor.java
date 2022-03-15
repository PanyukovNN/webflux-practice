package org.panyukovnn.rxjavapractice.service;

import org.panyukovnn.rxjavapractice.model.Temperature;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import rx.Observable;

import javax.annotation.PostConstruct;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Иммитирует датчик температуры
 * Со случайной периодичностью вызываем публикацию события Temperature со случайным значением температуры
 */
@Component
public class TemperatureSensor {

    private final Random rnd = new Random();

    private final Observable<Temperature> dataStream = Observable
            .range(0, Integer.MAX_VALUE)
            .concatMap(tick -> Observable
                .just(tick)
                .delay(rnd.nextInt(5000), TimeUnit.MILLISECONDS)
                .map(tickValue -> this.probe()))
            .publish()
            .refCount();

    private Temperature probe() {
        return new Temperature(16 + rnd.nextGaussian() * 10);
    }

    public Observable<Temperature> temperatureStream() {
        return dataStream;
    }
}
