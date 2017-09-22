package com.woosii.biz.base;

import android.content.Context;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by lan on 2017/6/22.
 */
public abstract class BasePresenter<V> {
    public Context mContext;
    public V mView;
    protected CompositeSubscription subscription;
    //取消订阅
    protected void unSubscribe() {
        if (subscription != null) {
            subscription.unsubscribe();
        }
    }

    /**
     * @param observable
     * @param subscriber
     * 把Subscription加入到CompositeSubscription
     */
    protected void addSubscrebe(Observable observable, Subscriber subscriber) {
        if (subscription == null) {
            subscription = new CompositeSubscription();
        }
        subscription.add(observable
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber));
    }
    /**
     * @param v\
     * 设置View
     */
    public void setV(V v){
        this.mView=v;
    }
    public void onDestroy(){
        this.mView=null;
        //取消订阅和移除集合
        unSubscribe();
    }
    //子类实现，可做初始化操作
    public abstract void onStart();
}
