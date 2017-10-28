package com.woosii.biz.base.bean.json;

import java.util.List;

/**
 * Created by Administrator on 2017/10/25.
 */

public class PreViewQuestonsListBean {
    private String count;
    private String comparsion;
    private List<PreViewQuestionsItemBean> array;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getComparsion() {
        return comparsion;
    }

    public void setComparsion(String comparsion) {
        this.comparsion = comparsion;
    }

    public List<PreViewQuestionsItemBean> getArray() {
        return array;
    }

    public void setArray(List<PreViewQuestionsItemBean> array) {
        this.array = array;
    }
}
