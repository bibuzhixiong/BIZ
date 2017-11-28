package com.woosii.biz.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.woosii.biz.R;
import com.woosii.biz.base.bean.json.QuestionListBean;
import com.woosii.biz.utils.GlideUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/9/27.
 */

public class AnswerBranchAdapter extends BaseQuickAdapter<QuestionListBean,BaseViewHolder> {
    public AnswerBranchAdapter(@Nullable List<QuestionListBean> data) {
        super(R.layout.item_question,data);


    }
    @Override
    protected void convert(BaseViewHolder helper, QuestionListBean item) {
            helper.setText(R.id.tv_title,item.getProblem());
        helper.setText(R.id.tv_name,item.getNick_name()+"  的回答");
        helper.setText(R.id.tv_time,item.getAdd_time()+"  提问");

        if(item.getFree()==1||item.getFree_type().equals("0")){
            helper.setText(R.id.tv_free_or_no,"点击语音播放");
        }else{
            helper.setText(R.id.tv_free_or_no,"语音播放需要"+item.getMoney()+"元");
        }
        ImageView imageView=helper.getView(R.id.img_head);
        GlideUtil.getInstance().LoadContextCircleBitmap(mContext,item.getThumb(),imageView,R.drawable.icon_head_normal,R.drawable.icon_head_normal);
    }


}