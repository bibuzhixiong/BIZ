package com.woosii.biz.event;

import com.woosii.biz.base.bean.LoginBean;

/**
 * Created by Administrator on 2017/10/14   .
 */

public class UserInfoEvent {
    private LoginBean userInfoBean;

    public UserInfoEvent(LoginBean userInfoBean) {
            this.userInfoBean=userInfoBean;
    }
    public LoginBean getUserInfoBean(){
        return userInfoBean;
    }
}
