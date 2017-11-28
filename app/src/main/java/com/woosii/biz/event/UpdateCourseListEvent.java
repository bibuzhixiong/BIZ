package com.woosii.biz.event;

/**
 * Created by Administrator on 2017/11/11.
 */

public class UpdateCourseListEvent {
    private int position;



    public UpdateCourseListEvent(int position){
        this.position=position;

    }
    public int getPosition() {
        return position;
    }
}
