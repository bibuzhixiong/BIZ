package com.woosii.biz.base.bean.json;

/**
 * Created by Administrator on 2017/9/26.
 */

public class NewsBean {
    private String new_id;
    private String new_theme;
    private String new_link;
    private String new_source;
    private String new_author;
    private String new_img;
    private String new_type;
    private String new_state;

    public String getNew_id() {
        return new_id;
    }

    public void setNew_id(String new_id) {
        this.new_id = new_id;
    }

    public String getNew_theme() {
        return new_theme;
    }

    public void setNew_theme(String new_theme) {
        this.new_theme = new_theme;
    }

    public String getNew_link() {
        return new_link;
    }

    public void setNew_link(String new_link) {
        this.new_link = new_link;
    }

    public String getNew_source() {
        return new_source;
    }

    public void setNew_source(String new_source) {
        this.new_source = new_source;
    }

    public String getNew_author() {
        return new_author;
    }

    public void setNew_author(String new_author) {
        this.new_author = new_author;
    }

    public String getNew_img() {
        return new_img;
    }

    public void setNew_img(String new_img) {
        this.new_img = new_img;
    }

    public String getNew_type() {
        return new_type;
    }

    public void setNew_type(String new_type) {
        this.new_type = new_type;
    }

    public String getNew_state() {
        return new_state;
    }

    public void setNew_state(String new_state) {
        this.new_state = new_state;
    }

    @Override
    public String toString() {
        return "NewsBean{" +
                "new_id='" + new_id + '\'' +
                ", new_theme='" + new_theme + '\'' +
                ", new_link='" + new_link + '\'' +
                ", new_source='" + new_source + '\'' +
                ", new_author='" + new_author + '\'' +
                ", new_img='" + new_img + '\'' +
                ", new_type='" + new_type + '\'' +
                ", new_state='" + new_state + '\'' +
                '}';
    }

}
