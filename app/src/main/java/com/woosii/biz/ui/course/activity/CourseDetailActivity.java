package com.woosii.biz.ui.course.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.woosii.biz.R;
import com.woosii.biz.base.BaseActivity;
import com.woosii.biz.base.BaseToolbar;
import com.woosii.biz.base.bean.json.BaseInfoBean;
import com.woosii.biz.common.dialog.DialogInterface;
import com.woosii.biz.common.dialog.LoadingDialog;
import com.woosii.biz.common.dialog.NormalAlertDialog;
import com.woosii.biz.ui.course.contract.CourseDetailContract;
import com.woosii.biz.ui.course.presenter.CourseDetailPresenter;
import com.woosii.biz.ui.login.activity.LoginActivity;
import com.woosii.biz.utils.SharedPreferencesUtil;
import com.woosii.biz.utils.ToastUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/10/9.
 */

public class CourseDetailActivity extends BaseActivity<CourseDetailPresenter> implements CourseDetailContract.View {
    @Bind(R.id.toolbar)
    BaseToolbar toolbar;
    @Bind(R.id.ll_ask)
    LinearLayout llAsk;
    @Bind(R.id.ll_enter_now)
    LinearLayout llEnterNow;
    @Bind(R.id.web_container)
    FrameLayout web_container;
    WebView webView;
    @Bind(R.id.img_preview_questions)
    ImageView imgPreviewQuestions;
    private LoadingDialog loadingDialog;
    private String id;
    private String teacher_id;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_course_deatil;
    }

    @Override
    protected void initToolBar() {
        toolbar.setLeftButtonOnClickLinster(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish_Activity(CourseDetailActivity.this);
            }
        });
    }

    @Override
    protected void initView() {
        webView = new WebView(getApplicationContext());
        web_container.addView(webView);
        WebSettings webSettings = webView.getSettings();

        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setUseWideViewPort(true);//设置webview推荐使用的窗口
        webSettings.setLoadWithOverviewMode(true);//设置webview加载的页面的模式
        webSettings.setDisplayZoomControls(false);//隐藏webview缩放按钮
        webSettings.setJavaScriptEnabled(true); // 设置支持javascript脚本
        webSettings.setAllowFileAccess(true); // 允许访问文件
        webSettings.setBuiltInZoomControls(false); // 设置显示缩放按钮
        webSettings.setSupportZoom(false); // 支持缩放
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress > 80) {
                    if (loadingDialog != null)
                        loadingDialog.cancelDialog();
//                    myProgressBar.setVisibility(View.GONE);
                } else {
                   /* if (View.INVISIBLE == myProgressBar.getVisibility()) {
                        myProgressBar.setVisibility(View.VISIBLE);
                    }
                    myProgressBar.setProgress(newProgress);*/
                }
                super.onProgressChanged(view, newProgress);
            }

        });
        Bundle bundle = getIntent().getExtras();
         id = bundle.getString("class_id");
        teacher_id= bundle.getString("teacher_id");
        String type=bundle.getString("type");
        if(type.equals("0")){
            imgPreviewQuestions.setVisibility(View.GONE);
        }else if(type.equals("1")){
            imgPreviewQuestions.setImageResource(R.drawable.btn_keqianfuxi);
        }else if(type.equals("2")){
            imgPreviewQuestions.setVisibility(View.VISIBLE);
        }
        webView.loadUrl("http://biz.woosii.com/index.php/Home/Course/index?id=" + id);
        loadingDialog = new LoadingDialog(CourseDetailActivity.this, "加载中...", true);
        loadingDialog.show();
    }

    @OnClick({R.id.ll_ask, R.id.ll_enter_now,R.id.img_preview_questions})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.ll_ask:
                Bundle bundle=new Bundle();
                bundle.putString("teacher_id",teacher_id);
                startActivity(AskActivity.class,bundle);
                break;
            case R.id.ll_enter_now:
                if((SharedPreferencesUtil.getValue(CourseDetailActivity.this,SharedPreferencesUtil.USER_ID,"")+"").equals("")){
                    startActivity(LoginActivity.class);
                    return;
                }

                if((SharedPreferencesUtil.getValue(CourseDetailActivity.this,SharedPreferencesUtil.VIP,"")+"").equals("1")){


                    NormalAlertDialog dialog = new NormalAlertDialog.Builder(this)
                            .setBoolTitle(false)
                            .setContentText("立即报名")
//                            .setSingleModel(false)
                            .setRightText("报名")
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
                                    Map<String,String> map=new HashMap<String, String>();
                                    map.put("user_id",SharedPreferencesUtil.getValue(CourseDetailActivity.this,SharedPreferencesUtil.USER_ID,"")+"");
                                    map.put("class_id",id);
                                    mPresenter.signUp(map);
                                    dialog.dismiss();
                                }
                            })
                            .setTouchOutside(false)
                            .build();
                    dialog.show();
                    return;
                }
                startActivity(OpenMembershipActivity.class);
                break;
            case R.id.img_preview_questions:

                startActivity(PreviewQuestionsActivity.class);
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//            destroyWebView();

//        web_container.removeView(webView);
        webView.destroy();
        webView = null;
    }




    @Override
    public void signUpSuccess(BaseInfoBean model) {
        ToastUtil.showShortToast(model.getMessage());
    }

    @Override
    public void loadFail(String msg) {
        ToastUtil.showShortToast(msg);
    }

    @Override
    public void showLoading() {
        loadingDialog = new LoadingDialog(CourseDetailActivity.this, "加载中...", false);
        loadingDialog.show();
    }

    @Override
    public void hideLoading() {
        if (loadingDialog != null) {
            loadingDialog.cancelDialog();
        }
    }
}
