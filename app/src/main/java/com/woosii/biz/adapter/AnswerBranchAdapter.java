package com.woosii.biz.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.woosii.biz.R;
import com.woosii.biz.base.bean.json.NewsBean;

import java.util.List;

/**
 * Created by Administrator on 2017/9/27.
 */

public class AnswerBranchAdapter extends BaseQuickAdapter<NewsBean,BaseViewHolder> {
    public AnswerBranchAdapter(@Nullable List<NewsBean> data) {
        super(R.layout.item_question,data);


    }
    @Override
    protected void convert(BaseViewHolder helper, NewsBean item) {

    }


}