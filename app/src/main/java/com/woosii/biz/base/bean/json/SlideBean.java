package com.woosii.biz.base.bean.json;

/**
 * Created by Administrator on 2017/11/11.
 */

public class SlideBean {
    private String ad_id;
    private String img;
    private String url;
    private String title;


    public String getAd_id() {
        return ad_id;
    }

    public void setAd_id(String ad_id) {
        this.ad_id = ad_id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "SlideBean{" +
                "ad_id='" + ad_id + '\'' +
                ", img='" + img + '\'' +
                ", url='" + url + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
