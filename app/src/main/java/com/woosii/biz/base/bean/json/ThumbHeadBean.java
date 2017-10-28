package com.woosii.biz.base.bean.json;

/**
 * Created by Administrator on 2017/10/23.
 */

public class ThumbHeadBean extends BaseInfoBean {
    private String thumb;

    public ThumbHeadBean(int code, String message) {
        super(code, message);
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    @Override
    public String toString() {
        return "ThumbHeadBean{" +
                "thumb='" + thumb + '\'' +
                '}';
    }
}
