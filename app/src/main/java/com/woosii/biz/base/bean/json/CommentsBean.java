package com.woosii.biz.base.bean.json;

/**
 * Created by Administrator on 2017/11/2.
 */

public class CommentsBean {
    private String thumb;
    private String nick_name;
    private String comment;
    private String addtime;

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    @Override
    public String toString() {
        return "CommentsBean{" +
                "thumb='" + thumb + '\'' +
                ", nick_name='" + nick_name + '\'' +
                ", comment='" + comment + '\'' +
                ", addtime='" + addtime + '\'' +
                '}';
    }

    public CommentsBean(String thumb, String nick_name, String comment, String addtime) {
        this.thumb = thumb;
        this.nick_name = nick_name;
        this.comment = comment;
        this.addtime = addtime;
    }
}
