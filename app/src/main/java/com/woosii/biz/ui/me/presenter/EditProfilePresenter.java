package com.woosii.biz.ui.me.presenter;

import com.woosii.biz.api.Api;
import com.woosii.biz.base.bean.json.BaseInfoBean;
import com.woosii.biz.base.bean.json.ThumbHeadBean;
import com.woosii.biz.base.rx.RxSubscriber;
import com.woosii.biz.ui.me.contract.EditProfileContract;

import java.util.Map;

import okhttp3.MultipartBody;

/**
 * Created by Administrator on 2017/10/23.
 */

public class EditProfilePresenter extends EditProfileContract.Presenter{
    @Override
    public void upUserHead(MultipartBody.Part file) {
        mView.showLoading();
        addSubscrebe(Api.getInstance().upUserHead(file),new RxSubscriber<ThumbHeadBean>(mContext,true){
            @Override
            protected void onSuccess(ThumbHeadBean info) {
                if(info.getCode()==1){
                    mView.upUserHeadSuccess(info);
                }else{
                    mView.hideLoading();
                    mView.loadFail(info.getMessage());
                }


            }
            @Override
            protected void onFailure(String message) {
                mView.loadFail(message);
            }
        });

    }

    @Override
    public void updateInfo(Map<String, String> map) {

        addSubscrebe(Api.getInstance().updateInfo(map),
                new RxSubscriber<BaseInfoBean>(mContext, false) {
                    @Override
                    protected void onSuccess(BaseInfoBean model) {
                        mView.hideLoading();
                       mView.updateInfoSuccess(model);


                    }
                    @Override
                    protected void onFailure(String message) {
                        mView.hideLoading();
                        mView.loadFail(message);
                    }
                });
    }
}
