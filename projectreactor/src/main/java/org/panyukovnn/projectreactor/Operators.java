package org.panyukovnn.projectreactor;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.Instant;

@Slf4j
public class Operators {

    public static void main(String[] args) {
        indexTimestamp();
    }

    private static void indexTimestamp() {
        Flux.range(2018, 5)
                .timestamp()
                .index()
                .subscribe(e -> log.info("index: {}, ts: {}, value {}",
                        e.getT1(),
                        Instant.ofEpochMilli(e.getT2().getT1()),
                        e.getT2().getT2()));
    }

}
