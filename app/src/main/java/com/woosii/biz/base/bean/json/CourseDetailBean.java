package com.woosii.biz.base.bean.json;

/**
 * Created by Administrator on 2017/10/9.
 */

public class CourseDetailBean {
    private String class_id;
    private String class_name;
    private String teacher_icon;
    private String time;
    private String teacher;
    private String ssd;
    private String url;
    private String place;
    private String count;

    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public String getTeacher_icon() {
        return teacher_icon;
    }

    public void setTeacher_icon(String teacher_icon) {
        this.teacher_icon = teacher_icon;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getSsd() {
        return ssd;
    }

    public void setSsd(String ssd) {
        this.ssd = ssd;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "CourseDetailBean{" +
                "class_id='" + class_id + '\'' +
                ", class_name='" + class_name + '\'' +
                ", teacher_icon='" + teacher_icon + '\'' +
                ", time='" + time + '\'' +
                ", teacher='" + teacher + '\'' +
                ", ssd='" + ssd + '\'' +
                ", url='" + url + '\'' +
                ", place='" + place + '\'' +
                ", count='" + count + '\'' +
                '}';
    }
}
