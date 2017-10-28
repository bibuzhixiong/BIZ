package com.woosii.biz.ui.login.contract;

import com.woosii.biz.base.BasePresenter;
import com.woosii.biz.base.BaseView;
import com.woosii.biz.base.bean.LoginBean;
import com.woosii.biz.base.bean.json.BaseInfoBean;

import java.util.Map;

/**
 * Created by Administrator on 2017/10/21.
 */

public interface ForgetPasswordContract {
    interface View extends BaseView {


        void getCodeSuccess(BaseInfoBean model);

        void forgetPassworddSuccess(LoginBean model);



        void loadFail(String msg);
        void showLoading();
        void hideLoading();

    }

    abstract class Presenter extends BasePresenter<ForgetPasswordContract.View> {
        public abstract void getCode(Map<String, String> map);
        public abstract void forgetPassword(Map<String, String> map);

        @Override
        public void onStart() {

        }
    }
}
