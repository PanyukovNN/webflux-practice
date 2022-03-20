package org.panyukovnn.reactivestreams.model;

import lombok.*;

import java.util.Collection;

/**
 * Новостное сообщение
 */
@Data(staticConstructor = "of")
@Builder(builderClassName = "NewsLetterTemplate", builderMethodName = "template")
@AllArgsConstructor
@With
public class NewsLetter {

    private final @NonNull String title;
    private final          String recipient;
    private final @NonNull Collection<String> digest;
}
