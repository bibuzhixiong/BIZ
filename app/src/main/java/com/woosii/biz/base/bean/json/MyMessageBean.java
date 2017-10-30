package com.woosii.biz.base.bean.json;

/**
 * Created by Administrator on 2017/10/30.
 */

public class MyMessageBean {
    private String p_id;
    private String problem;
    private String add_time;
    private String nick_name;
    private String thumb;

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

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
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

    @Override
    public String toString() {
        return "MyMessageBean{" +
                "p_id='" + p_id + '\'' +
                ", problem='" + problem + '\'' +
                ", add_time='" + add_time + '\'' +
                ", nick_name='" + nick_name + '\'' +
                ", thumb='" + thumb + '\'' +
                '}';
    }
}
