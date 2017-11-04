package com.woosii.biz.ui.me.contract;

import com.woosii.biz.base.BasePresenter;
import com.woosii.biz.base.BaseView;
import com.woosii.biz.base.bean.json.BaseInfoBean;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2017/11/1.
 */

public interface QuestionAnswerDetailNorContract {
    interface View extends BaseView {


//        void getPayOrderInfoSuccess(PayInfoBean model);
        void changeHeadSuccess(BaseInfoBean model);
//        void askByUserSuccess(AskBean model);

        void loadFail(String msg);
        void showLoading();
        void hideLoading();

    }

    abstract class Presenter extends BasePresenter<QuestionAnswerDetailNorContract.View> {
//        public abstract void getPayOrderInfo(Map<String, String> map);
//        public abstract void getWeChatOrderInfo(Map<String, String> map);
//        public abstract void askByUser(Map<String, String> map);
        public abstract void changeHead(RequestBody uid, RequestBody pid, MultipartBody.Part body);




        @Override
        public void onStart() {

        }
    }
}
