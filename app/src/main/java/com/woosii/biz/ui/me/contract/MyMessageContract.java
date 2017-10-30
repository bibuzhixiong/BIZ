package com.woosii.biz.ui.me.contract;

import com.woosii.biz.base.BasePresenter;
import com.woosii.biz.base.BaseView;
import com.woosii.biz.base.bean.json.MyMessageBean;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/10/30.
 */

public interface MyMessageContract {

    interface View extends BaseView {



        void  getMyMessageSuccess(List<MyMessageBean> model);


        void loadFail(String msg);
        void showLoading();
        void hideLoading();

    }

    abstract class Presenter extends BasePresenter<MyMessageContract.View> {

        public abstract void getMyMessage(Map<String, String> map);

        @Override
        public void onStart() {

        }
    }

}
