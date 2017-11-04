package com.woosii.biz.base.bean.json;

import java.util.List;

/**
 * Created by Administrator on 2017/11/1.
 */

public class MoneyListBean {
    private String money;
    private List<MoneyItemBean> child;

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public List<MoneyItemBean> getChild() {
        return child;
    }

    public void setChild(List<MoneyItemBean> child) {
        this.child = child;
    }

    @Override
    public String toString() {
        return "MoneyListBean{" +
                "money='" + money + '\'' +
                ", child=" + child +
                '}';
    }
}
