package com.woosii.biz.common.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.woosii.biz.R;
import com.woosii.biz.utils.DensityUtil;


/**
 * Created by lan on 2016/12/19.
 * 单选、双选弹出框
 */
public class NormalAlertDialog {
    private TextView mTitle;
    private TextView mSubtitleTitle;
    private TextView mContent;
    private Button mLeftBtn;
    private Button mRightBtn;
    private Button mSingleBtn;
    private TextView mLineTv;
    private Dialog mDialog;
    private View mDialogView;
    private Builder mBuilder;

    private NormalAlertDialog(Builder builder){
        this.mBuilder=builder;
        mDialog = new Dialog(mBuilder.getContext(), R.style.NormalDialogStyle);
        mDialogView=View.inflate(mBuilder.getContext(), R.layout.widget_dialog_normal,null);
        mDialog.setContentView(mDialogView);

        Window dialogWindow=mDialog.getWindow();
        WindowManager.LayoutParams lp=dialogWindow.getAttributes();
        lp.width=(int) (DensityUtil.getScreenWidth(builder.getContext()) *
                builder.getWidth());
        lp.height=WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity= Gravity.CENTER;
        dialogWindow.setAttributes(lp);

        mTitle = (TextView) mDialogView.findViewById(R.id.dialog_normal_title);
        mSubtitleTitle=(TextView) mDialogView.findViewById(R.id.dialog_normal_subtitletitle);
        mContent = (TextView) mDialogView.findViewById(R.id.dialog_normal_content);
        mLeftBtn = (Button) mDialogView.findViewById(R.id.dialog_normal_leftbtn);
        mRightBtn = (Button) mDialogView.findViewById(R.id.dialog_normal_rightbtn);
        mSingleBtn = (Button) mDialogView.findViewById(R.id.dialog_normal_midbtn);
        mLineTv = (TextView) mDialogView.findViewById(R.id.dialog_normal_line);
        mDialogView.setMinimumHeight((int) (DensityUtil.getScreenWidth(builder.getContext()) * builder.getHeight()));
        initDialog(builder);
    }

    private void initDialog(Builder builder) {
        mDialog.setCanceledOnTouchOutside(mBuilder.isTouchOutside());
        mDialog.setCancelable(mBuilder.isCancelable());
        if(mBuilder.isBoolTitle()){
            mTitle.setVisibility(View.VISIBLE);
        }else{
            mTitle.setVisibility(View.GONE);
        }
        if(mBuilder.isBoolSubtitleTitle()){
            Log.d("TTT","TTTT");
            mSubtitleTitle.setVisibility(View.VISIBLE);
        }else{
            Log.d("TTT","TTTTfsdf");
            mSubtitleTitle.setVisibility(View.GONE);
        }
        if(mBuilder.isSingleModel){
            mLeftBtn.setVisibility(View.GONE);
            mLineTv.setVisibility(View.GONE);
            mRightBtn.setVisibility(View.GONE);
            mSingleBtn.setVisibility(View.VISIBLE);
        }
        mTitle.setText(builder.getTitleText());
        mTitle.setTextColor(builder.getTitleTextColor());
        mTitle.setTextSize(builder.getTitleTextSize());
        Log.d("TTT","TTTT11"+builder.getSubtitleTitleText());
        mSubtitleTitle.setText(builder.getSubtitleTitleText());
        mSubtitleTitle.setTextColor(builder.getSubtitleTitleTextColor());
        mSubtitleTitle.setTextSize(builder.getSubtitleTitleTextSize());

        mContent.setText(builder.getContentText());
        mContent.setTextColor(builder.getContentTextColor());
        mContent.setTextSize(builder.getContentTextSize());
        mLeftBtn.setText(builder.getLeftText());
        mLeftBtn.setTextColor(builder.getLeftTextColor());
        mLeftBtn.setTextSize(builder.getButtonTextSize());
        mRightBtn.setText(builder.getRightText());
        mRightBtn.setTextColor(builder.getRightTextColor());
        mRightBtn.setTextSize(builder.getButtonTextSize());
        mSingleBtn.setText(builder.getSingleText());
        mSingleBtn.setTextColor(builder.getSingleTextColor());
        mSingleBtn.setTextSize(builder.getButtonTextSize());

        mLeftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBuilder.getOnclickListener().clickLeftButton(NormalAlertDialog.this,mLeftBtn);
            }
        });
        mRightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBuilder.getOnclickListener().clickRightButton(NormalAlertDialog.this,mRightBtn);
            }
        });
        mSingleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBuilder.getSingleListener().clickSingleButton(NormalAlertDialog.this,mSingleBtn);
            }
        });

    }
    public void show() {

        mDialog.show();
    }

    public void dismiss() {

        mDialog.dismiss();
    }

    public Dialog getDialog(){

        return mDialog;
    }

    public static class Builder{
        private Context mContext;
        //设置标题属性
        private boolean  boolTitle;
        private boolean  boolSubtitleTitle;
        private String titleText;
        private int titleTextColor;
        private float titleTextSize;

        private String subtitleTitleText;
        private int subtitleTitleTextColor;
        private float subtitleTitleTextSize;
        //设置内容属性\
        private CharSequence contentText;
        private int contentTextColor;
        private float contentTextSize;
        //设置按钮，单按钮模式或者双按钮
        private boolean isSingleModel;
        private String singleText;
        private int singleTextColor;
        //左边按钮
        private String leftText;
        private int leftTextColor;
        //右边按钮
        private String rightText;
        private int rightTextColor;

        private DialogInterface.OnSingleClickListener singleListener;
        private DialogInterface.OnLeftAndRightClickListener onclickListener;

        private boolean isTouchOutside;
        private boolean isCancelable;
        private float height;
        private float width;
        private int buttonTextSize;



        public Builder(Context context){
            mContext=context;
            boolTitle=false;
            boolSubtitleTitle=false;
            titleText="温馨提示";
            subtitleTitleText="温馨提示";
            titleTextColor= ContextCompat.getColor(mContext, R.color.common_h1);
            subtitleTitleTextColor=ContextCompat.getColor(mContext, R.color.common_h1);

            contentTextColor= ContextCompat.getColor(mContext, R.color.common_h1);
            singleText="确认";
            isSingleModel=false;
            singleTextColor= ContextCompat.getColor(mContext, R.color.blue);
            leftText="取消";
            leftTextColor= ContextCompat.getColor(mContext, R.color.common_h3);
            rightText="确认";
            rightTextColor= ContextCompat.getColor(mContext, R.color.blue);
            onclickListener = null;
            singleListener = null;
            isTouchOutside = true;
            isCancelable=true;
            height = 0.23f;
            width = 0.65f;
            titleTextSize = 16;
            subtitleTitleTextSize=16;
            contentTextSize = 16;
            buttonTextSize = 14;
        }
        public boolean isBoolTitle() {
            return boolTitle;
        }

        public Builder setBoolTitle(boolean boolTitle) {
            this.boolTitle = boolTitle;
            return this;
        }

        public boolean isBoolSubtitleTitle() {
            return boolSubtitleTitle;
        }

        public Builder setBoolSubtitleTitle(boolean boolTitle) {
            this.boolSubtitleTitle = boolTitle;
            return this;
        }

        public Context getContext() {
            return mContext;
        }

        public String getTitleText() {
            return titleText;
        }

        public Builder setTitleText(String titleText) {
            this.titleText = titleText;
            return this;
        }

        public int getTitleTextColor() {
            return titleTextColor;
        }

        public Builder setTitleTextColor(int titleTextColor) {
            this.titleTextColor = ContextCompat.getColor(mContext, titleTextColor);
            return this;
        }

        public float getTitleTextSize() {
            return titleTextSize;
        }

        public Builder setTitleTextSize(float titleTextSize) {
            this.titleTextSize = titleTextSize;
            return this;
        }

        public Builder setSubtitleTitleText(String titleText) {
            this.subtitleTitleText = titleText;
            return this;
        }
        public String getSubtitleTitleText() {
            return subtitleTitleText;
        }

        public Builder setSubtitleTitleTextColor(int titleTextColor) {
            this.subtitleTitleTextColor = ContextCompat.getColor(mContext, titleTextColor);
            return this;
        }
        public int getSubtitleTitleTextColor() {
            return subtitleTitleTextColor;
        }
        public Builder setSubtitleTitleTextSize(float titleTextSize) {
            this.subtitleTitleTextSize = titleTextSize;
            return this;
        }
        public float getSubtitleTitleTextSize() {
            return subtitleTitleTextSize;
        }

        public CharSequence getContentText() {
            return contentText;
        }

        public Builder setContentText(CharSequence contentText) {
            this.contentText = contentText;
            return this;
        }
        public int getContentTextColor() {
            return contentTextColor;
        }
        public Builder setContentTextColor(int contentTextColor) {
            this.contentTextColor = contentTextColor;
            return this;
        }

        public float getContentTextSize() {
            return contentTextSize;
        }

        public Builder setContentTextSize(float contentTextSize) {
            this.contentTextSize = contentTextSize;
            return this;
        }

        public boolean isSingleModel() {
            return isSingleModel;
        }

        public Builder setSingleModel(boolean singleModel) {
            isSingleModel = singleModel;
            return this;
        }

        public String getSingleText() {
            return singleText;
        }

        public Builder setSingleText(String singleText) {
            this.singleText = singleText;
            return this;
        }

        public int getSingleTextColor() {
            return singleTextColor;
        }

        public Builder setSingleTextColor(int singleTextColor) {
            this.singleTextColor = ContextCompat.getColor(mContext, singleTextColor);
            return this;
        }
        public String getLeftText() {
            return leftText;
        }
        public Builder setLeftText(String leftText) {
            this.leftText = leftText;
            return this;
        }

        public int getLeftTextColor() {
            return leftTextColor;
        }

        public Builder setLeftTextColor(int leftTextColor) {
            this.leftTextColor = ContextCompat.getColor(mContext, leftTextColor);
            return this;
        }

        public String getRightText() {
            return rightText;
        }

        public Builder setRightText(String rightText) {
            this.rightText = rightText;
            return this;
        }

        public int getRightTextColor() {
            return rightTextColor;
        }

        public Builder setRightTextColor(int rightTextColor) {
            this.rightTextColor = ContextCompat.getColor(mContext, rightTextColor);
            return this;
        }

        public DialogInterface.OnSingleClickListener getSingleListener() {
            return singleListener;
        }

        public Builder setSingleListener(DialogInterface.OnSingleClickListener singleListener) {
            this.singleListener = singleListener;
            return this;
        }

        public DialogInterface.OnLeftAndRightClickListener getOnclickListener() {
            return onclickListener;
        }

        public Builder setOnclickListener(DialogInterface.OnLeftAndRightClickListener onclickListener) {
            this.onclickListener = onclickListener;
            return this;
        }

        public boolean isTouchOutside() {
            return isTouchOutside;
        }

        public Builder setTouchOutside(boolean touchOutside) {
            isTouchOutside = touchOutside;
            return this;
        }
        public boolean isCancelable() {
            return isCancelable;
        }

        public Builder setCancelable(boolean touchOutside) {
            isCancelable = touchOutside;
            return this;
        }

        public float getHeight() {
            return height;
        }

        public Builder setHeight(float height) {
            this.height = height;
            return this;
        }

        public float getWidth() {
            return width;
        }

        public Builder setWidth(float width) {
            this.width = width;
            return this;
        }

        public int getButtonTextSize() {
            return buttonTextSize;
        }

        public Builder setButtonTextSize(int buttonTextSize) {
            this.buttonTextSize = buttonTextSize;
            return this;
        }

        public NormalAlertDialog build() {

            return new NormalAlertDialog(this);
        }

    }

}
