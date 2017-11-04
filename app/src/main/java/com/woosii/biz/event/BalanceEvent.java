package com.woosii.biz.event;

/**
 * Created by Administrator on 2017/11/3.
 */

public class BalanceEvent {
    private String money;
    public BalanceEvent(String code){
        this.money=code;
    }

    public String getMoney() {
        return money;
    }
}
