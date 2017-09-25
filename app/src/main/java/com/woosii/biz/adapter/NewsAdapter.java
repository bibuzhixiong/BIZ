package com.woosii.biz.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.woosii.biz.R;
import com.woosii.biz.base.bean.json.BaseInfoBean;

import java.util.List;

/**
 * Created by Administrator on 2017/9/25.
 */

public class NewsAdapter extends BaseQuickAdapter<BaseInfoBean,BaseViewHolder> {
    public NewsAdapter(@Nullable List<BaseInfoBean> data) {
        super(R.layout.item_news,data);


    }
    @Override
    protected void convert(BaseViewHolder helper, BaseInfoBean item) {
        helper.setText(R.id.tv_content, item.getMessage());


    }

    }