package com.woosii.biz.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.woosii.biz.AppConstant;
import com.woosii.biz.R;
import com.woosii.biz.base.bean.json.CourseListBean;
import com.woosii.biz.utils.DensityUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/9/26.
 */

public class CourseListAdapter extends BaseQuickAdapter<CourseListBean,BaseViewHolder> {
    private int ss=0;
    public CourseListAdapter(@Nullable List<CourseListBean> data) {
        super(R.layout.item_course_list,data);

    }
    @Override
    protected void convert(BaseViewHolder helper, CourseListBean item) {
        helper.setText(R.id.tv_content,item.getClass_name());
        helper.setText(R.id.tv_time,item.getTime());
        ImageView imageView=helper.getView(R.id.img_course);
        ImageView imgMsg=helper.getView(R.id.img_msg);
        if(item.getType().equals("0")){
//            imgMsg.setImageResource();
            imgMsg.setImageDrawable(mContext.getResources().getDrawable(R.drawable.msg_yuyue_default));
        }else  if(item.getType().equals("2")){
            imgMsg.setImageDrawable(mContext.getResources().getDrawable(R.drawable.msg_yuyue_selected));
        }else  if(item.getType().equals("1")){
            imgMsg.setImageDrawable(mContext.getResources().getDrawable(R.drawable.msg_yuyue_disabled));
        }else  if(item.getType().equals("3")){
            imgMsg.setImageDrawable(mContext.getResources().getDrawable(R.drawable.msg_yuyue_yijieshu));
        }

        ss= DensityUtil.getScreenWidth(mContext);
        if(ss!=0){
            //动态设置imageView的高度
            RelativeLayout.LayoutParams linearParams =(RelativeLayout.LayoutParams) imageView.getLayoutParams();
            double a=(ss-DensityUtil.dip2px(20))/2.6;
//            Log.e("TTT","a:"+a+"---"+DensityUtil.dip2px(20)+"---"+(int)a);
            linearParams.height =(int)a ;// 控件的宽强制设成
            imageView.setLayoutParams(linearParams);
        }

        Glide.with(mContext).load(AppConstant.BASE_URL+item.getThumb()).into(  imageView);
        }


    }