package com.woosii.biz.ui.course.contract;

import com.woosii.biz.base.BasePresenter;
import com.woosii.biz.base.BaseView;
import com.woosii.biz.base.bean.json.AskBean;
import com.woosii.biz.base.bean.json.PayInfoBean;
import com.woosii.biz.base.bean.json.PayReqBean;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2017/10/13.
 */

public interface AskContract {
    interface View extends BaseView {


        void getPayOrderInfoSuccess(PayInfoBean model);
        void getWeChatOrderInfoSuccess(PayReqBean model);
        void askByUserSuccess(AskBean model);

        void loadFail(String msg);
        void showLoading();
        void hideLoading();

    }

    abstract class Presenter extends BasePresenter<AskContract.View> {
        public abstract void getPayOrderInfo(Map<String, String> map);
        public abstract void getWeChatOrderInfo(Map<String, String> map);
        public abstract void askByUser(Map<String, String> map);
        public abstract void changeHeadSuccess(RequestBody uid,RequestBody pid, MultipartBody.Part body);


        @Override
        public void onStart() {

        }
    }
}
