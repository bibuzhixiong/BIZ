package com.woosii.biz.ui.course.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.woosii.biz.R;
import com.woosii.biz.adapter.CourseListAdapter;
import com.woosii.biz.base.BaseFragment;
import com.woosii.biz.base.bean.json.NewsBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/9/26.
 */

public class CourseListFragment extends BaseFragment {
    @Bind(R.id.recycleview)
    RecyclerView recycleview;

    List<NewsBean> list=new ArrayList<>();
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
        NewsBean baseInfoBean=new NewsBean();
        for(int i=0;i<50;i++){
            list.add(baseInfoBean);
        }


        courseListAdapter = new CourseListAdapter(list);
        courseListAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        recycleview.setAdapter(courseListAdapter);
    }
}
