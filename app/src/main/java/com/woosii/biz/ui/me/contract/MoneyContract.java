package com.woosii.biz.ui.me.contract;

import com.woosii.biz.base.BasePresenter;
import com.woosii.biz.base.BaseView;
import com.woosii.biz.base.bean.json.MoneyListBean;

import java.util.Map;

/**
 * Created by Administrator on 2017/11/1.
 */

public interface MoneyContract {
    interface View extends BaseView {


        void getMoneyRecordSuccess(MoneyListBean model);



        void loadFail(String msg);
        void showLoading();
        void hideLoading();

    }

    abstract class Presenter extends BasePresenter<MoneyContract.View> {
        public abstract void getMoneyRecord(Map<String, String> map);

        @Override
        public void onStart() {

        }
    }
}
