package com.woosii.biz.ui.course.activity;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.woosii.biz.R;
import com.woosii.biz.base.BaseActivity;
import com.woosii.biz.base.BaseToolbar;
import com.woosii.biz.base.bean.json.PreViewQuestionsItemBean;
import com.woosii.biz.base.bean.json.PreViewQuestionsSelectionBean;
import com.woosii.biz.base.bean.json.PreViewQuestonsListBean;
import com.woosii.biz.common.dialog.LoadingDialog;
import com.woosii.biz.ui.course.contract.PreviewQuestionsContract;
import com.woosii.biz.ui.course.presenter.PreviewQuestionsPrensenter;
import com.woosii.biz.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/10/25.
 */

public class PreviewQuestionsActivity extends BaseActivity<PreviewQuestionsPrensenter> implements PreviewQuestionsContract.View {
    @Bind(R.id.toolbar)
    BaseToolbar toolbar;
    @Bind(R.id.tv_question)
    TextView tvQuestion;
    @Bind(R.id.rb_a)
    RadioButton rbA;
    @Bind(R.id.rb_b)
    RadioButton rbB;
    @Bind(R.id.rb_c)
    RadioButton rbC;
    @Bind(R.id.rb_d)
    RadioButton rbD;
    @Bind(R.id.rb_e)
    RadioButton rbE;
    @Bind(R.id.radioGroup)
    RadioGroup radioGroup;
    @Bind(R.id.tv_correct_answer)
    TextView tvCorrectAnswer;
    @Bind(R.id.tv_parsing)
    TextView tvParsing;
    @Bind(R.id.scrollView)
    ScrollView scrollView;
    @Bind(R.id.tv_last)
    TextView tvLast;
    @Bind(R.id.tv_next)
    TextView tvNext;
    @Bind(R.id.img_pasring_g)
    ImageView imgPasringG;
    @Bind(R.id.img_pasring_v)
    ImageView imgPasringV;
    @Bind(R.id.ll_parsing)
    LinearLayout llParsing;
    private LoadingDialog mLoadingDialog;
    private List<PreViewQuestionsItemBean> list = new ArrayList<>();
    private int sumSelection = 0;
    private int current = 0;
    private List<Integer> listInt = new ArrayList<>();
    private String type;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_preview_questions;
    }

    @Override
    protected void initToolBar() {
        toolbar.setLeftButtonOnClickLinster(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish_Activity(PreviewQuestionsActivity.this);
            }
        });
    }

    @Override
    protected void initView() {
        Bundle bundle=getIntent().getExtras();
        String class_id=bundle.getString("class_id");
        type=bundle.getString("type");
        Map<String, String> map = new HashMap<>();
        map.put("class_id",class_id);
        map.put("type",type);
        mPresenter.getPreviewQuestions(map);

    }

    @Override
    public void getPreviewQuestions(PreViewQuestonsListBean model) {
        list = model.getArray();
        sumSelection = list.size();
        for (int i = 0; i < sumSelection; i++) {
            listInt.add(0);
        }
        setParsing();

    }

    private void setParsing(){
        //隐藏解析
        llParsing.setVisibility(View.GONE);

        PreViewQuestionsItemBean preViewQuestionsItemBean1 = list.get(current);
        PreViewQuestionsSelectionBean selection1 = preViewQuestionsItemBean1.getQuestionoptions();
        int temp=current+1;
        SpannableString spannableString = new SpannableString("(单选题)" +temp+"/"+sumSelection+"."+ preViewQuestionsItemBean1.getQuestion());
        spannableString.setSpan(new ForegroundColorSpan(PreviewQuestionsActivity.this.getResources().getColor(R.color.blue)), 0,5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvQuestion.setText(spannableString);
//        tvQuestion.setText("(单选题)" +current+1+"."+ preViewQuestionsItemBean1.getQuestion());
        tvParsing.setText(preViewQuestionsItemBean1.getParsing());
        tvCorrectAnswer.setText("正确答案："+preViewQuestionsItemBean1.getAnswer());
        setCheck();
        rbA.setText("A:" + selection1.getA());
        rbB.setText("B:" + selection1.getB());
        if (selection1.getC() != null) {
            rbC.setVisibility(View.VISIBLE);

            rbC.setText("C:" + selection1.getC());
            if (selection1.getD() != null) {
                rbD.setVisibility(View.VISIBLE);
                rbD.setText("D:" + selection1.getD());
                if(selection1.getE() != null){
                    rbE.setVisibility(View.VISIBLE);
                    rbE.setText("D:" + selection1.getD());
                }else{
                    rbE.setVisibility(View.GONE);
                }
            } else {
                rbD.setVisibility(View.GONE);
                rbE.setVisibility(View.GONE);
            }
        } else {
            rbC.setVisibility(View.GONE);
            rbD.setVisibility(View.GONE);
            rbE.setVisibility(View.GONE);
        }
    }

    private void setCheck() {
        Log.e("UUU", "当前位置" + listInt.get(current) + "");
        if (listInt.get(current) == 1) {
            rbA.setChecked(true);
        } else if (listInt.get(current) == 2) {
            rbB.setChecked(true);
        } else if (listInt.get(current) == 3) {
            rbC.setChecked(true);
        } else if (listInt.get(current) == 4) {
            rbD.setChecked(true);
        }else if (listInt.get(current) == 5) {
            rbE.setChecked(true);
        }
        else if (listInt.get(current) == 0) {
            radioGroup.clearCheck();

        }
    }

    @OnClick({R.id.tv_last, R.id.tv_next, R.id.rb_a, R.id.rb_b, R.id.rb_c, R.id.rb_d,R.id.rb_e,R.id.img_pasring_g,R.id.img_pasring_v})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_last:
                tvNext.setText("下一题");
                current--;
                if (current < 0) {
                    current++;
                    ToastUtil.showShortToast("已经是第一题");
                    return;
                }
                setParsing();


                break;
            case R.id.tv_next:
                int yourCorrect=0;
                if((current+2)==sumSelection){
                    tvNext.setText("完成");
                }
                current++;
                if (current >= sumSelection) {
                    current--;
                    for(int i=0;i<listInt.size();i++){
                        if(listInt.get(i)==0){
                            ToastUtil.showShortToast("第"+(i+1)+"题未完成");
                            return;
                        }
                   String yourAnswer=listInt.get(i)+"";
                        if(yourAnswer.equals("1")){
                            yourAnswer="a";

                        }  if(yourAnswer.equals("2")){
                            yourAnswer="b";

                        }
                        if(yourAnswer.equals("3")){
                            yourAnswer="c";

                        }
                        if(yourAnswer.equals("4")){
                            yourAnswer="d";

                        }
                        if(yourAnswer.equals("5")){
                            yourAnswer="e";

                        }

                     String  correctAnswer=   list.get(i).getAnswer();

                        if(yourAnswer.equals(correctAnswer)){
                            yourCorrect++;
                        }
                    }
                    Bundle bundle=new Bundle();
                    bundle.putString("correct",yourCorrect+"");
                    bundle.putString("error",list.size()-yourCorrect+"");
                   startActivity(PreviewQuestionsCompleteActivity.class,bundle);
                    finish_Activity(PreviewQuestionsActivity.this);
                    return;
                }
                setParsing();
              /*  PreViewQuestionsItemBean preViewQuestionsItemBean = list.get(current);
                PreViewQuestionsSelectionBean selection = preViewQuestionsItemBean.getQuestionoptions();
                tvQuestion.setText("(单选题)" + preViewQuestionsItemBean.getQuestion());
                tvParsing.setText(preViewQuestionsItemBean.getParsing());
                setCheck();
                rbA.setText("A;" + selection.getA());
                rbB.setText("B;" + selection.getB());
                if (selection.getC() != null) {
                    rbC.setVisibility(View.VISIBLE);

                    rbC.setText("C:" + selection.getC());
                    if (selection.getD() != null) {
                        rbD.setVisibility(View.VISIBLE);
                        rbD.setText("D:" + selection.getD());
                    } else {
                        rbD.setVisibility(View.GONE);
                    }
                } else {
                    rbC.setVisibility(View.GONE);
                    rbD.setVisibility(View.GONE);
                }
*/
                break;
            case R.id.rb_a:
                listInt.set(current, 1);
                break;
            case R.id.rb_b:
                listInt.set(current, 2);
                break;
            case R.id.rb_c:
                listInt.set(current, 3);
                break;
            case R.id.rb_d:
                listInt.set(current, 4);
                break;
            case R.id.rb_e:
                listInt.set(current, 5);
                break;
            case R.id.img_pasring_g:
                llParsing.setVisibility(View.GONE);
                break;
            case R.id.img_pasring_v:
                llParsing.setVisibility(View.VISIBLE);
                break;


        }
    }


    @Override
    public void loadFail(String msg) {

    }

    @Override
    public void showLoading() {
        mLoadingDialog = new LoadingDialog(PreviewQuestionsActivity.this, "加载中...", false);
        mLoadingDialog.show();
    }

    @Override
    public void hideLoading() {
        if (mLoadingDialog != null) {
            mLoadingDialog.cancelDialog();
        }
    }



}
