package com.woosii.biz.ui.home.activity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.woosii.biz.AppConstant;
import com.woosii.biz.R;
import com.woosii.biz.base.BaseActivity;
import com.woosii.biz.base.BaseToolbar;
import com.woosii.biz.common.dialog.LoadingDialog;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/9/26.
 */

public class NewsDetailActivity extends BaseActivity {
    @Bind(R.id.webview)
    WebView webView;
    @Bind(R.id.toolbar)
    BaseToolbar toolbar;
    @Bind(R.id.myProgressBar)
    ProgressBar myProgressBar;
    private LoadingDialog loadingDialog;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_news_detail;
    }

    @Override
    protected void initToolBar() {
        toolbar.setLeftButtonOnClickLinster(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish_Activity(NewsDetailActivity.this);
            }
        });
    }

    @Override
    protected void initView() {
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

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        if (dm.densityDpi >= 240 ) {
            webSettings.setDefaultFontSize(40); //可以取1-72之间的任意值，默认16
        }else {
            webSettings.setDefaultFontSize(30);
        }
        //主要用于平板，针对特定屏幕代码调整分辨率
//        DisplayMetrics metrics = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(metrics);
//        webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
       /* int mDensity = metrics.densityDpi;
        if (mDensity == 240) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        } else if (mDensity == 160) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        } else if (mDensity == 120) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.CLOSE);
        } else if (mDensity == DisplayMetrics.DENSITY_XHIGH) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        } else if (mDensity == DisplayMetrics.DENSITY_TV) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        } else {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        }
*/
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress >80) {
                    if(loadingDialog!=null)
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

        Bundle bundle=getIntent().getExtras();
        String id=bundle.getString("id");
        webView.loadUrl(AppConstant.WEB_URL+id);
         loadingDialog=new LoadingDialog(NewsDetailActivity.this,"加载中...",true);
        loadingDialog.show();
    }
}
