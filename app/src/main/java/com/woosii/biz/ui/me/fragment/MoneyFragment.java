package com.woosii.biz.ui.me.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.woosii.biz.R;
import com.woosii.biz.adapter.MoneyAdapter;
import com.woosii.biz.base.BaseFragment;
import com.woosii.biz.base.bean.json.MoneyItemBean;
import com.woosii.biz.base.bean.json.MoneyListBean;
import com.woosii.biz.base.rx.RxBus;
import com.woosii.biz.event.BalanceEvent;
import com.woosii.biz.ui.me.contract.MoneyContract;
import com.woosii.biz.ui.me.presenter.MoneyPresenter;
import com.woosii.biz.utils.RescourseUtil;
import com.woosii.biz.utils.SharedPreferencesUtil;
import com.woosii.biz.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/11/1.
 */

public class MoneyFragment extends BaseFragment<MoneyPresenter> implements MoneyContract.View,SwipeRefreshLayout.OnRefreshListener{
    @Bind(R.id.recycleview)
    RecyclerView recycleview;
    @Bind(R.id.swiperefreshlayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    MoneyAdapter adapter;
    List<MoneyItemBean> list=new ArrayList<>();


    private   int mType=0;
    public static MoneyFragment getInstance(int type) {
        MoneyFragment sf = new MoneyFragment();
        sf.mType = type;
        return sf;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_money;
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

        adapter = new MoneyAdapter(list);

        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        recycleview.setAdapter(adapter);
//        adapter.setOnLoadMoreListener(this, recycleview);
//        adapter.setLoadMoreView(new CustomLoadMoreView());
        Map<String,String> map=new HashMap<>();
        map.put("user_id", SharedPreferencesUtil.getValue(getActivity(),SharedPreferencesUtil.USER_ID,"")+"");
        map.put("type",mType+"");
        mPresenter.getMoneyRecord(map);
    }

    @Override
    public void getMoneyRecordSuccess(MoneyListBean model) {
        if(model.getMoney()!=null){
            RxBus.$().postEvent(new BalanceEvent(model.getMoney()));
        }
        mSwipeRefreshLayout.setRefreshing(false);
        if(model.getChild()!=null){
            list.clear();
            list=model.getChild();
           adapter.setNewData(list);
//            adapter.addData(list);
        }
    }

    @Override
    public void loadFail(String msg) {
        mSwipeRefreshLayout.setRefreshing(false);
        ToastUtil.showShortToast(msg);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onRefresh() {
        Map<String,String> map=new HashMap<>();
        map.put("user_id", SharedPreferencesUtil.getValue(getActivity(),SharedPreferencesUtil.USER_ID,"")+"");
        map.put("type",mType+"");
        mPresenter.getMoneyRecord(map);
    }
}
