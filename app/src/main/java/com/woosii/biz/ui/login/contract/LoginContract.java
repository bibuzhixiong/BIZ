package com.woosii.biz.ui.login.contract;

import com.woosii.biz.base.BasePresenter;
import com.woosii.biz.base.BaseView;
import com.woosii.biz.base.bean.LoginBean;
import com.woosii.biz.base.bean.json.BaseInfoBean;

import java.util.Map;

/**
 * Created by Administrator on 2017/9/22.
 */

public interface LoginContract   {
    interface View extends BaseView {

        void getRegisterCodeSuccess(BaseInfoBean info);

        void registerSuccess(BaseInfoBean model);

        void loginByPassword(LoginBean model);

        void loadFail(String msg);
        void showLoading();
        void hideLoading();

    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void getRegisterCode(Map<String, String> map);
        public abstract void register(Map<String, String> map);
        public abstract void loginByPassword(Map<String, String> map);


        @Override
        public void onStart() {

        }
    }
}
