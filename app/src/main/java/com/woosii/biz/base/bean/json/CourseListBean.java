package com.woosii.biz.base.bean.json;

/**
 * Created by Administrator on 2017/9/28.
 */

public class CourseListBean {
    private String class_id;
    private String class_name;
    private String thumb;
    private String time;
    private String type;
    private String teacher_id;

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

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(String teacher_id) {
        this.teacher_id = teacher_id;
    }

    @Override
    public String toString() {
        return "CourseListBean{" +
                "class_id='" + class_id + '\'' +
                ", class_name='" + class_name + '\'' +
                ", thumb='" + thumb + '\'' +
                ", time='" + time + '\'' +
                ", type='" + type + '\'' +
                ", teacher_id='" + teacher_id + '\'' +
                '}';
    }
}
