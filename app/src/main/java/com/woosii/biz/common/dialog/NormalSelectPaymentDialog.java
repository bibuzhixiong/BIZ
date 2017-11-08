package com.woosii.biz.common.dialog;


import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.woosii.biz.R;

/**
 * Created by Administrator on 2017/11/7.
 */

public class NormalSelectPaymentDialog extends PopupWindow{
    private View mContentView;
    private Activity mActivity;


    private LinearLayout ll_weixin,ll_alipay;
    private TextView tv_money;

    private NormalSelectPaymentDialog.OnContinueClickListener onContinueClickListener;
    private NormalSelectPaymentDialog.OnBackIndexClickListener onBackIndexClickListerner;
    public NormalSelectPaymentDialog(Activity activity,String content){
        mActivity=activity;

        this.ll_weixin = ll_weixin;
        this.ll_alipay=ll_alipay;
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        mContentView = LayoutInflater.from(activity).inflate(R.layout.dialog_select_payment, null);
        setContentView(mContentView);
        setFocusable(true);

        setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
        setOutsideTouchable(false);
        setTouchable(true);
        ll_weixin= (LinearLayout) mContentView.findViewById(R.id.ll_weixin);
        ll_alipay= (LinearLayout) mContentView.findViewById(R.id.ll_alipay);
        tv_money=(TextView) mContentView.findViewById(R.id.tv_money);
        tv_money.setText("￥"+content);
        setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                lighton();
            }
        });

        ll_alipay.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(onContinueClickListener!=null){
                    onContinueClickListener.onContinueClickListener();
                    dismiss();
                }else{
                    dismiss();
                }


            }
        });

      /*  mIvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });*/
    }

    private void lighton() {
        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
        lp.alpha = 1.0f;
        mActivity.getWindow().setAttributes(lp);
    }

    private void lightoff() {
        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
        lp.alpha = 0.3f;
        mActivity.getWindow().setAttributes(lp);
    }
    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff) {
        lightoff();
        super.showAsDropDown(anchor, xoff, yoff);
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        lightoff();
        super.showAtLocation(parent, gravity, x, y);
    }

    public interface OnContinueClickListener{
        public void onContinueClickListener();
    }

    public void setOnContinueClickListener(NormalSelectPaymentDialog.OnContinueClickListener onContinueClickListener) {
        this.onContinueClickListener = onContinueClickListener;
    }

    public interface  OnBackIndexClickListener{
        public void onBackIndexClickListener();
    }
    public void setOnBackIndexClickListener(NormalSelectPaymentDialog.OnBackIndexClickListener onBackIndexClickListener) {
        this.onBackIndexClickListerner = onBackIndexClickListener;
    }

}
