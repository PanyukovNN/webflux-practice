package org.panyukovnn.rxjavapractice.model;

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
