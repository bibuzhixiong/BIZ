package com.woosii.biz.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.woosii.biz.R;
import com.woosii.biz.base.bean.json.MoneyItemBean;

import java.util.List;

/**
 * Created by Administrator on 2017/11/1.
 */

public class MoneyAdapter extends BaseQuickAdapter<MoneyItemBean,BaseViewHolder> {
    public MoneyAdapter(@Nullable List<MoneyItemBean> data) {
        super(R.layout.item_money, data);


    }

    @Override
    protected void convert(BaseViewHolder helper, MoneyItemBean item) {
        helper.setText(R.id.tv_time, item.getTime());
        helper.setText(R.id.tv_money, item.getPay_money());

    }
}
