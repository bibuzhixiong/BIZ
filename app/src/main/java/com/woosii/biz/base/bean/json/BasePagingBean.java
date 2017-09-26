package com.woosii.biz.base.bean.json;

import java.util.List;

/**
 * Created by Administrator on 2017/9/26.
 */

public class BasePagingBean<T>   {
    private String PIndex;
    private String PSize;
    private String Count;
    private String PCount;
    private List<T> child;

    public String getPIndex() {
        return PIndex;
    }

    public void setPIndex(String PIndex) {
        this.PIndex = PIndex;
    }

    public String getPSize() {
        return PSize;
    }

    public void setPSize(String PSize) {
        this.PSize = PSize;
    }

    public String getCount() {
        return Count;
    }

    public void setCount(String count) {
        Count = count;
    }

    public String getPCount() {
        return PCount;
    }

    public void setPCount(String PCount) {
        this.PCount = PCount;
    }

    public List<T> getChild() {
        return child;
    }

    public void setChild(List<T> child) {
        this.child = child;
    }

    @Override
    public String toString() {
        return "BasePagingBean{" +
                "PIndex='" + PIndex + '\'' +
                ", PSize='" + PSize + '\'' +
                ", Count='" + Count + '\'' +
                ", PCount='" + PCount + '\'' +
                ", child=" + child +
                '}';
    }
}
