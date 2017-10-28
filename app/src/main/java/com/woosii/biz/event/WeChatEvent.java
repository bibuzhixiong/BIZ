package com.woosii.biz.event;

/**
 * Created by lan on 2017/8/10.
 */

public class WeChatEvent {
    private String code;
    public WeChatEvent(String code){
        this.code=code;
    }

    public String getCode() {
        return code;
    }
}
