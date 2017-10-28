package com.woosii.biz.ui.home.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.woosii.biz.base.bean.json.BasePagingBean;
import com.woosii.biz.base.bean.json.NewsBean;
import com.woosii.biz.base.bean.json.PointBean;
import com.woosii.biz.common.dialog.DialogInterface;
import com.woosii.biz.common.dialog.NormalAlertDialog;
import com.woosii.biz.ui.home.activity.NewsDetailActivity;
import com.woosii.biz.ui.home.activity.SignSuccessActivity;
import com.woosii.biz.ui.home.contract.HomeContract;
import com.woosii.biz.ui.home.presenter.HomePresenter;
import com.woosii.biz.utils.AESCrypt;
import com.woosii.biz.utils.DensityUtil;
import com.woosii.biz.utils.RescourseUtil;
import com.woosii.biz.utils.SharedPreferencesUtil;
import com.woosii.biz.utils.ToastUtil;
import com.woosii.biz.widget.CustomLoadMoreView;
import com.xys.libzxing.zxing.activity.CaptureActivity;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Administrator on 2017/9/23.
 */

public class HomeFragment extends BaseFragment<HomePresenter> implements OnBannerListener,View.OnClickListener,HomeContract.View,BaseQuickAdapter.RequestLoadMoreListener,SwipeRefreshLayout.OnRefreshListener{
/*    @Bind(R.id.banner)
    Banner banner;*/
    @Bind(R.id.ll_search)
    LinearLayout llSearch;
    @Bind(R.id.img_scan)
    ImageView imgScan;
    @Bind(R.id.recycleview)
    RecyclerView recycleview;
    @Bind(R.id.swiperefreshlayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    //头部布局
    private Banner banner;

    //设置图片资源:url或本地资源
    List<String> images = new ArrayList<>();
    List<String> titles = new ArrayList<>();
    List<NewsBean> listBanner=new ArrayList<>();

    List<NewsBean> list=new ArrayList<>();
    private NewsAdapter newsAdapter;

    private int page=1;  //页数
    private int totalPages=1;//总页数


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {



        mSwipeRefreshLayout.setColorSchemeColors(RescourseUtil.getColor(R.color.blue),
                RescourseUtil.getColor(R.color.blue1));
        //设置刷新出现的位置
        mSwipeRefreshLayout.setProgressViewEndTarget(false, 120);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        //设置布局
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recycleview.setLayoutManager(linearLayoutManager);
/*        BaseInfoBean baseInfoBean=new BaseInfoBean(1,"身份解顺风顺水地方是范德萨发说冷风机");
        for(int i=0;i<50;i++){
            list.add(baseInfoBean);
        }*/


        newsAdapter = new NewsAdapter(list);
        newsAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        recycleview.setAdapter(newsAdapter);

        newsAdapter.setOnLoadMoreListener(this, recycleview);
        newsAdapter.setLoadMoreView(new CustomLoadMoreView());

        newsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle=new Bundle();
                bundle.putString("id",newsAdapter.getData().get(position).getNew_id());
                bundle.putString("title",newsAdapter.getData().get(position).getNew_theme());
                bundle.putString("imgurl",newsAdapter.getData().get(position).getNew_img());
                startActivity(NewsDetailActivity.class,bundle);
            }
        });
        addHeadView();

        loaddata(false);
        //获取轮播
        Map<String,String> map=new HashMap<>();
        mPresenter.getNewsBanner(map);


    }
    //swifload判断是否是下拉加载
    private void loaddata(boolean isRefresh){
        Map<String,String> map=new HashMap<>();
        map.put("psize","10");
        if(isRefresh){
            map.put("pindex","1");
            mPresenter.refreshNews(map);
        }else{
            map.put("pindex",page+"");
            mPresenter.getNews(map);

        }


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
            linearParams.height = (int)(ss / 2);
            banner.setLayoutParams(linearParams);

        }

    //轮播点击
    @Override
    public void OnBannerClick(int position) {
        Bundle bundle=new Bundle();
        bundle.putString("id",listBanner.get(position).getNew_id());
        bundle.putString("title",listBanner.get(position).getNew_theme());
        bundle.putString("imgurl",listBanner.get(position).getNew_img());
        startActivity(NewsDetailActivity.class,bundle);
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

                try {
                    AESCrypt aesCrypt=new AESCrypt();
                    String s= aesCrypt.decrypt(result);
                   if(s!=null&&!s.equals("")){
                       if(s.length()>4){
                           if(s.substring(0,5).equals("u_id=")){
                               String value=s+"&user_id"+ SharedPreferencesUtil.getValue(getActivity(),SharedPreferencesUtil.USER_ID,"");
                               Map<String,String> map=new HashMap<>();
                               map.put("temp",aesCrypt.encrypt(value));
                               mPresenter.scan(map);

                           }else{
                               ToastUtil.showShortToast("该扫码只适用于沃噻签到");
                           }
                       }
                   }else{
                       ToastUtil.showShortToast("该扫码只适用于沃噻签到");
                   }

                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtil.showShortToast("该扫码只适用于沃噻签到");
                }
//                ToastUtil.showShortToast(result);

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

    @Override
    public void getNewsSuccess(BasePagingBean<NewsBean> model) {
        totalPages=Integer.parseInt(model.getCount());
        newsAdapter.addData(model.getChild());

        list=newsAdapter.getData();
//        dataLists=newsAdapter.getData();
        newsAdapter.loadMoreComplete();


    }

    @Override
    public void getNewsBannerSuccess(List<NewsBean> model) {
        listBanner=model;
        //设置内置样式，共有六种可以点入方法内逐一体验使用。
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        for(int i=0;i<model.size();i++){
            images.add(model.get(i).getNew_img());
            titles.add(model.get(i).getNew_theme());
        }

        //设置图片集合
        banner.setImages(images);
        //设置轮播的动画效果，内含多种特效，可点入方法内查找后内逐一体验
        banner.setBannerAnimation(Transformer.Default);
        //设置图片网址或地址的集合
        banner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播间隔时间
        banner.setDelayTime(5000);
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

    @Override
    public void refreshNewsSuccess(BasePagingBean<NewsBean> model) {
        totalPages=Integer.parseInt(model.getCount());
        mSwipeRefreshLayout.setRefreshing(false);
        list.clear();
        list.addAll(model.getChild());
        newsAdapter.setNewData(list);
        page=1;
//        Log.e("TTT",page+"号");
//        newsAdapter.notifyDataSetChanged();
    }

    @Override
    public void scanSuccess(BaseInfoBean model) {
        if(model.getCode()==1){
            Map<String ,String> map=new HashMap<>();
            map.put("user_id",SharedPreferencesUtil.getValue(getActivity(),SharedPreferencesUtil.USER_ID,"")+"");
            mPresenter.getPoint(map);
        }else{
            //要删掉，这是测试
            Map<String ,String> map=new HashMap<>();
            map.put("user_id",SharedPreferencesUtil.getValue(getActivity(),SharedPreferencesUtil.USER_ID,"")+"");
            mPresenter.getPoint(map);
//            ToastUtil.showShortToast("失败");
            NormalAlertDialog dialog = new NormalAlertDialog.Builder(getActivity())
                    .setBoolTitle(false)
                    .setContentText("您还没预约课程")
                    .setSingleModel(true)
                    .setSingleText("确认")
                    .setHeight(0.23f)
                    .setWidth(0.65f)
                    .setSingleListener(new DialogInterface.OnSingleClickListener<NormalAlertDialog>() {
                        @Override
                        public void clickSingleButton(NormalAlertDialog dialog, View view) {
                            dialog.dismiss();
                        }
                    }).setTouchOutside(false)
                    .build();
            dialog.show();
        }



    }

    @Override
    public void getPointSuccess(PointBean model) {
        if(model.getCode()==1){
            Bundle bundle=new Bundle();
            bundle.putString("NUM",model.getCount());
            startActivity(SignSuccessActivity.class,bundle);

        }else if(model.getCode()==2||model.getCode()==3){
            NormalAlertDialog dialog = new NormalAlertDialog.Builder(getActivity())
                    .setBoolTitle(false)
                    .setContentText(model.getMessage())
                    .setSingleModel(true)
                    .setSingleText("确认")
                    .setHeight(0.23f)
                    .setWidth(0.65f)
                    .setSingleListener(new DialogInterface.OnSingleClickListener<NormalAlertDialog>() {
                        @Override
                        public void clickSingleButton(NormalAlertDialog dialog, View view) {
                            dialog.dismiss();
                        }
                    }).setTouchOutside(false)
                    .build();
            dialog.show();
        }else{
            ToastUtil.showShortToast(model.getMessage());
        }
    }

    @Override
    public void loadFail(String msg) {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onLoadMoreRequested() {
        if (newsAdapter.getData().size() < 10) {
            newsAdapter.loadMoreEnd(false);
            return;
        }else {
            int maxpage=1;
            if(totalPages%10==0){
                maxpage=totalPages/10-1;
            }else{
                maxpage=totalPages/10;
            }
            if (page > maxpage) {
                newsAdapter.loadMoreEnd(false);
                return;
            }
            page++;
//            Log.e("TTT",page+"好吧");
            loaddata( false);
        }
    }

    @Override
    public void onRefresh() {
        loaddata(true);
    }
}


