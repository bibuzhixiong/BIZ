package com.woosii.biz.ui.me.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.woosii.biz.R;
import com.woosii.biz.base.BaseActivity;
import com.woosii.biz.base.BaseToolbar;
import com.woosii.biz.base.MyOrderTabEntity;
import com.woosii.biz.base.rx.RxBus;
import com.woosii.biz.event.BalanceEvent;
import com.woosii.biz.ui.me.fragment.MoneyFragment;
import com.woosii.biz.utils.ToastUtil;

import java.util.ArrayList;

import butterknife.Bind;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by Administrator on 2017/11/1.
 */

public class MyWalletActivity extends BaseActivity {
    @Bind(R.id.commontablayout)
    CommonTabLayout commonTabLayout;
    @Bind(R.id.vp)
    ViewPager viewPager;
    @Bind(R.id.toolbar)
    BaseToolbar toolbar;
    @Bind(R.id.tv_money)
    TextView tvMoney;
    @Bind(R.id.tv_withdraw)
    TextView tvWithdraw;


    private String[] mTitles = {"收入明细", "支出明细"};
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private FragmentPagerAdapter mAdapter;
    private int[] mType = {0, 1, 2};

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_wallet;
    }

    @Override
    protected void initToolBar() {
        toolbar.setLeftButtonOnClickLinster(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish_Activity(MyWalletActivity.this);
            }
        });
    }

    @Override
    protected void initView() {
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new MyOrderTabEntity(mTitles[i]));
        }
        for (int type : mType) {
            mFragments.add(MoneyFragment.getInstance(type));
        }

//        mFragments.add(meFragment);
        commonTabLayout.setTabData(mTabEntities);

        mAdapter = new FragmentPagerAdapter(MyWalletActivity.this.getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return mFragments.size();
            }

            @Override
            public Fragment getItem(int position) {
             /*   if(detailsYarnFragment!=null&&position==1){
                    detailsYarnFragment.isVisible();
                }*/
                return mFragments.get(position);
            }
        };
        viewPager.setAdapter(mAdapter);
        viewPager.setOffscreenPageLimit(5);
//        commonTabLayout.setTabData(mTabEntities, getActivity(),R.id.fl_change, mFragments);
        // 对ViewPager设置滑动监听
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            int lastPosition;

            @Override
            public void onPageSelected(int position) {
                // 页面被选中

                // 修改position
                position = position % mFragments.size();

                // 替换位置
                lastPosition = position;
                commonTabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //导航栏设置监听
        commonTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                commonTabLayout.setCurrentTab(position);
                viewPager.setCurrentItem(position);

            }

            @Override
            public void onTabReselect(int position) {

            }
        });

        tvWithdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String money=tvMoney.getText().toString().trim();
                double mon=Double.parseDouble(money);
                if(mon<=0){
                    ToastUtil.showShortToast("您暂无可提现金额");
                    return;
                }
                Bundle bundle=new Bundle();
                bundle.putString("money",money);
                startActivity(WithDrawActivity.class,bundle);
            }
        });
        event();
    }
    private Subscription subscription;
    private void event() {
        subscription = RxBus.$().toObservable(BalanceEvent.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<BalanceEvent>() {
                    @Override
                    public void call(BalanceEvent event) {
                        tvMoney.setText(event.getMoney());
                    }
                });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscription != null&&!subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
