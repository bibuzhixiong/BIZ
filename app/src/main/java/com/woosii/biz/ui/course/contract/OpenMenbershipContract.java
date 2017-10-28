package com.woosii.biz.ui.course.contract;

import com.woosii.biz.base.BasePresenter;
import com.woosii.biz.base.BaseView;
import com.woosii.biz.base.bean.json.PayInfoBean;
import com.woosii.biz.base.bean.json.PayReqBean;

import java.util.Map;

/**
 * Created by Administrator on 2017/10/24.
 */

public interface OpenMenbershipContract {
    interface View extends BaseView {


        void getPayOrderInfoSuccess(PayInfoBean model);
        void getWeChatOrderInfoSuccess(PayReqBean model);


        void loadFail(String msg);
        void showLoading();
        void hideLoading();

    }

    abstract class Presenter extends BasePresenter<OpenMenbershipContract.View> {
        public abstract void getPayOrderInfo(Map<String, String> map);
        public abstract void getWeChatOrderInfo(Map<String, String> map);



        @Override
        public void onStart() {

        }
    }
}
