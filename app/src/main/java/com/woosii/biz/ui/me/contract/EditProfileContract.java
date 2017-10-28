package com.woosii.biz.ui.me.contract;

import com.woosii.biz.base.BasePresenter;
import com.woosii.biz.base.BaseView;
import com.woosii.biz.base.bean.json.BaseInfoBean;
import com.woosii.biz.base.bean.json.ThumbHeadBean;

import java.util.Map;

import okhttp3.MultipartBody;

/**
 * Created by Administrator on 2017/10/23.
 */

public interface EditProfileContract {
    interface View extends BaseView {


        void  upUserHeadSuccess(ThumbHeadBean model);
        void  updateInfoSuccess(BaseInfoBean model);


        void loadFail(String msg);
        void showLoading();
        void hideLoading();

    }

    abstract class Presenter extends BasePresenter<EditProfileContract.View> {
        public abstract void upUserHead(MultipartBody.Part file);
        public abstract void updateInfo(Map<String, String> map);

        @Override
        public void onStart() {

        }
    }
}
