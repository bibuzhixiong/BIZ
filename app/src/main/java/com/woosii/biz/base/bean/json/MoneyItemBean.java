package com.woosii.biz.base.bean.json;

/**
 * Created by Administrator on 2017/11/1.
 */

public class MoneyItemBean {
    private String pay_money;
    private String time;

    public String getPay_money() {
        return pay_money;
    }

    public void setPay_money(String pay_money) {
        this.pay_money = pay_money;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "MoneyItemBean{" +
                "pay_money='" + pay_money + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
