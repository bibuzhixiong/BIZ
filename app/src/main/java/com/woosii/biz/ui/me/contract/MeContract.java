package com.woosii.biz.ui.me.contract;

import com.woosii.biz.base.BasePresenter;
import com.woosii.biz.base.BaseView;
import com.woosii.biz.base.bean.json.UserInfoBean;

import java.util.Map;

/**
 * Created by Administrator on 2017/10/14.
 */

public interface MeContract {
    interface View extends BaseView {


        void getUserInfoSuccess(UserInfoBean model);



        void loadFail(String msg);
        void showLoading();
        void hideLoading();

    }

    abstract class Presenter extends BasePresenter<MeContract.View> {
        public abstract void getUserInfo(Map<String, String> map);

        @Override
        public void onStart() {

        }
    }
}
