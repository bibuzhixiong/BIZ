package com.woosii.biz.ui.home.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
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
//    @Bind(R.id.webview)
    WebView webView;
    @Bind(R.id.toolbar)
    BaseToolbar toolbar;
    @Bind(R.id.myProgressBar)
    ProgressBar myProgressBar;
    @Bind(R.id.web_container)
    FrameLayout web_container;

    private LoadingDialog loadingDialog;
    private String id="";
    private String title="";
    private String imgurl="";

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
        toolbar.setRightButtonIcon(this.getResources().getDrawable(R.drawable.btn_fenxiang));
        toolbar.setRightButtonOnClickLinster(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ShareAction(NewsDetailActivity.this).setDisplayList(SHARE_MEDIA.WEIXIN,SHARE_MEDIA.WEIXIN_CIRCLE,SHARE_MEDIA.WEIXIN_FAVORITE)
                        .withTitle(title)
                        .withText("——来自沃噻APP")
                        .withMedia(imgurl.equals("")?new UMImage(NewsDetailActivity.this,R.drawable.ic_back):new UMImage(NewsDetailActivity.this,imgurl))
                        .withTargetUrl(AppConstant.WEB_URL+id)
                        .setCallback(umShareListener)
                        .open();

              /*
                UMWebPage web = new UMWebPage(AppConstant.WEB_URL+id);
                web.setTitle(title);//标题
//                web.setThumb(thumb);  //缩略图
//                web.setDescription("my description");//描述
                new ShareAction(NewsDetailActivity.this).withTitle(title)
//                        .withText(Defaultcontent.text+"——来自友盟分享面板")
//                        .withMedia(new UMImage(ShareMenuActivity.this, Defaultcontent.imageurl))
                        .withTargetUrl(AppConstant.WEB_URL+id)
                        .setDisplayList( SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                        .setCallback(umShareListener).open();*/
//                ToastUtil.showShortToast("分享个瓜皮"+AppConstant.WEB_URL+id);
            }
        });
    }
    UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA share_media) {
            Toast.makeText(NewsDetailActivity.this,"onResult", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            Toast.makeText(NewsDetailActivity.this,"onError", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            Toast.makeText(NewsDetailActivity.this,"onCancel", Toast.LENGTH_SHORT).show();
        }
    };

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
        // android 5.0以上默认不支持Mixed Content
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(
                    WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        }


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
         id=bundle.getString("id");
        title=bundle.getString("title");
        imgurl=bundle.getString("imgurl");
        webView.loadUrl(AppConstant.WEB_URL+id);
         loadingDialog=new LoadingDialog(NewsDetailActivity.this,"加载中...",true);
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

    public void destroyWebView() {

//        web_container.removeAllViews();

        if (webView != null) {
            webView.clearHistory();
            webView.clearCache(true);
            webView.loadUrl("about:blank"); // clearView() should be changed to loadUrl("about:blank"), since clearView() is deprecated now
            webView.freeMemory();
            webView.pauseTimers();
            webView = null; // Note that mWebView.destroy() and mWebView = null do the exact same thing
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);

    }

}
