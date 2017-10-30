package com.woosii.biz.ui.me.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.woosii.biz.R;
import com.woosii.biz.adapter.MyMessageAdapter;
import com.woosii.biz.base.BaseActivity;
import com.woosii.biz.base.BaseToolbar;
import com.woosii.biz.base.bean.json.MyMessageBean;
import com.woosii.biz.ui.me.contract.MyMessageContract;
import com.woosii.biz.ui.me.presenter.MyMessagePresenter;
import com.woosii.biz.utils.SharedPreferencesUtil;
import com.woosii.biz.widget.CustomLoadMoreView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/10/30.
 */

public class MyMessageActivity extends BaseActivity<MyMessagePresenter> implements MyMessageContract.View {
    @Bind(R.id.toolbar)
    BaseToolbar toolbar;
    @Bind(R.id.recycleview)
    RecyclerView recycleview;

    private MyMessageAdapter adapter;
    private List<MyMessageBean> list=new ArrayList<>();
    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_message;
    }

    @Override
    protected void initToolBar() {
        toolbar.setLeftButtonOnClickLinster(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish_Activity(MyMessageActivity.this);
            }
        });
    }

    @Override
    protected void initView() {
        //设置布局
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MyMessageActivity.this);
        recycleview.setLayoutManager(linearLayoutManager);


        adapter = new MyMessageAdapter(list);

        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        recycleview.setAdapter(adapter);
//        adapter.setOnLoadMoreListener(this, recycleview);
        adapter.setLoadMoreView(new CustomLoadMoreView());
        Map<String,String> map=new HashMap<>();
        map.put("user_id", SharedPreferencesUtil.getValue(MyMessageActivity.this,SharedPreferencesUtil.USER_ID,"")+"");
        mPresenter.getMyMessage(map);


    }

    @Override
    public void getMyMessageSuccess(List<MyMessageBean> model) {
        adapter.addData(model);
    }

    @Override
    public void loadFail(String msg) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }


}
