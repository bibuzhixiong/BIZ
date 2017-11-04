package com.woosii.biz.event;

/**
 * Created by Administrator on 2017/11/4.
 */

public class FreshMyQuestionsEvent {
    private int code;
    public FreshMyQuestionsEvent(int code){
        this.code=code;
    }

    public int getMoney() {
        return code;
    }
}
