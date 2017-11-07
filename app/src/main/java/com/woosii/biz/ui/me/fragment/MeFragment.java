package com.woosii.biz.ui.me.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.woosii.biz.AppConstant;
import com.woosii.biz.R;
import com.woosii.biz.base.BaseFragment;
import com.woosii.biz.base.bean.json.UserInfoBean;
import com.woosii.biz.base.rx.RxBus;
import com.woosii.biz.common.dialog.DialogInterface;
import com.woosii.biz.common.dialog.NormalAlertDialog;
import com.woosii.biz.event.ExitAccountEvent;
import com.woosii.biz.event.UserInfoEvent;
import com.woosii.biz.ui.course.activity.OpenMembershipActivity;
import com.woosii.biz.ui.login.activity.LoginActivity;
import com.woosii.biz.ui.me.activity.CourseCodeActivity;
import com.woosii.biz.ui.me.activity.EditProfileActivity;
import com.woosii.biz.ui.me.activity.MyAppointmentActivity;
import com.woosii.biz.ui.me.activity.MyMessageActivity;
import com.woosii.biz.ui.me.activity.MyQuestionAnswerActivity;
import com.woosii.biz.ui.me.activity.MyWalletActivity;
import com.woosii.biz.ui.me.activity.SettingActivity;
import com.woosii.biz.ui.me.activity.SuggestionFeedbackActivity;
import com.woosii.biz.ui.me.contract.MeContract;
import com.woosii.biz.ui.me.presenter.MePresenter;
import com.woosii.biz.utils.GlideCircleTransform;
import com.woosii.biz.utils.SharedPreferencesUtil;
import com.woosii.biz.utils.ToastUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by Administrator on 2017/9/25.
 */

public class MeFragment extends BaseFragment<MePresenter> implements View.OnClickListener, MeContract.View {
    @Bind(R.id.ll_my_login)
    LinearLayout llMyLogin;
    @Bind(R.id.ll_code)
    LinearLayout llCode;
    @Bind(R.id.img_head)
    ImageView imgHead;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.img_menber)
    ImageView imgMenber;
    @Bind(R.id.ll_setting)
    LinearLayout llSetting;
    @Bind(R.id.tv_class_name)
    TextView tvClassName;
    @Bind(R.id.ll_open_menber)
    LinearLayout llOpenMenber;
    @Bind(R.id.ll_my_question_answer)
    LinearLayout llMyQuestionAnswer;
    @Bind(R.id.ll_seggestion_feedback)
    LinearLayout llSeggestionFeedback;
    @Bind(R.id.ll_my_appointment)
    LinearLayout llMyAppointment;
    @Bind(R.id.ll_my_message)
    LinearLayout llMyMessage;
    @Bind(R.id.ll_my_wallat)
    LinearLayout llMyWallat;
    @Bind(R.id.ll_narrow_wallat)
    LinearLayout llNarrowWallat;
    @Bind(R.id.tv_integration)
    TextView tvIntegration;
    @Bind(R.id.img_location)
    ImageView imgLocation;

    private Subscription subscription;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_me;
    }

    @Override
    protected void initView() {
        llMyLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(LoginActivity.class);
                if ((SharedPreferencesUtil.getValue(getActivity(), SharedPreferencesUtil.USER_ID, "") + "").equals("")) {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), LoginActivity.class);
                    getActivity().startActivity(intent);
//                activity.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                    activity.overridePendingTransition(R.anim.my_push_up_in, R.anim.my_push_down_in);
                    llMyLogin.postDelayed(new Runnable() {
                        @Override
                        public void run() {
//                        activity.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                            activity.overridePendingTransition(R.anim.my_push_up_in, R.anim.my_push_down_in);
                        }
                    }, 1000);
                } else {
                    startActivity(EditProfileActivity.class);
                }

//                startActivity(LoginActivity.class);
            }
        });
        event();

        loadInfo();

    }

    private void loadInfo() {
//        Log.e("TTT","啊哈哈");
        if (!(SharedPreferencesUtil.getValue(getActivity(), SharedPreferencesUtil.USER_ID, "") + "").equals("")) {
//            Log.e("TTT","jinlai  "+SharedPreferencesUtil.getValue(getActivity(),SharedPreferencesUtil.USER_ID,""));
            Map<String, String> map = new HashMap<>();
            map.put("user_id", SharedPreferencesUtil.getValue(getActivity(), SharedPreferencesUtil.USER_ID, "") + "");
            map.put("token", SharedPreferencesUtil.getValue(getActivity(), SharedPreferencesUtil.TOKEN, "") + "");
            mPresenter.getUserInfo(map);
        }
    }


    private void event() {
        subscription = RxBus.$().toObservable(Object.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object event) {
                        if (event instanceof UserInfoEvent) {
                            UserInfoEvent userInfoEvent = (UserInfoEvent) event;
                            Map<String, String> map = new HashMap<>();
                            map.put("user_id", userInfoEvent.getUserInfoBean().getUser_id());
                            map.put("token", userInfoEvent.getUserInfoBean().getToken());
                            mPresenter.getUserInfo(map);
                        } else if (event instanceof ExitAccountEvent) {
                            imgHead.setImageResource(R.drawable.def_touxiang);
                            tvName.setText("点击登录");
                            tvClassName.setText("登录后可以获得更多功能，更佳体验");
                            tvIntegration.setText("0积分");
                            imgMenber.setVisibility(View.GONE);
                            llNarrowWallat.setVisibility(View.GONE);
                            llMyWallat.setVisibility(View.GONE);
                            llCode.setVisibility(View.GONE);
                            imgLocation.setVisibility(View.GONE);
                            tvIntegration.setVisibility(View.GONE);
                        }

                    }
                });
    }

    @OnClick({R.id.ll_code, R.id.ll_setting, R.id.ll_open_menber, R.id.ll_my_question_answer, R.id.ll_seggestion_feedback, R.id.ll_my_message, R.id.ll_my_appointment, R.id.ll_my_wallat})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_code:
                startActivity(CourseCodeActivity.class);

                break;
            case R.id.ll_setting:
                startActivity(SettingActivity.class);
                break;
            case R.id.ll_open_menber:
                if ((SharedPreferencesUtil.getValue(getActivity(), SharedPreferencesUtil.USER_ID, "") + "").equals("")) {
                    startActivity(LoginActivity.class);
                    return;
                }
                if ((SharedPreferencesUtil.getValue(getActivity(), SharedPreferencesUtil.VIP, "") + "").equals("1")) {
                    ToastUtil.showShortToast("您已经是VIP会员");
                    return;
                }
                startActivity(OpenMembershipActivity.class);
                break;
            case R.id.ll_my_question_answer:
                if ((SharedPreferencesUtil.getValue(getActivity(), SharedPreferencesUtil.USER_ID, "") + "").equals("")) {
                    startActivity(LoginActivity.class);
                    return;
                }
                startActivity(MyQuestionAnswerActivity.class);
                break;
            case R.id.ll_seggestion_feedback:
                startActivity(SuggestionFeedbackActivity.class);
                break;
            case R.id.ll_my_message:
                if ((SharedPreferencesUtil.getValue(getActivity(), SharedPreferencesUtil.USER_ID, "") + "").equals("")) {
                    startActivity(LoginActivity.class);
                    return;
                }
                startActivity(MyMessageActivity.class);
                break;
            case R.id.ll_my_appointment:
                if ((SharedPreferencesUtil.getValue(getActivity(), SharedPreferencesUtil.USER_ID, "") + "").equals("")) {
                    startActivity(LoginActivity.class);
                    return;
                }
                startActivity(MyAppointmentActivity.class);
                break;
            case R.id.ll_my_wallat:
                startActivity(MyWalletActivity.class);
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void getUserInfoSuccess(UserInfoBean model) {
        if(model.getCode()==0){
            ToastUtil.showShortToast(model.getMessage());
            return;
        }else if(model.getCode()==2){
            Log.e("TTT",model.getCode()+"");
            SharedPreferencesUtil.removeAll(getActivity());
            NormalAlertDialog dialog = new NormalAlertDialog.Builder(getActivity())
                    .setBoolTitle(false)
                    .setContentText("您的账号已过期，请重新登录")
//                            .setSingleModel(false)
                    .setRightText("登录")
                    .setLeftText("取消")
//                            .setRightTextColor(CourseDetailActivity.this.getResources().getColor(R.color.blue))
                    .setHeight(0.23f)
                    .setWidth(0.65f)
                    .setOnclickListener(new DialogInterface.OnLeftAndRightClickListener<NormalAlertDialog>(){
                        @Override
                        public void clickLeftButton(NormalAlertDialog dialog, View view) {
                            dialog.dismiss();
                        }
                        @Override
                        public void clickRightButton(NormalAlertDialog dialog, View view) {
                         startActivity(LoginActivity.class);
                            dialog.dismiss();
                        }
                    })
                    .setTouchOutside(false)
                    .build();
            dialog.show();

            return;
        }
        String imgpath = model.getThumb();
        if (!imgpath.equals("")) {
            if (!imgpath.contains("http")) {
                imgpath = AppConstant.BASE_URL + model.getThumb();
            }
        }
        Glide.with(getActivity())
                .load(imgpath)
                .placeholder(R.drawable.def_touxiang)
                .error(R.drawable.def_touxiang)
                .transform(new GlideCircleTransform(getActivity()))
                .into(imgHead);
//        GlideUtil.getInstance().LoadContextCircleBitmap(getActivity(), imgpath, imgHead, R.drawable.def_touxiang, R.drawable.def_touxiang);
        SharedPreferencesUtil.putValue(getActivity(), SharedPreferencesUtil.NICK_NAME, model.getNick_name());
        SharedPreferencesUtil.putValue(getActivity(), SharedPreferencesUtil.HEAD_PATH, model.getThumb());
        SharedPreferencesUtil.putValue(getActivity(), SharedPreferencesUtil.VIP, model.getVip());
        SharedPreferencesUtil.putValue(getActivity(), SharedPreferencesUtil.USER_TYPE, model.getUser_type());
        SharedPreferencesUtil.putValue(getActivity(), SharedPreferencesUtil.GENDER, model.getGender() + "");
        SharedPreferencesUtil.putValue(getActivity(), SharedPreferencesUtil.CLASS_NAME, model.getC_name() + "");
        if (model.getVip().equals("1")) {
            imgMenber.setVisibility(View.VISIBLE);
            imgLocation.setVisibility(View.VISIBLE);
        }
        if (model.getUser_type().equals("1")) {
            llNarrowWallat.setVisibility(View.VISIBLE);
            llMyWallat.setVisibility(View.VISIBLE);
        } else if (model.getUser_type().equals("2")) {
            llCode.setVisibility(View.VISIBLE);
        }
        tvIntegration.setVisibility(View.VISIBLE);
        tvIntegration.setText(model.getIntegral()+"积分");
        tvClassName.setText(model.getC_name());
        tvName.setText(model.getNick_name());
    }

    @Override
    public void loadFail(String msg) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        super.onDestroy();
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

}
