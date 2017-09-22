package com.woosii.biz.ui.login.contract;

import com.woosii.biz.base.BasePresenter;
import com.woosii.biz.base.BaseView;
import com.woosii.biz.base.bean.json.RegisterInfoBean;

import java.util.Map;

/**
 * Created by Administrator on 2017/9/22.
 */

public interface LoginContract   {
    interface View extends BaseView {

        void getRegisterCodeSuccess(RegisterInfoBean info);

        void registerSuccess(RegisterInfoBean model);
        void loadFail(String msg);
        void showLoading();
        void hideLoading();

    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void getRegisterCode(Map<String, String> map);
        public abstract void register(Map<String, String> map);


        @Override
        public void onStart() {

        }
    }
}
