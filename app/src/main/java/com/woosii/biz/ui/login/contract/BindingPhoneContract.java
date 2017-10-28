package com.woosii.biz.ui.login.contract;

import com.woosii.biz.base.BasePresenter;
import com.woosii.biz.base.BaseView;
import com.woosii.biz.base.bean.LoginBean;
import com.woosii.biz.base.bean.json.BaseInfoBean;
import com.woosii.biz.base.bean.json.WechatInfoBean;

import java.util.Map;

/**
 * Created by Administrator on 2017/10/13.
 */

public interface BindingPhoneContract {
    interface View extends BaseView {


        void getCodeSuccess(BaseInfoBean model);

        void wechatBinding(LoginBean model);

        void getWeChatInfoSuccess(WechatInfoBean model);

        void loadFail(String msg);
        void showLoading();
        void hideLoading();

    }

    abstract class Presenter extends BasePresenter<BindingPhoneContract.View> {
        public abstract void getCode(Map<String, String> map);
        public abstract void wechatBinding(Map<String, String> map);
        public abstract void getWeChatInfo(String url);

        @Override
        public void onStart() {

        }
    }
}
