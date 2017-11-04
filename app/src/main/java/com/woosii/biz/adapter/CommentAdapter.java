package com.woosii.biz.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.woosii.biz.R;
import com.woosii.biz.base.bean.json.CommentsBean;
import com.woosii.biz.utils.GlideUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/11/2.
 */

public class CommentAdapter extends BaseQuickAdapter<CommentsBean,BaseViewHolder> {
    public CommentAdapter(@Nullable List<CommentsBean> data) {
        super(R.layout.item_comment, data);


    }

    @Override
    protected void convert(BaseViewHolder helper, CommentsBean item) {
        helper.setText(R.id.tv_content, item.getComment());
        helper.setText(R.id.tv_nick_name, item.getNick_name());
        helper.setText(R.id.tv_time, item.getAddtime());


        ImageView imageView = helper.getView(R.id.img_head);
        GlideUtil.getInstance().LoadContextCircleBitmap(mContext, item.getThumb(), imageView, R.drawable.icon_head_normal, R.drawable.icon_head_normal);
    }
}