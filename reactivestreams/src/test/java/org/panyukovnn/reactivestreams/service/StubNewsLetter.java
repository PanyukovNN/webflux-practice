package org.panyukovnn.reactivestreams.service;

import org.panyukovnn.reactivestreams.model.NewsLetter;

import java.util.Collections;

public class StubNewsLetter extends NewsLetter {

    StubNewsLetter(int element) {
        super(String.valueOf(element), null, Collections.emptyList());
    }
}
