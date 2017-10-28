package com.woosii.biz.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.woosii.biz.R;
import com.woosii.biz.base.bean.json.CollegeBean;

import java.util.List;

/**
 * Created by Administrator on 2017/10/18.
 */

public class SelectCollegeAdapter extends BaseQuickAdapter<CollegeBean,BaseViewHolder> {
    public SelectCollegeAdapter(@Nullable List<CollegeBean> data) {
        super(R.layout.item_select_college,data);


    }
    @Override
    protected void convert(BaseViewHolder helper, CollegeBean item) {
        helper.setText(R.id.tv_content,item.getC_name());
        }


    }

