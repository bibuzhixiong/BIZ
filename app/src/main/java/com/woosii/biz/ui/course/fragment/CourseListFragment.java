package com.woosii.biz.ui.course.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.woosii.biz.R;
import com.woosii.biz.adapter.CourseListAdapter;
import com.woosii.biz.base.BaseFragment;
import com.woosii.biz.base.bean.json.CourseListBean;
import com.woosii.biz.ui.course.activity.CourseDetailActivity;
import com.woosii.biz.ui.course.contract.CourseContract;
import com.woosii.biz.ui.course.presenter.CoursePresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/9/26.
 */

public class CourseListFragment extends BaseFragment<CoursePresenter> implements CourseContract.View {
    @Bind(R.id.recycleview)
    RecyclerView recycleview;

    List<CourseListBean> list=new ArrayList<>();
    private CourseListAdapter courseListAdapter;
    private int mType=0;

    public static CourseListFragment getInstance(int type) {
        CourseListFragment sf = new CourseListFragment();
        sf.mType = type;
        return sf;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_course_list;
    }

    @Override
    protected void initView() {
        //设置布局
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recycleview.setLayoutManager(linearLayoutManager);

        courseListAdapter = new CourseListAdapter(list);
        courseListAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        recycleview.setAdapter(courseListAdapter);
        courseListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle=new Bundle();
                bundle.putString("class_id",courseListAdapter.getData().get(position).getClass_id());
                startActivity(CourseDetailActivity.class,bundle);
            }
        });
        loadData();
    }
    private void loadData(){
        Map<String,String> map=new HashMap<>();
        map.put("type","0");
        mPresenter.getCourses(map);
    };

    @Override
    public void getCoursesSuccess(List<CourseListBean> model) {
        list=model;
        courseListAdapter.setNewData(model);
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
