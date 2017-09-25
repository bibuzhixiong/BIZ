package com.woosii.biz.common.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.woosii.biz.R;


/**
 * description:弹窗浮动加载进度条
 * Created by xsf
 * on 2016.07.17:22
 */
public class LoadingDialog {
    /** 加载数据对话框 */
    private  Dialog mLoadingDialog;
    private String msg;
    private boolean isCancle;
    private Context mcontext;
    /**
     * 显示加载对话框
     * @param context 上下文
     */
    public LoadingDialog(Context context){
       this(context,"加载中....",true);
    }
    /**
     * 显示加载对话框
     * @param context 上下文
     * @param msg 对话框显示内容
     * @param cancelable 对话框是否可以取消
     */
    public LoadingDialog(Context context, String msg, boolean cancelable){
        this.mcontext=context;
        this.msg=msg;
        this.isCancle=cancelable;
        init();
    }
    private void init(){
        View view = LayoutInflater.from(mcontext).inflate(R.layout.dialog_loading, null);
        TextView loadingText = (TextView)view.findViewById(R.id.id_tv_loading_dialog_text);
        loadingText.setText(msg);

        mLoadingDialog = new Dialog(mcontext, R.style.CustomProgressDialog);
        mLoadingDialog.setCancelable(isCancle);
        mLoadingDialog.setCanceledOnTouchOutside(false);
        mLoadingDialog.setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
    }
    public void show(){
        if(mLoadingDialog != null) {
            mLoadingDialog.show();
        }
    }
    /**
     * 关闭加载对话框
     */
    public  void cancelDialog() {
        if(mLoadingDialog != null) {
            mLoadingDialog.cancel();
        }
    }
}
