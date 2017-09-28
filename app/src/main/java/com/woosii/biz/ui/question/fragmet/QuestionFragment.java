package com.woosii.biz.ui.question.fragmet;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.woosii.biz.R;
import com.woosii.biz.adapter.AnswerBranchAdapter;
import com.woosii.biz.base.BaseFragment;
import com.woosii.biz.base.bean.json.NewsBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/9/25.
 */

public class QuestionFragment extends BaseFragment {

    @Bind(R.id.recycleview)
    RecyclerView recycleview;
    List<NewsBean> list=new ArrayList<>();
    private AnswerBranchAdapter answerBranchAdapter;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_question;
    }

    @Override
    protected void initView() {
        //设置布局
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recycleview.setLayoutManager(linearLayoutManager);
        NewsBean baseInfoBean=new NewsBean();
        for(int i=0;i<50;i++){
            list.add(baseInfoBean);
        }


        answerBranchAdapter = new AnswerBranchAdapter(list);
        answerBranchAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        recycleview.setAdapter(answerBranchAdapter);
    }


}
