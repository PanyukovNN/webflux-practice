package org.panyukovnn.webfluxpractice.service;

import org.panyukovnn.webfluxpractice.model.Temperature;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

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

    private final ApplicationEventPublisher publisher;
    private final Random rnd = new Random();
    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    public TemperatureSensor(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @PostConstruct
    public void startProcessing() {
        this.executor.schedule(this::probe, 1, TimeUnit.SECONDS);
    }

    private void probe() {
        double temperature = 16 + rnd.nextGaussian() * 10;
        publisher.publishEvent(new Temperature(temperature));

        // запланировать следующее чтение спустя случайное число секунд (от 0 до 5)
        executor.schedule(
                this::probe, rnd.nextInt(5000), TimeUnit.MILLISECONDS
        );
    }
}
