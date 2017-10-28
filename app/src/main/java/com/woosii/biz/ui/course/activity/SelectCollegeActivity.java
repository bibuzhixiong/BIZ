package com.woosii.biz.ui.course.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.woosii.biz.R;
import com.woosii.biz.adapter.SelectCollegeAdapter;
import com.woosii.biz.base.BaseActivity;
import com.woosii.biz.base.BaseToolbar;
import com.woosii.biz.base.bean.json.CollegeBean;
import com.woosii.biz.common.dialog.LoadingDialog;
import com.woosii.biz.ui.course.contract.SelectCollegeContract;
import com.woosii.biz.ui.course.presenter.SellectCollegePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/10/18.
 */

public class SelectCollegeActivity extends BaseActivity<SellectCollegePresenter> implements SelectCollegeContract.View {
    @Bind(R.id.toolbar)
    BaseToolbar toolbar;
    @Bind(R.id.recycleview)
    RecyclerView recycleview;
    SelectCollegeAdapter selectCollegeAdapter;
    List<CollegeBean> list=new ArrayList<>();
    private LoadingDialog mLoadingDialog;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_college;
    }

    @Override
    protected void initToolBar() {
        toolbar.setLeftButtonOnClickLinster(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish_Activity(SelectCollegeActivity.this);
            }
        });
    }

    @Override
    protected void initView() {
        //设置布局
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SelectCollegeActivity.this);
        recycleview.setLayoutManager(linearLayoutManager);

        selectCollegeAdapter = new SelectCollegeAdapter(list);
        selectCollegeAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        recycleview.setAdapter(selectCollegeAdapter);
        selectCollegeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                Intent intent = new Intent(SelectCollegeActivity.this, OpenMembershipActivity.class);
                String college = selectCollegeAdapter.getData().get(position).getC_name();
                String class_id = selectCollegeAdapter.getData().get(position).getC_id();
                intent.putExtra("college", college);
                intent.putExtra("class_id", class_id);
                setResult(RESULT_OK, intent);
             finish_Activity(SelectCollegeActivity.this);
            }
        });
        mPresenter.getCollege();
    }


    @Override
    public void getCollegeSuccess(List<CollegeBean> model) {
        selectCollegeAdapter.addData(model);
    }

    @Override
    public void loadFail(String msg) {

    }
    @Override
    public void showLoading() {
        mLoadingDialog = new LoadingDialog(SelectCollegeActivity.this, "加载中...", false);
        mLoadingDialog.show();
    }

    @Override
    public void hideLoading() {
        if (mLoadingDialog != null) {
            mLoadingDialog.cancelDialog();
        }
    }
}
