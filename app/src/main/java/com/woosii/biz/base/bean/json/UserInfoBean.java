package com.woosii.biz.base.bean.json;

/**
 * Created by Administrator on 2017/10/14.
 */

public class UserInfoBean extends BaseInfoBean{

    private String user_id;
    private String nick_name;
    private int gender;
    private String info_tel;
    private String thumb;
    private String user_type;
    private String vip;
    private String c_name;
    private String integral;


    public UserInfoBean(int code, String message) {
        super(code, message);
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getInfo_tel() {
        return info_tel;
    }

    public void setInfo_tel(String info_tel) {
        this.info_tel = info_tel;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getVip() {
        return vip;
    }

    public void setVip(String vip) {
        this.vip = vip;
    }

    public String getC_name() {
        return c_name;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }

    @Override
    public String toString() {
        return "UserInfoBean{" +
                "user_id='" + user_id + '\'' +
                ", nick_name='" + nick_name + '\'' +
                ", gender=" + gender +
                ", info_tel='" + info_tel + '\'' +
                ", thumb='" + thumb + '\'' +
                ", user_type='" + user_type + '\'' +
                ", vip='" + vip + '\'' +
                ", c_name='" + c_name + '\'' +
                ", integral='" + integral + '\'' +
                '}';
    }
}
