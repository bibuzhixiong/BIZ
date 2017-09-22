package com.woosii.biz.base.bean.json;

/**
 * Created by Administrator on 2017/9/22.
 */

public class RegisterInfoBean {
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
        return "RegisterInfoBean{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
