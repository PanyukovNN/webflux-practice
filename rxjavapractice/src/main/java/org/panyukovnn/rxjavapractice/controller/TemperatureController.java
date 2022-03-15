package org.panyukovnn.rxjavapractice.controller;

import lombok.RequiredArgsConstructor;
import org.panyukovnn.rxjavapractice.service.RxSseEmitter;
import org.panyukovnn.rxjavapractice.service.TemperatureSensor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletRequest;

/**
 * Копонент взаимосвязи с клиентом
 */
@RestController
@RequiredArgsConstructor
public class TemperatureController {

    private final TemperatureSensor temperatureSensor;

    @GetMapping("/temperature-stream")
    public SseEmitter events(HttpServletRequest request) {
        RxSseEmitter emitter = new RxSseEmitter();

        temperatureSensor.temperatureStream()
                .subscribe(emitter.getSubscriber());

        return emitter;
    }
}
