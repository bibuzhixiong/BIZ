package com.woosii.biz.base.bean.json;

/**
 * Created by Administrator on 2017/9/22.
 */

public class BaseInfoBean {
    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "BaseInfoBean{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }

    public BaseInfoBean(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
