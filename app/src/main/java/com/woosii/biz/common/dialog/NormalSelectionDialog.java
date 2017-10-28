package com.woosii.biz.common.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.woosii.biz.R;
import com.woosii.biz.utils.DensityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lan on 2016/12/19.
 * 底部弹出列表框
 */
public class NormalSelectionDialog {
    private Dialog mDialog;
    private View dialogView;
    private TextView title;
    private Button bottomBtn;
    private LinearLayout linearLayout;
    private Builder mBuilder;
    private List<String> datas;
    private int clickPosition;//最后一次选择的位置

    public NormalSelectionDialog(Builder builder) {
        this.mBuilder=builder;

        mDialog=new Dialog(mBuilder.getContext(), R.style.DialogStyle);
        dialogView=View.inflate(builder.getContext(), R.layout.dialog_bottom,null);
        mDialog.setContentView(dialogView);

        Window window=mDialog.getWindow();
        WindowManager.LayoutParams lp=window.getAttributes();
        lp.width = (int) (DensityUtil.getScreenWidth(builder.getContext()) *
                builder.getItemWidth());
        lp.height=WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity= Gravity.BOTTOM;
        window.setAttributes(lp);

        title= (TextView) dialogView.findViewById(R.id.action_dialog_title);
        linearLayout = (LinearLayout) dialogView.findViewById(R.id.action_dialog_linearlayout);
        bottomBtn = (Button) dialogView.findViewById(R.id.action_dialog_botbtn);
        bottomBtn.setText(mBuilder.getCancleButtonText());
        bottomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        mDialog.setCanceledOnTouchOutside(mBuilder.isTouchOutside());
    }
    //根据数据生成item
    private void loadItem(){
        //设置标题
        if(mBuilder.isBoolTitle()){
            title.setVisibility(View.VISIBLE);
            title.setText(mBuilder.getTitleText());
            title.setTextColor(mBuilder.getTitleTextColor());
            title.setTextSize(mBuilder.getTitleTextSize());
            LinearLayout.LayoutParams lp= (LinearLayout.LayoutParams) title.getLayoutParams();
            lp.height=mBuilder.getTitleHeight();

            title.setLayoutParams(lp);
            if(datas.size()!=0){
                title.setBackgroundResource(R.drawable.selector_widget_actiondialog_top);
            }else{
                title.setBackgroundResource(R.drawable.selector_widget_actiondialog_single);
            }
        }else{
            title.setVisibility(View.GONE);
        }
        //设置底部“取消”按钮
        bottomBtn.setTextSize(mBuilder.getItemTextSize());
        LinearLayout.LayoutParams btnLp=new LinearLayout.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,
                mBuilder.getItemHeight());
        btnLp.topMargin=10;
        bottomBtn.setLayoutParams(btnLp);
        if(datas.size()==1){
            Log.d("TTT","到佛爱豆");
            Button button=getButton(datas.get(0),0);
            if(mBuilder.isBoolTitle()){
                button.setBackgroundResource(R.drawable.selector_widget_actiondialog_bottom);
            }else{
                button.setBackgroundResource(R.drawable.selector_widget_actiondialog_single);
            }
            linearLayout.addView(button);
        }else if(datas.size()>1){
            for(int i=0;i<datas.size();i++){
                Button button=getButton(datas.get(i),i);
                if(!mBuilder.isBoolTitle()&&i==0){
                    button.setBackgroundResource(R.drawable.selector_widget_actiondialog_top);
                }else{
                   if(i!=datas.size()-1){
                       button.setBackgroundResource(R.drawable
                               .selector_widget_actiondialog_middle);
                   }else{
                       button.setBackgroundResource(R.drawable
                               .selector_widget_actiondialog_bottom);
                   }
                }
                linearLayout.addView(button);
            }
        }
    }
    //生成Button
    private Button getButton(String text,int position){
        final Button button=new Button(mBuilder.getContext());
        button.setText(text);
        button.setTag(position);
        button.setTextColor(mBuilder.getItemTextColor());
        button.setTextSize(mBuilder.getItemTextSize());
        button.setLayoutParams(new LinearLayout.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,
                mBuilder.getItemHeight()));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mBuilder.getOnItemListener()!=null){
                    clickPosition=Integer.parseInt(button.getTag().toString());
                    mBuilder.getOnItemListener().onItemClick(NormalSelectionDialog.this,button,clickPosition);
                }
            }
        });
        return button;
    }
    //初始化数据
    public void setData(List<String> datas){
       int count=linearLayout.getChildCount();
        if(count>1){
            linearLayout.removeViewsInLayout(1,count-1);
        }
        this.datas=(datas==null?new ArrayList<String>():datas);
        loadItem();
    }

    public int getFinalClickPosition(){

        return this.clickPosition;
    }

    public void show() {

        mDialog.show();

    }

    public void dismiss() {

        mDialog.dismiss();
    }

    public Dialog getDialog() {

        return mDialog;
    }
    public static class Builder{
        private Context mContext;
        //标题属性
        private boolean boolTitle;
        private String titleText;
        private int titleHeight;
        private int titleTextColor;
        private float titleTextSize;
        //item的属性
        private DialogInterface.OnItemClickListener onItemListener;
        private int itemHeight;
        private float itemWidth;
        private int itemTextColor;
        private float itemTextSize;
        //取消按钮属性
        private String cancleButtonText;
        private int cancleTextColor;
        private boolean isTouchOutside;



        public boolean isTouchOutside() {
            return isTouchOutside;
        }

        public Builder setTouchOutside(boolean touchOutside) {
            isTouchOutside = touchOutside;
            return this;
        }

        public  Builder(Context context) {
            this.mContext = context;
            //默认标题属性
            boolTitle = false;
            titleHeight = DensityUtil.dip2px(50); // 默认title高度
            titleText = "请选择";
            titleTextColor = ContextCompat.getColor(mContext, R.color.common_h2);
            titleTextSize = 13;
            //item的默认属性
            onItemListener = null;
            itemHeight = DensityUtil.dip2px(45); // 默认item高度
            itemWidth = 0.92f;
            itemTextColor = ContextCompat.getColor(mContext, R.color.common_h2); // 默认字体颜色
            itemTextSize = 14;  //默认自体大小

            cancleButtonText = "取消";
            cancleTextColor = ContextCompat.getColor(mContext, R.color.red1);
            isTouchOutside = true;
        }
        public Context getContext() {
            return mContext;
        }



        public boolean isBoolTitle() {
            return boolTitle;
        }

        public Builder setBoolTitle(boolean boolTitle) {
            this.boolTitle = boolTitle;
            return this;
        }

        public String getTitleText() {
            return titleText;
        }

        public Builder setTitleText(String titleText) {
            this.titleText = titleText;
            return this;
        }

        public int getTitleHeight() {
            return titleHeight;
        }

        public Builder setTitleHeight(int titleHeight) {
            this.titleHeight = DensityUtil.dip2px(titleHeight);
            return this;
        }

        public float getTitleTextSize() {
            return titleTextSize;
        }

        public Builder setTitleTextSize(float titleTextSize) {
            this.titleTextSize = titleTextSize;
            return this;
        }

        public int getTitleTextColor() {
            return titleTextColor;
        }

        public Builder setTitleTextColor(int titleTextColor) {
            this.titleTextColor = ContextCompat.getColor(mContext, titleTextColor);
            return this;
        }

        public DialogInterface.OnItemClickListener getOnItemListener() {
            return onItemListener;
        }

        public Builder setOnItemListener(DialogInterface.OnItemClickListener onItemListener) {
            this.onItemListener = onItemListener;
            return this;
        }

        public int getItemHeight() {
            return itemHeight;
        }

        public Builder setItemHeight(int itemHeight) {
            this.itemHeight = DensityUtil.dip2px(itemHeight);
            return this;
        }

        public float getItemWidth() {
            return itemWidth;
        }

        public Builder setItemWidth(float itemWidth) {
            this.itemWidth = itemWidth;
            return this;
        }

        public int getItemTextColor() {
            return itemTextColor;
        }

        public Builder setItemTextColor(int itemTextColor) {
            this.itemTextColor = ContextCompat.getColor(mContext, itemTextColor);
            return this;
        }

        public float getItemTextSize() {
            return itemTextSize;
        }

        public Builder setItemTextSize(float itemTextSize) {
            this.itemTextSize = itemTextSize;
            return this;
        }

        public String getCancleButtonText() {
            return cancleButtonText;
        }

        public Builder setCancleButtonText(String cancleButtonText) {
            this.cancleButtonText = cancleButtonText;
            return this;
        }
        public Builder setCancleTextColor(int Color) {
            this.cancleTextColor = Color;
            return this;
        }
        public NormalSelectionDialog build(){
            return new NormalSelectionDialog(this);
        }
        }

    }
