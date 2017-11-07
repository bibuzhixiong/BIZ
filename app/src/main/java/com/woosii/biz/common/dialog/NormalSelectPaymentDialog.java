package com.woosii.biz.common.dialog;

import android.app.Dialog;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.woosii.biz.R;
import com.woosii.biz.utils.DensityUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/11/7.
 */

public class NormalSelectPaymentDialog {
    private Dialog mDialog;
    private View dialogView;
    private TextView title;
    private Button bt_sure;
    private LinearLayout linearLayout;
    private NormalSelectionDialog.Builder mBuilder;
    private List<String> datas;
    private int clickPosition;//最后一次选择的位置

    public NormalSelectPaymentDialog(NormalSelectionDialog.Builder builder) {
        this.mBuilder=builder;

        mDialog=new Dialog(mBuilder.getContext(), R.style.DialogStyle);
        dialogView= View.inflate(builder.getContext(), R.layout.dialog_select_payment,null);
        mDialog.setContentView(dialogView);

        Window window=mDialog.getWindow();
        WindowManager.LayoutParams lp=window.getAttributes();
        lp.width = (int) (DensityUtil.getScreenWidth(builder.getContext()) *
                builder.getItemWidth());
        lp.height=WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity= Gravity.BOTTOM;
        window.setAttributes(lp);

//        title= (TextView) dialogView.findViewById(R.id.action_dialog_title);
//        linearLayout = (LinearLayout) dialogView.findViewById(R.id.action_dialog_linearlayout);
        bt_sure = (Button) dialogView.findViewById(R.id.bt_sure);
        bt_sure.setText(mBuilder.getCancleButtonText());
        bt_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        mDialog.setCanceledOnTouchOutside(mBuilder.isTouchOutside());
    }
}
