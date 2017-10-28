package com.woosii.biz.base.bean.json;

/**
 * Created by Administrator on 2017/10/16.
 */

public class AskBean extends BaseInfoBean{
    private String goods_code;

    public String getGoods_code() {
        return goods_code;
    }

    public void setGoods_code(String goods_code) {
        this.goods_code = goods_code;
    }

    @Override
    public String toString() {
        return "AskBean{" +
                "goods_code='" + goods_code + '\'' +
                '}';
    }

    public AskBean(int code, String message, String goods_code) {
        super(code, message);
        this.goods_code = goods_code;
    }


}
