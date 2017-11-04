package com.woosii.biz.ui.me.contract;

import com.woosii.biz.base.BasePresenter;
import com.woosii.biz.base.BaseView;
import com.woosii.biz.base.bean.json.BaseInfoBean;
import com.woosii.biz.base.bean.json.WechatBean;

import java.util.Map;

/**
 * Created by Administrator on 2017/11/3.
 */

public interface WithDrawContract {
    interface View extends BaseView {



        void loadWeChatData(WechatBean wechatBean);
        void withDrawSuccess(BaseInfoBean model);


        void loadFail(String msg);
        void showLoading();
        void hideLoading();

    }

    abstract class Presenter extends BasePresenter<WithDrawContract.View> {

        public abstract void loadWeChatData(String url);
        public abstract void withDraw(Map<String,String> map);

        @Override
        public void onStart() {

        }
    }
}
