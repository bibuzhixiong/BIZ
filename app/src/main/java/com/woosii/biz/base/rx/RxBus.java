package com.woosii.biz.base.rx;

import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * 用RxJava实现的EventBus
 *
 */
public class RxBus {
    private static RxBus instance;

    private RxBus() {
    }
    public static synchronized RxBus $() {
        if (null == instance) {
            instance = new RxBus();
        }
        return instance;
    }
    private PublishSubject<Object> mEventBus = PublishSubject.create();
    /**
     * 注册事件源
     *
     */
    public void postEvent(Object event) {
        mEventBus.onNext(event);
    }
    /*
         * 订阅事件源
         *
         * @param mObservable
         * @param mAction1
         * @return*/
    public <T> Observable<T> toObservable(Class<T> eventType) {
        return mEventBus.ofType(eventType);
    }

}
