package com.woosii.biz.ui.me.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.woosii.biz.R;
import com.woosii.biz.adapter.MyQuestionAnswerAdapter;
import com.woosii.biz.base.BaseFragment;
import com.woosii.biz.base.bean.json.BasePagingBean;
import com.woosii.biz.base.bean.json.MyQuestionAnswerBean;
import com.woosii.biz.base.rx.RxBus;
import com.woosii.biz.event.FreshMyQuestionsEvent;
import com.woosii.biz.ui.me.activity.QuestionAnswerDetailActivity;
import com.woosii.biz.ui.me.activity.QuestionAnswerDetailNorActivity;
import com.woosii.biz.ui.me.contract.MyQuestionAnswerContract;
import com.woosii.biz.ui.me.presenter.MyQuestionAnswerPresenter;
import com.woosii.biz.utils.RescourseUtil;
import com.woosii.biz.utils.SharedPreferencesUtil;
import com.woosii.biz.widget.CustomLoadMoreView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by Administrator on 2017/10/27.
 */

public class MyQuestionAnswerFragment extends BaseFragment<MyQuestionAnswerPresenter> implements MyQuestionAnswerContract.View,SwipeRefreshLayout.OnRefreshListener,BaseQuickAdapter.RequestLoadMoreListener {

    @Bind(R.id.recycleview)
    RecyclerView recycleview;
    @Bind(R.id.swiperefreshlayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    MyQuestionAnswerAdapter adapter;
    List<MyQuestionAnswerBean> list=new ArrayList<>();

    private int page=1;  //页数
    private int totalPages=1;//总页数

    private int mType=0;
    private   int idtype=0;;
    public static MyQuestionAnswerFragment getInstance(int type) {
        MyQuestionAnswerFragment sf = new MyQuestionAnswerFragment();
        sf.mType = type;
        return sf;
    }
    private View notDataView;

    private Subscription subscription;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my_question_answer;
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

        if((SharedPreferencesUtil.getValue(getActivity(),SharedPreferencesUtil.USER_TYPE,"")+"").equals("1")){
            idtype=1;
        }
        adapter = new MyQuestionAnswerAdapter(list,idtype,mType);

        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        recycleview.setAdapter(adapter);
        adapter.setOnLoadMoreListener(this, recycleview);
        adapter.setLoadMoreView(new CustomLoadMoreView());


        notDataView = getActivity().getLayoutInflater().inflate(R.layout.empty_view, (ViewGroup) recycleview.getParent(), false);
        notDataView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.setEmptyView(R.layout.loading_view, (ViewGroup) recycleview.getParent());
                onRefresh();
            }
        });
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter1, View view, int position) {
                if(mType==0&&idtype==0){
                    return;
                }else if(mType==0&&idtype==1){
                    MyQuestionAnswerBean questionListBean=adapter.getData().get(position);
//                Log.e("TTT","这是1："+questionListBean.toString());
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("question", questionListBean);
                    startActivity(QuestionAnswerDetailNorActivity.class,bundle);
//                 startActivity(QuestionAnswerDetailNorActivity.class);
                    return;
                }else {
                    MyQuestionAnswerBean questionListBean=adapter.getData().get(position);
                    Bundle bundle=new Bundle();
                    bundle.putString("p_id",questionListBean.getP_id());
                    bundle.putString("goods_code","0");
//                bundle.putSerializable("question", questionListBean);
                    startActivity(QuestionAnswerDetailActivity.class,bundle);
                }

            }
        });

        loadData(true);
event();
    }

    private void event() {
        subscription = RxBus.$().toObservable(Object.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object event) {
                        if (event instanceof FreshMyQuestionsEvent) {
                          onRefresh();
                        }

                    }
                });
    }
    //swifload判断是否是下拉加载
    private void loadData(boolean isRefresh) {

        Map<String, String> map = new HashMap<>();
        map.put("psize", "10");
        map.put("type", mType + "");
        map.put("user_id", SharedPreferencesUtil.getValue(getActivity(), SharedPreferencesUtil.USER_ID, "") + "");
        if (isRefresh) {
            map.put("pindex", "1");
            mPresenter.getfreshMyQuestionAnswer(map);
        } else {
            map.put("pindex", page + "");
            mPresenter.getMyQuestionAnswer(map);

        }
    }
    @Override
    public void getMyQuestionAnswerSuccess(BasePagingBean<MyQuestionAnswerBean> model) {
        if(model.getChild()==null){
            adapter.setEmptyView(notDataView);
        }
        totalPages=Integer.parseInt(model.getCount());
        adapter.addData(model.getChild());

        list=adapter.getData();
//        dataLists=newsAdapter.getData();
        adapter.loadMoreComplete();
        Log.e("TTT",totalPages+"总页数");
    }

    @Override
    public void getfreshMyQuestionAnswerSuccess(BasePagingBean<MyQuestionAnswerBean> model) {
        if(model.getChild()==null){
            adapter.setEmptyView(notDataView);
        }
        totalPages=Integer.parseInt(model.getCount());
        mSwipeRefreshLayout.setRefreshing(false);
        list.clear();
        list.addAll(model.getChild());
        adapter.setNewData(list);
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
        if (adapter.getData().size() < 10) {
            adapter.loadMoreEnd(false);
            return;
        }else {
            int maxpage=1;
            if(totalPages%10==0){
                maxpage=totalPages/10-1;
            }else{
                maxpage=totalPages/10;
            }
            if (page > maxpage) {
                adapter.loadMoreEnd(false);
                return;
            }
            page++;
//            Log.e("TTT",page+"好吧");
            loadData( false);
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        super.onDestroy();
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
