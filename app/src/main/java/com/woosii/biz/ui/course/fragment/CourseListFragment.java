package com.woosii.biz.ui.course.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.woosii.biz.R;
import com.woosii.biz.adapter.CourseListAdapter;
import com.woosii.biz.base.BaseFragment;
import com.woosii.biz.base.bean.json.BasePagingBean;
import com.woosii.biz.base.bean.json.CourseListBean;
import com.woosii.biz.ui.course.activity.CourseDetailActivity;
import com.woosii.biz.ui.course.contract.CourseContract;
import com.woosii.biz.ui.course.presenter.CoursePresenter;
import com.woosii.biz.utils.RescourseUtil;
import com.woosii.biz.utils.SharedPreferencesUtil;
import com.woosii.biz.widget.CustomLoadMoreView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/9/26.
 */

public class CourseListFragment extends BaseFragment<CoursePresenter> implements CourseContract.View,SwipeRefreshLayout.OnRefreshListener,BaseQuickAdapter.RequestLoadMoreListener {
    @Bind(R.id.recycleview)
    RecyclerView recycleview;
    @Bind(R.id.swiperefreshlayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    List<CourseListBean> list=new ArrayList<>();
    private CourseListAdapter courseListAdapter;
    private int mType=0;

    private int page=1;  //页数
    private int totalPages=1;//总页数

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

        mSwipeRefreshLayout.setColorSchemeColors(RescourseUtil.getColor(R.color.blue),
                RescourseUtil.getColor(R.color.blue1));
        //设置刷新出现的位置
        mSwipeRefreshLayout.setProgressViewEndTarget(false, 120);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        //设置布局
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recycleview.setLayoutManager(linearLayoutManager);

        courseListAdapter = new CourseListAdapter(list);
        courseListAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        recycleview.setAdapter(courseListAdapter);

        courseListAdapter.setOnLoadMoreListener(this, recycleview);
        courseListAdapter.setLoadMoreView(new CustomLoadMoreView());

        courseListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle=new Bundle();
                bundle.putString("class_id",courseListAdapter.getData().get(position).getClass_id());
                bundle.putString("type",courseListAdapter.getData().get(position).getType());
                bundle.putString("teacher_id",courseListAdapter.getData().get(position).getTeacher_id());
                startActivity(CourseDetailActivity.class,bundle);
            }
        });
        loadData(false);
    }
    //swifload判断是否是下拉加载
    private void loadData(boolean isRefresh){
        Map<String,String> map=new HashMap<>();
        map.put("psize","10");
        map.put("type",mType+"");
        map.put("user_id", SharedPreferencesUtil.getValue(getActivity(),SharedPreferencesUtil.USER_ID,"")+"");
        if(isRefresh){
            map.put("pindex","1");
            mPresenter.getRefreshCourses(map);
        }else{
            map.put("pindex",page+"");
            mPresenter.getCourses(map);

        }


    }


    @Override
    public void getCoursesSuccess(BasePagingBean<CourseListBean> model) {

        totalPages=Integer.parseInt(model.getCount());
        courseListAdapter.addData(model.getChild());

        list=courseListAdapter.getData();
//        dataLists=newsAdapter.getData();
        courseListAdapter.loadMoreComplete();
    }

    @Override
    public void getRefreshCoursesSuccess(BasePagingBean<CourseListBean> model) {
        totalPages=Integer.parseInt(model.getCount());
        mSwipeRefreshLayout.setRefreshing(false);
        list.clear();
        list.addAll(model.getChild());
        courseListAdapter.setNewData(list);
        page=1;
    }

    @Override
    public void loadFail(String msg) {
            mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onRefresh() {
        loadData(true);
    }

    @Override
    public void onLoadMoreRequested() {
        if (courseListAdapter.getData().size() < 10) {
            courseListAdapter.loadMoreEnd(false);
            return;
        }else {
            int maxpage=1;
            if(totalPages%10==0){
                maxpage=totalPages/10-1;
            }else{
                maxpage=totalPages/10;
            }
            if (page > maxpage) {
                courseListAdapter.loadMoreEnd(false);
                return;
            }
            page++;
//            Log.e("TTT",page+"好吧");
            loadData( false);
        }
    }
}
