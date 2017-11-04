package com.woosii.biz.base.bean.json;

/**
 * Created by Administrator on 2017/11/2.
 */

public class QuestionAnswerDetailBean {
    private int free;
    private int buy;
    private String  problem;
    private String audio;
    private String teacher_name;
    private String teacher_thumb;
    private String add_time;
    private String user_name;

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

    public String getTeacher_name() {
        return teacher_name;
    }

    public void setTeacher_name(String teacher_name) {
        this.teacher_name = teacher_name;
    }

    public String getTeacher_thumb() {
        return teacher_thumb;
    }

    public void setTeacher_thumb(String teacher_thumb) {
        this.teacher_thumb = teacher_thumb;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    @Override
    public String toString() {
        return "QuestionAnswerDetailBean{" +
                "free=" + free +
                ", buy=" + buy +
                ", problem='" + problem + '\'' +
                ", audio='" + audio + '\'' +
                ", teacher_name='" + teacher_name + '\'' +
                ", teacher_thumb='" + teacher_thumb + '\'' +
                ", add_time='" + add_time + '\'' +
                ", user_name='" + user_name + '\'' +
                '}';
    }

    public QuestionAnswerDetailBean(int free, int buy, String problem, String audio, String teacher_name, String teacher_thumb, String add_time, String user_name) {
        this.free = free;
        this.buy = buy;
        this.problem = problem;
        this.audio = audio;
        this.teacher_name = teacher_name;
        this.teacher_thumb = teacher_thumb;
        this.add_time = add_time;
        this.user_name = user_name;
    }
}
