package com.woosii.biz.base.bean.json;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/29.
 */

public class QuestionListBean implements Serializable {
    private int free;
    private int buy;
    private String p_id;
    private String problem;
    private String audio;
    private String nick_name;
    private String thumb;
    private String add_time;
    private String goods_code;

    public int getFree() {
        return free;
    }

    public void setFree(int free) {
        this.free = free;
    }

    public int getBuy() {
        return buy;
    }

    public void setBuy(int buy) {
        this.buy = buy;
    }

    public String getP_id() {
        return p_id;
    }

    public void setP_id(String p_id) {
        this.p_id = p_id;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getGoods_code() {
        return goods_code;
    }

    public void setGoods_code(String goods_code) {
        this.goods_code = goods_code;
    }

    @Override
    public String toString() {
        return "QuestionListBean{" +
                "free=" + free +
                ", buy=" + buy +
                ", p_id='" + p_id + '\'' +
                ", problem='" + problem + '\'' +
                ", audio='" + audio + '\'' +
                ", nick_name='" + nick_name + '\'' +
                ", thumb='" + thumb + '\'' +
                ", add_time='" + add_time + '\'' +
                ", goods_code='" + goods_code + '\'' +
                '}';
    }

    public QuestionListBean(int free, int buy, String p_id, String problem, String audio, String nick_name, String thumb, String add_time) {
        this.free = free;
        this.buy = buy;
        this.p_id = p_id;
        this.problem = problem;
        this.audio = audio;
        this.nick_name = nick_name;
        this.thumb = thumb;
        this.add_time = add_time;
    }

    public QuestionListBean(int free, int buy, String p_id, String problem, String audio, String nick_name, String thumb, String add_time, String goods_code) {
        this.free = free;
        this.buy = buy;
        this.p_id = p_id;
        this.problem = problem;
        this.audio = audio;
        this.nick_name = nick_name;
        this.thumb = thumb;
        this.add_time = add_time;
        this.goods_code = goods_code;
    }
}
