package com.woosii.biz.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.woosii.biz.R;
import com.woosii.biz.base.bean.json.MyMessageBean;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Administrator on 2017/10/30.
 */

public class MyMessageAdapter extends BaseQuickAdapter<MyMessageBean,BaseViewHolder> {
    SimpleDateFormat sdf;
    public MyMessageAdapter(@Nullable List<MyMessageBean> data) {
        super(R.layout.item_my_message, data);

//this.sdf=   new SimpleDateFormat("yyyy-MM-dd");

    }

    @Override
    protected void convert(BaseViewHolder helper, MyMessageBean item) {
        helper.setText(R.id.tv_problem, item.getProblem());
        helper.setText(R.id.tv_nick_name, item.getNick_name() );
//        String d = sdf.format(new Date(Long.valueOf(item.getAdd_time()+"000")));

        helper.setText(R.id.tv_time,  item.getAdd_time());
//        ImageView imageView = helper.getView(R.id.img_head);
//        GlideUtil.getInstance().LoadContextCircleBitmap(mContext, item.getThumb(), imageView, R.drawable.icon_head_normal, R.drawable.icon_head_normal);
    }
}