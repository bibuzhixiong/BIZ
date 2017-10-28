package com.woosii.biz.base.bean;

import com.woosii.biz.base.bean.json.BaseInfoBean;

/**
 * Created by Administrator on 2017/9/22.
 */

public class LoginBean extends BaseInfoBean{
    private String token;
    private String user_id;

    public LoginBean(int code, String message) {
        super(code, message);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "LoginBean{" +
                "token='" + token + '\'' +
                ", user_id='" + user_id + '\'' +
                '}';
    }

    public LoginBean(int code, String message, String token, String user_id) {
        super(code, message);
        this.token = token;
        this.user_id = user_id;
    }


}
