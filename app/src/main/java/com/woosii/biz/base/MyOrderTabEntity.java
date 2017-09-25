package com.woosii.biz.base;

import com.flyco.tablayout.listener.CustomTabEntity;

public class MyOrderTabEntity implements CustomTabEntity {
    public String title;

    public MyOrderTabEntity(String title) {
        this.title = title;
    }

    @Override
    public String getTabTitle() {
        return title;
    }

    @Override
    public int getTabSelectedIcon() {
        return 0;
    }

    @Override
    public int getTabUnselectedIcon() {
        return 0;
    }


}
