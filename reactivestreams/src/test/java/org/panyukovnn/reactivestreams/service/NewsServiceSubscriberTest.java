package org.panyukovnn.reactivestreams.service;

import org.panyukovnn.reactivestreams.model.NewsLetter;
import org.reactivestreams.Subscriber;
import org.reactivestreams.tck.SubscriberBlackboxVerification;
import org.reactivestreams.tck.TestEnvironment;

class NewsServiceSubscriberTest extends SubscriberBlackboxVerification<NewsLetter> {

    public NewsServiceSubscriberTest() {
        super(new TestEnvironment());
    }

    @Override
    public NewsLetter createElement(int element) {
        return new StubNewsLetter(element);
    }

    @Override
    public Subscriber<NewsLetter> createSubscriber() {
        return new NewsServiceSubscriber(5);
    }

    @Override
    public void triggerRequest(Subscriber<? super NewsLetter> subscriber) {
        ((NewsServiceSubscriber) subscriber).eventuallyReadDigest();
    }
}
