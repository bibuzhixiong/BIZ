package com.woosii.biz.ui.me.activity;

import android.graphics.Bitmap;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.woosii.biz.R;
import com.woosii.biz.base.BaseActivity;
import com.woosii.biz.base.BaseToolbar;
import com.woosii.biz.utils.AESCrypt;
import com.woosii.biz.utils.DensityUtil;
import com.woosii.biz.utils.GlideUtil;
import com.woosii.biz.utils.SharedPreferencesUtil;
import com.xys.libzxing.zxing.encoding.EncodingUtils;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/10/9.
 */

public class CourseCodeActivity extends BaseActivity {
    @Bind(R.id.toolbar)
    BaseToolbar toolbar;
    @Bind(R.id.img_code)
    ImageView imgCode;
    @Bind(R.id.cardview)
    CardView cardview;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_class_name)
    TextView tvClassName;
    @Bind(R.id.img_head)
    ImageView imgHead;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_course_code;
    }

    @Override
    protected void initToolBar() {
        toolbar.setLeftButtonOnClickLinster(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish_Activity(CourseCodeActivity.this);
            }
        });
    }

    @Override
    protected void initView() {
        //获取屏幕宽度
        int ss = DensityUtil.getScreenWidth(CourseCodeActivity.this);
        //动态设置banner的高度
        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) cardview.getLayoutParams();
        linearParams.height = (int) (ss);
        cardview.setLayoutParams(linearParams);
        try {
            AESCrypt aesCrypt = new AESCrypt();
            String input = aesCrypt.encrypt("u_id=" + SharedPreferencesUtil.getValue(CourseCodeActivity.this, SharedPreferencesUtil.USER_ID, ""));
            Bitmap bitmap = EncodingUtils.createQRCode(input, ss - DensityUtil.dip2px(60), ss - DensityUtil.dip2px(60), null);
            imgCode.setImageBitmap(bitmap);

        } catch (Exception e) {
            e.printStackTrace();
        }
        tvClassName.setText(SharedPreferencesUtil.getValue(CourseCodeActivity.this, SharedPreferencesUtil.CLASS_NAME, "")+"");
        tvName.setText(SharedPreferencesUtil.getValue(CourseCodeActivity.this, SharedPreferencesUtil.NICK_NAME, "") + "");
        GlideUtil.getInstance().LoadContextCircleBitmap(CourseCodeActivity.this,SharedPreferencesUtil.getValue(CourseCodeActivity.this,SharedPreferencesUtil.HEAD_PATH,"")+"",imgHead,R.drawable.icon_head_normal,R.drawable.icon_head_normal);
    }



}
