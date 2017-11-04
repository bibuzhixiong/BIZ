package com.woosii.biz.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.woosii.biz.R;
import com.woosii.biz.base.bean.json.MyQuestionAnswerBean;

import java.util.List;

/**
 * Created by Administrator on 2017/10/27.
 */

public class MyQuestionAnswerAdapter extends BaseQuickAdapter<MyQuestionAnswerBean,BaseViewHolder> {
    private int idtype=0;
    private int isanswer=0;
    public MyQuestionAnswerAdapter(@Nullable List<MyQuestionAnswerBean> data,int idtype,int isanswer) {
        super(R.layout.item_my_question_answer,data);
        this.idtype=idtype;
        this.isanswer=isanswer;



    }
    @Override
    protected void convert(BaseViewHolder helper, MyQuestionAnswerBean item) {
       helper.setText(R.id.tv_question,item.getProblem());
        if(idtype==0){
            helper.setText(R.id.tv_name_time,item.getAdd_time()+"  提问");
        }else{
            helper.setText(R.id.tv_name_time,item.getNick_name()+"  "+item.getAdd_time()+"  提问");
        }
        if(isanswer==0){
            helper.getView(R.id.tv_answered).setVisibility(View.GONE);
        }



    }
}
