package org.panyukovnn.webfluxpractice.controller;

import org.panyukovnn.webfluxpractice.model.Temperature;
import org.springframework.context.event.EventListener;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Копонент взаимосвязи с клиентом
 */
@RestController
public class TemperatureController {

    private final Set<SseEmitter> clients = new CopyOnWriteArraySet<>();

    @GetMapping(value = "/temperature-stream")
    public SseEmitter events(HttpServletRequest request) {
        SseEmitter emitter = new SseEmitter();
        clients.add(emitter);

        // Удалить emitter из clients в случае ошибки или разрыва соединения
        emitter.onTimeout(() -> clients.remove(emitter));
        emitter.onCompletion(() -> clients.remove(emitter));

        return emitter;
    }

    @Async
    @EventListener
    public void handleMessage(Temperature temperature) {
        List<SseEmitter> deadEmitters = new ArrayList<>();
        clients.forEach(sseEmitter -> {
            try {
                sseEmitter.send(temperature, MediaType.APPLICATION_JSON);
            } catch (Exception ignore) {
                deadEmitters.add(sseEmitter);
            }
        });

        clients.removeAll(deadEmitters);
    }
}
