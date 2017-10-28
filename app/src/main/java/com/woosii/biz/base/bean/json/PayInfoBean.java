package com.woosii.biz.base.bean.json;

/**
 * Created by Administrator on 2017/10/13.
 */

public class PayInfoBean {
    private String user_id;
    private String order_sn;
    private String add_time;
    private String subject;
    private String money;
    private String ret_code;
    private String ret_msg;
    private String content;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getRet_code() {
        return ret_code;
    }

    public void setRet_code(String ret_code) {
        this.ret_code = ret_code;
    }

    public String getRet_msg() {
        return ret_msg;
    }

    public void setRet_msg(String ret_msg) {
        this.ret_msg = ret_msg;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "PayInfoBean{" +
                "user_id='" + user_id + '\'' +
                ", order_sn='" + order_sn + '\'' +
                ", add_time='" + add_time + '\'' +
                ", subject='" + subject + '\'' +
                ", money='" + money + '\'' +
                ", ret_code='" + ret_code + '\'' +
                ", ret_msg='" + ret_msg + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
