package org.panyukovnn.webfluxpractice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Событие, содержащиее текущую температуру
 */
@Getter
@Setter
@AllArgsConstructor
public class Temperature {

    private final double value;
}
