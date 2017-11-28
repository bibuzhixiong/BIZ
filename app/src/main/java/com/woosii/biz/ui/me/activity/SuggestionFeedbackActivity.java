package com.woosii.biz.ui.me.activity;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.woosii.biz.R;
import com.woosii.biz.base.BaseActivity;
import com.woosii.biz.base.BaseToolbar;
import com.woosii.biz.base.bean.json.BaseInfoBean;
import com.woosii.biz.common.dialog.DialogInterface;
import com.woosii.biz.common.dialog.LoadingDialog;
import com.woosii.biz.common.dialog.NormalAlertDialog;
import com.woosii.biz.ui.me.contract.SuggestionFeedbackContract;
import com.woosii.biz.ui.me.presenter.SuggestionFeedbackPresenter;
import com.woosii.biz.utils.CheckUtils;
import com.woosii.biz.utils.ToastUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/10/30.
 */

public class SuggestionFeedbackActivity extends BaseActivity<SuggestionFeedbackPresenter> implements SuggestionFeedbackContract.View {
    @Bind(R.id.toolbar)
    BaseToolbar toolbar;
    @Bind(R.id.et_content)
    EditText etContent;
    @Bind(R.id.et_phone)
    EditText etPhone;
    @Bind(R.id.bt_submit)
    Button btSubmit;
    @Bind(R.id.tv_connect_phone)
    TextView tvConnectPhone;
    private LoadingDialog mLoadingDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_suggestion_feedback;
    }

    @Override
    protected void initToolBar() {
        toolbar.setLeftButtonOnClickLinster(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish_Activity(SuggestionFeedbackActivity.this);
            }
        });

    }

    @Override
    protected void initView() {
        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etContent.getText().toString().trim().equals("")) {
                    ToastUtil.showShortToast("问题不能为空");
                    return;
                }
                if (!etPhone.getText().toString().trim().equals("") && !CheckUtils.isMobileNO(etPhone.getText().toString().trim())) {
                    ToastUtil.showShortToast("请输入正确的手机号码");
                    return;
                }
                Map<String, String> map = new HashMap<String, String>();
                map.put("content", etContent.getText().toString().trim());
                map.put("tel", etPhone.getText().toString().trim());
                mPresenter.suggestionFeedback(map);
            }
        });
        tvConnectPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NormalAlertDialog dialog = new NormalAlertDialog.Builder(SuggestionFeedbackActivity.this)
                        .setBoolTitle(false)
                        .setContentText("020-29078931")
//                            .setSingleModel(false)
                        .setRightText("拨打")
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
                                Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:" + "020-29078931"));
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                             dialog.dismiss();
                            }
                        })
                        .setTouchOutside(false)
                        .build();
                dialog.show();

            }
        });

    }


    @Override
    public void suggestionFeedbackSuccess(BaseInfoBean model) {
        if (model.getCode() == 1) {
            etContent.setText("");
            etPhone.setText("");
            ToastUtil.showShortToast("反馈成功");
        } else {
            ToastUtil.showShortToast("反馈失败");
        }
    }

    @Override
    public void loadFail(String msg) {
        ToastUtil.showShortToast(msg);
    }

    @Override
    public void showLoading() {
        mLoadingDialog = new LoadingDialog(SuggestionFeedbackActivity.this, "加载中...", false);
        mLoadingDialog.show();
    }

    @Override
    public void hideLoading() {
        if (mLoadingDialog != null) {
            mLoadingDialog.cancelDialog();
        }
    }


}
