package com.woosii.biz.base.bean.json;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/27.
 */

public class MyQuestionAnswerBean implements Serializable {
    private String p_id;
    private String problem;
    private String nick_name;
    private  String add_time;

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

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    @Override
    public String toString() {
        return "MyQuestionAnswerBean{" +
                "p_id='" + p_id + '\'' +
                ", problem='" + problem + '\'' +
                ", nick_name='" + nick_name + '\'' +
                ", add_time='" + add_time + '\'' +
                '}';
    }

    public MyQuestionAnswerBean(String p_id, String problem, String nick_name, String add_time) {
        this.p_id = p_id;
        this.problem = problem;
        this.nick_name = nick_name;
        this.add_time = add_time;
    }
}

