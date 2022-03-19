package org.panyukovnn.reactivestreams.service;

import org.panyukovnn.reactivestreams.model.NewsLetter;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class NewsServiceSubscriber implements Subscriber<NewsLetter> {

    private final Queue<NewsLetter> mailbox = new ConcurrentLinkedQueue<>();
    private final int take;
    private final AtomicInteger remaining = new AtomicInteger();
    private Subscription subscription;

    public NewsServiceSubscriber(int take) {
        this.take = take;
    }

    @Override
    public void onSubscribe(Subscription s) {
        this.subscription = s;

        // При подписке выполняем первый запрос новостей
        subscription.request(take);
    }

    @Override
    public void onNext(NewsLetter newsLetter) {
        mailbox.offer(newsLetter);
    }

    @Override
    public void onError(Throwable t) {
        // Реализация не требуется
    }

    @Override
    public void onComplete() {
        // Реализация не требуется
    }

    /**
     * Пользователь решил прочитать сообщение
     * Если сообщения в очереди закончились, то перед возвратом последнего, будет выполнен запрос следующей группы сообщений
     *
     * @return новостное сообщение
     */
    public Optional<NewsLetter> eventuallyReadDigest() {
        NewsLetter letter = mailbox.poll();
        if (letter != null) {
            if (remaining.decrementAndGet() == 0) {
                subscription.request(take);
                remaining.set(take);
            }

            return Optional.of(letter);
        }

        return Optional.empty();
    }
}
