package com.woosii.biz.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.woosii.biz.R;
import com.woosii.biz.base.bean.json.NewsBean;
import java.util.List;

/**
 * Created by Administrator on 2017/9/26.
 */

public class CourseListAdapter extends BaseQuickAdapter<NewsBean,BaseViewHolder> {
    public CourseListAdapter(@Nullable List<NewsBean> data) {
        super(R.layout.item_course_list,data);


    }
    @Override
    protected void convert(BaseViewHolder helper, NewsBean item) {

        }


    }