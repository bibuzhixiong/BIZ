package com.woosii.biz.manager;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.woosii.biz.common.dialog.NormalAlertDialog;
import com.woosii.biz.utils.ToastUtil;


/**
 * Created by zs on 2016/7/7.
 */
public class UpdateManager {

    private Context mContext;

    public UpdateManager(Context context) {
        this.mContext = context;
    }

    /**
     * 检测软件更新
     */
    public void checkUpdate(final boolean isToast) {
        showNoticeDialog("有新版本更新");
        /**
         * 在这里请求后台接口，获取更新的内容和最新的版本号
         */
        // 版本的更新信息
//        String version_info = "有版本更新";
//        int mVersion_code = DeviceUtils.getVersionCode(mContext);// 当前的版本号
//        int nVersion_code = 2;
//        if (mVersion_code < nVersion_code) {
//            // 显示提示对话
//            showNoticeDialog(version_info);
//        } else {
//            if (isToast) {
//                Toast.makeText(mContext, "已经是最新版本", Toast.LENGTH_SHORT).show();
//            }
//        }
    }

    /**
     * 显示更新对话框
     *
     * @param version_info
     */
    private void showNoticeDialog(String version_info) {
        NormalAlertDialog dialog = new NormalAlertDialog.Builder(mContext)
                .setBoolTitle(false)
                .setContentText("有新的版本更新")
//                            .setSingleModel(false)
                .setRightText("立即更新")
                .setLeftText("取消")
//                            .setRightTextColor(CourseDetailActivity.this.getResources().getColor(R.color.blue))
                .setHeight(0.23f)
                .setWidth(0.65f)
                .setOnclickListener(new com.woosii.biz.common.dialog.DialogInterface.OnLeftAndRightClickListener<NormalAlertDialog>(){
                    @Override
                    public void clickLeftButton(NormalAlertDialog dialog, View view) {
                        dialog.dismiss();
                    }
                    @Override
                    public void clickRightButton(NormalAlertDialog dialog, View view) {

                        mContext.startService(new Intent(mContext, DownLoadService.class));
                        ToastUtil.showShortToast("在通知栏为您显示更新进度");
                        dialog.dismiss();
                    }
                })
                .setTouchOutside(false)
                .build();
              dialog.show();

        // 构造对话框
     /*   AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("更新提示");
        builder.setMessage(version_info);
        // 更新
        builder.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                mContext.startService(new Intent(mContext, DownLoadService.class));
                Log.e("zs", "安装4");
            }
        });
        // 稍后更新
        builder.setNegativeButton("以后更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        Dialog noticeDialog = builder.create();
        noticeDialog.show();*/
    }
}
