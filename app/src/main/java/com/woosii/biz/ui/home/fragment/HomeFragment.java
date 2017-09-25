package com.woosii.biz.ui.home.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.woosii.biz.R;
import com.woosii.biz.adapter.NewsAdapter;
import com.woosii.biz.base.BaseFragment;
import com.woosii.biz.base.bean.json.BaseInfoBean;
import com.woosii.biz.utils.DensityUtil;
import com.woosii.biz.utils.ToastUtil;
import com.xys.libzxing.zxing.activity.CaptureActivity;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Administrator on 2017/9/23.
 */

public class HomeFragment extends BaseFragment implements OnBannerListener,View.OnClickListener {
/*    @Bind(R.id.banner)
    Banner banner;*/
    @Bind(R.id.ll_search)
    LinearLayout llSearch;
    @Bind(R.id.img_scan)
    ImageView imgScan;
    @Bind(R.id.recycleview)
    RecyclerView recycleview;


    //头部布局
    private Banner banner;

    //设置图片资源:url或本地资源
    List<String> images = new ArrayList<>();
    List<String> titles = new ArrayList<>();
    List<BaseInfoBean> list=new ArrayList<>();
    private NewsAdapter newsAdapter;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {

        //设置布局
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recycleview.setLayoutManager(linearLayoutManager);
        BaseInfoBean baseInfoBean=new BaseInfoBean(1,"身份解顺风顺水地方是范德萨发说冷风机");
        for(int i=0;i<50;i++){
            list.add(baseInfoBean);
        }


        newsAdapter = new NewsAdapter(list);
        newsAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        recycleview.setAdapter(newsAdapter);

        addHeadView();


    }

    private void addHeadView() {
        View headView = getActivity().getLayoutInflater().inflate(R.layout.header_home, (ViewGroup) recycleview.getParent(), false);
        banner =(Banner)headView.findViewById(R.id.banner);

        initBanner();

        newsAdapter.addHeaderView(headView);
    }
        private void initBanner(){
            //获取屏幕宽度
            int ss = DensityUtil.getScreenWidth(getActivity());
            //动态设置banner的高度
            RelativeLayout.LayoutParams linearParams = (RelativeLayout.LayoutParams) banner.getLayoutParams();
            linearParams.height = ss / 2;
            banner.setLayoutParams(linearParams);
            //设置内置样式，共有六种可以点入方法内逐一体验使用。
            banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
            images.add("http://img.zcool.cn/community/0166c756e1427432f875520f7cc838.jpg");
            images.add("http://img.zcool.cn/community/01fd2756e142716ac72531cbf8bbbf.jpg");
            //设置图片集合
            banner.setImages(images);
            //设置轮播的动画效果，内含多种特效，可点入方法内查找后内逐一体验
            banner.setBannerAnimation(Transformer.Default);
            //设置图片网址或地址的集合
            titles.add("时间了还好");
            titles.add("上飞机");
            banner.setBannerTitles(titles);
            //设置自动轮播，默认为true
            banner.isAutoPlay(true);
            //设置轮播间隔时间
            banner.setDelayTime(3000);
            //设置是否为自动轮播，默认是“是”。
            banner.isAutoPlay(true);

            //设置图片加载器
            banner.setImageLoader(new ImageLoader() {
                @Override
                public void displayImage(Context context, Object path, ImageView imageView) {
                    Glide.with(context).load((String) path).into(imageView);       //传入路径,因为list为String格式,path为Object格式,所以强制类型转换.

                }
            });

            //设置指示器位置（当banner模式中有指示器时）
            //设置指示器的位置，小点点，左中右。
            banner.setIndicatorGravity(BannerConfig.CENTER)
                    //以上内容都可写成链式布局，这是轮播图的监听。比较重要。方法在下面。
                    .setOnBannerListener(this)
                    //必须最后调用的方法，启动轮播图。
                    .start();
            //banner设置方法全部调用完毕时最后调用
        }

    //轮播点击
    @Override
    public void OnBannerClick(int position) {
        ToastUtil.showShortToast("你点击了" + position);
    }
    @OnClick({R.id.ll_search,R.id.img_scan})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.ll_search:
                break;
            case R.id.img_scan:
                scan();
                break;
        }

    }

    //扫描二维码
    //https://cli.im/text?2dd0d2b267ea882d797f03abf5b97d88二维码生成网站
    public void scan() {
        // 扫描功能
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            //在activity中申请CAMERA权限
//            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 3);
            //在fragment申请CAMERA权限
            requestPermissions(new String[]{Manifest.permission.CAMERA}, 3);
        } else {

            Intent intent = new Intent(getActivity(), CaptureActivity.class);
            startActivityForResult(intent, 0);
        }
    }

    //扫码二维码成功回调
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                String result = bundle.getString("result");
//                searchEt.setText(result);

                ToastUtil.showShortToast(result);

            }
        }
    }

    //权限回调
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (3 == requestCode) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Intent intent=  new Intent(getActivity(), CaptureActivity.class);
                startActivityForResult(intent,0);
            } else {
                // 未授权
            }
        }

    }

}


