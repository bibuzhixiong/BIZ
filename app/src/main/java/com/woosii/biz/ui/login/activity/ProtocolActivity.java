package com.woosii.biz.ui.login.activity;

import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;

import com.woosii.biz.R;
import com.woosii.biz.base.BaseActivity;
import com.woosii.biz.base.BaseToolbar;
import com.woosii.biz.common.dialog.LoadingDialog;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/11/4.
 */

public class ProtocolActivity  extends BaseActivity{
    @Bind(R.id.web_container)
    FrameLayout web_container;

    @Bind(R.id.toolbar)
    BaseToolbar toolbar;
    WebView webView;
    private LoadingDialog loadingDialog;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_protocol;
    }

    @Override
    protected void initToolBar() {
        toolbar.setLeftButtonOnClickLinster(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish_Activity(ProtocolActivity.this);
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
//        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
//        int fontSize = (int) getResources().getDimension(R.dimen.dp_50);
////        Log.i(TAG, "initView: fontSize = " + fontSize);
//        webSettings.setDefaultFontSize(fontSize);
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

        webView.loadUrl("http://biz.woosii.com/index.php/Home/Agreement/index");
        loadingDialog = new LoadingDialog(ProtocolActivity.this, "加载中...", true);
        loadingDialog.show();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
//            destroyWebView();

//        web_container.removeView(webView);
        webView.destroy();
        webView=null;
    }
}
