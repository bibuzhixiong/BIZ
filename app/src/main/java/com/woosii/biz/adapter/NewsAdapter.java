package com.woosii.biz.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.woosii.biz.R;
import com.woosii.biz.base.bean.json.NewsBean;

import java.util.List;

/**
 * Created by Administrator on 2017/9/25.
 */

public class NewsAdapter extends BaseQuickAdapter<NewsBean,BaseViewHolder> {
    public NewsAdapter(@Nullable List<NewsBean> data) {
        super(R.layout.item_news,data);


    }
    @Override
    protected void convert(BaseViewHolder helper, NewsBean item) {
        String s=item.getNew_source().replaceAll("\n","");
        String s1=s.replaceAll(" ","");
        helper.setText(R.id.tv_content, item.getNew_theme());
        helper.setText(R.id.tv_sourse,s1);
        String img_path=item.getNew_img();

        if(img_path.equals("")){
           helper.getView(R.id.img_news).setVisibility(View.GONE);
        }else{
            helper.getView(R.id.img_news).setVisibility(View.VISIBLE);
            Glide.with(mContext).load(img_path).into(  (ImageView) helper.getView(R.id.img_news));
        }

        if(item.getNew_state().equals("0")){
            helper.getView(R.id.ll_news).setVisibility(View.VISIBLE);

        }else{
            helper.getView(R.id.ll_news).setVisibility(View.GONE);
        }


    }

    }