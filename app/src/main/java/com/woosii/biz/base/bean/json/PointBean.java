package com.woosii.biz.base.bean.json;

/**
 * Created by Administrator on 2017/10/21.
 */

public class PointBean extends BaseInfoBean{
    private String count;

    public PointBean(int code, String message) {
        super(code, message);
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
