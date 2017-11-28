package com.woosii.biz.ui.question.fragmet;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.woosii.biz.R;
import com.woosii.biz.adapter.AnswerBranchAdapter;
import com.woosii.biz.base.BaseFragment;
import com.woosii.biz.base.bean.json.BasePagingBean;
import com.woosii.biz.base.bean.json.QuestionListBean;
import com.woosii.biz.base.rx.RxBus;
import com.woosii.biz.event.ExitAccountEvent;
import com.woosii.biz.event.UserInfoEvent;
import com.woosii.biz.ui.me.activity.QuestionAnswerDetailActivity;
import com.woosii.biz.ui.question.contract.QuestionContract;
import com.woosii.biz.ui.question.presenter.QuestionPresenter;
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
 * Created by Administrator on 2017/9/25.
 */

public class QuestionFragment extends BaseFragment<QuestionPresenter> implements QuestionContract.View ,SwipeRefreshLayout.OnRefreshListener,BaseQuickAdapter.RequestLoadMoreListener{
    @Bind(R.id.swiperefreshlayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.recycleview)
    RecyclerView recycleview;
    List<QuestionListBean> list=new ArrayList<>();
    private AnswerBranchAdapter adapter;


    private int page=1;  //页数
    private int totalPages=1;//总页数
    private int mType=0;
    public static QuestionFragment getInstance(int type) {
        QuestionFragment sf = new QuestionFragment();
        sf.mType = type;
        return sf;
    }
    private Subscription subscription;
    private View notDataView;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_question;
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


        adapter = new AnswerBranchAdapter(list);

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

                QuestionListBean questionListBean=adapter.getData().get(position);
//                Log.e("TTT","这是1："+questionListBean.toString());
                Bundle bundle=new Bundle();
                bundle.putString("p_id",questionListBean.getP_id());
//                bundle.putSerializable("question", questionListBean);
                bundle.putString("goods_code",questionListBean.getGoods_code());
                startActivity(QuestionAnswerDetailActivity.class,bundle);

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
                        if (event instanceof UserInfoEvent) {
                            list.clear();
                            adapter.notifyDataSetChanged();
                            loadData(true);
                        } else if (event instanceof ExitAccountEvent) {
                            list.clear();
                            adapter.notifyDataSetChanged();
                            loadData(true);
                        }

                    }
                });
    }
    //swifload判断是否是下拉加载
    private void loadData(boolean isRefresh) {

        Map<String, String> map = new HashMap<>();
        map.put("psize", "10");
        map.put("user_id", SharedPreferencesUtil.getValue(getActivity(), SharedPreferencesUtil.USER_ID, "") + "");
        if (isRefresh) {
            map.put("pindex", "1");
            mPresenter.getfreshQuestionList(map);
        } else {
            map.put("pindex", page + "");
            mPresenter.getQuestionList(map);

        }
    }


    @Override
    public void getQuestionListSuccess(BasePagingBean<QuestionListBean> model) {
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
    public void getfreshQuestionListSuccess(BasePagingBean<QuestionListBean> model) {
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
