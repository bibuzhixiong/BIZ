package com.woosii.biz.base.bean.json;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/29.
 */

public class QuestionListBean implements Serializable {
    private int free;
    private int buy;
    private int a_id;
    private String problem;
    private String audio;
    private String nick_name;
    private String thumb;
    private String add_time;

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

    public int getA_id() {
        return a_id;
    }

    public void setA_id(int a_id) {
        this.a_id = a_id;
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

    @Override
    public String toString() {
        return "QuestionListBean{" +
                "free=" + free +
                ", buy=" + buy +
                ", a_id=" + a_id +
                ", problem='" + problem + '\'' +
                ", audio='" + audio + '\'' +
                ", nick_name='" + nick_name + '\'' +
                ", thumb='" + thumb + '\'' +
                ", add_time='" + add_time + '\'' +
                '}';
    }

    public QuestionListBean(int free, int buy, int a_id, String problem, String audio, String nick_name, String thumb, String add_time) {
        this.free = free;
        this.buy = buy;
        this.a_id = a_id;
        this.problem = problem;
        this.audio = audio;
        this.nick_name = nick_name;
        this.thumb = thumb;
        this.add_time = add_time;
    }
}
