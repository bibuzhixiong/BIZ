package com.woosii.biz.ui.me.activity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.woosii.biz.AppConstant;
import com.woosii.biz.R;
import com.woosii.biz.base.BaseActivity;
import com.woosii.biz.base.BaseToolbar;
import com.woosii.biz.base.bean.LoginBean;
import com.woosii.biz.base.bean.json.BaseInfoBean;
import com.woosii.biz.base.bean.json.ThumbHeadBean;
import com.woosii.biz.base.rx.RxBus;
import com.woosii.biz.common.dialog.DialogInterface;
import com.woosii.biz.common.dialog.LoadingDialog;
import com.woosii.biz.common.dialog.NormalSelectionDialog;
import com.woosii.biz.event.UserInfoEvent;
import com.woosii.biz.ui.ClipHeaderActivity;
import com.woosii.biz.ui.me.contract.EditProfileContract;
import com.woosii.biz.ui.me.presenter.EditProfilePresenter;
import com.woosii.biz.utils.GlideUtil;
import com.woosii.biz.utils.ImageUtils;
import com.woosii.biz.utils.SharedPreferencesUtil;
import com.woosii.biz.utils.ToastUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2017/10/21.
 */

public class EditProfileActivity extends BaseActivity<EditProfilePresenter> implements EditProfileContract.View {
    @Bind(R.id.toolbar)
    BaseToolbar toolbar;
    @Bind(R.id.ll_img_head)
    LinearLayout llImgHead;
    @Bind(R.id.img_head)
    ImageView imgHead;

    //调用相机相册
    private static final int RESULT_CAPTURE = 100;
    private static final int RESULT_PICK = 101;
    private static final int CROP_PHOTO = 102;
    @Bind(R.id.et_nick_name)
    TextView etNickName;

    @Bind(R.id.tv_sex)
    TextView tvSex;
    @Bind(R.id.ll_sex)
    LinearLayout llSex;
    @Bind(R.id.ll_save)
    LinearLayout llSave;

    private File tempFile;

    private boolean isUpHead=false;
    private String path="";
    private LoadingDialog mLoadingDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_profile;
    }

    @Override
    protected void initToolBar() {
        toolbar.setLeftButtonOnClickLinster(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish_Activity(EditProfileActivity.this);
            }
        });
    }

    @Override
    protected void initView() {
        String headPath=SharedPreferencesUtil.getValue(EditProfileActivity.this,SharedPreferencesUtil.HEAD_PATH,"")+"";
        String nickName=SharedPreferencesUtil.getValue(EditProfileActivity.this,SharedPreferencesUtil.NICK_NAME,"")+"";
        String gender=SharedPreferencesUtil.getValue(EditProfileActivity.this,SharedPreferencesUtil.GENDER,"")+"";
        etNickName.setText(nickName);
        if(gender.equals("0")){
            tvSex.setText("女");
        }else{
            tvSex.setText("男");
        }

        if(!headPath.equals("")){
            if(!headPath.contains("http")){
                headPath= AppConstant.BASE_URL+headPath;
            }
            GlideUtil.getInstance().LoadContextCircleBitmap(EditProfileActivity.this,headPath,imgHead,R.drawable.icon_head_normal,R.drawable.icon_head_normal);
        }



    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("tempFile", tempFile);
    }

    private void initData(Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.containsKey("tempFile")) {
            tempFile = (File) savedInstanceState.getSerializable("tempFile");
        } else {
            tempFile = new File(checkDirPath(Environment.getExternalStorageDirectory().getPath() + "/woosii/image/"),
                    System.currentTimeMillis() + ".jpg");
        }
    }

    @OnClick({R.id.ll_img_head,R.id.ll_sex,R.id.ll_save})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_img_head:
                List<String> headData = new ArrayList<>();
                headData.add("相机");
                headData.add("相册");
                NormalSelectionDialog chaHeaddialog = new NormalSelectionDialog.Builder(EditProfileActivity.this)
                        .setItemHeight(45)
                        .setItemTextColor(R.color.blue)
                        .setItemTextSize(14)
                        .setItemWidth(0.7f)
                        .setCancleButtonText("取消")
                        .setOnItemListener(new DialogInterface.OnItemClickListener<NormalSelectionDialog>() {
                            @Override
                            public void onItemClick(NormalSelectionDialog dialog, View button, int position) {
                                switch (position) {
                                    case 0:
                                        performCodeWithPermission("软件更新需要您提供浏览存储的权限", new PermissionCallback() {
                                            @Override
                                            public void hasPermission() {
                                                startCamera();
                                            }

                                            @Override
                                            public void noPermission() {
                                            }
                                        }, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA);
                                        dialog.dismiss();
                                        break;
                                    case 1:
                                        performCodeWithPermission("软件更新需要您提供浏览存储的权限", new PermissionCallback() {
                                            @Override
                                            public void hasPermission() {
                                                startAlbum();
                                            }

                                            @Override
                                            public void noPermission() {
                                            }
                                        }, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                                        dialog.dismiss();
                                        break;
                                }

                            }
                        }).setTouchOutside(true)
                        .build();

                chaHeaddialog.setData(headData);
                chaHeaddialog.show();
                break;
            case R.id.ll_sex:
                List<String> headData1 = new ArrayList<>();
                headData1.add("男");
                headData1.add("女");
                NormalSelectionDialog chaHeaddialog1 = new NormalSelectionDialog.Builder(EditProfileActivity.this)
                        .setItemHeight(45)
                        .setItemTextColor(R.color.blue)
                        .setItemTextSize(14)
                        .setItemWidth(0.7f)
                        .setCancleButtonText("取消")
                        .setOnItemListener(new DialogInterface.OnItemClickListener<NormalSelectionDialog>() {
                            @Override
                            public void onItemClick(NormalSelectionDialog dialog, View button, int position) {
                                switch (position) {
                                    case 0:
                                        tvSex.setText("男");
                                        dialog.dismiss();
                                        break;
                                    case 1:
                                     tvSex.setText("女");
                                        dialog.dismiss();
                                        break;
                                }

                            }
                        }).setTouchOutside(true)
                        .build();

                chaHeaddialog1.setData(headData1);
                chaHeaddialog1.show();
                break;
            case R.id.ll_save:
                if(etNickName.getText().toString().trim().equals("")){
                    ToastUtil.showShortToast("请输入昵称");
                    return;
                }
                if(isUpHead==true){
                    File file = new File(path);
//                    Log.e("PPP", Environment.getExternalStorageDirectory().getAbsolutePath() + "/record/20171010112802.amr");
                    RequestBody requestFile =
                            RequestBody.create(MediaType.parse("multipart/form-data"), file);
                    MultipartBody.Part body =
                            MultipartBody.Part.createFormData("attach", file.getName(), requestFile);
//                    Map<String, RequestBody> map = new HashMap<>();
//                    RequestBody uidBody = RequestBody.create(MediaType.parse("text/plain"), String.valueOf("55555"));
////            RequestBody fileBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//            map.put("Filedata", fileBody);
                    mPresenter.upUserHead( body);
                }else{
                    showLoading();
                    Map<String,String> map=new HashMap<>();
                    map.put("user_id", SharedPreferencesUtil.getValue(EditProfileActivity.this,SharedPreferencesUtil.USER_ID,"")+"");
                    map.put("nick_name",etNickName.getText().toString().trim());
                    if(tvSex.getText().toString().trim().equals("男")){
                        map.put("gender","1");
                    }else {
                        map.put("gender","0");
                    }


                    mPresenter.updateInfo(map);
                }
                break;
        }
    }

    //打开照相机
    private void startCamera() {
        if (!tempFile.getParentFile().exists()) tempFile.getParentFile().mkdirs();
        Uri imageUri = FileProvider.getUriForFile(EditProfileActivity.this, "com.woosii.takephoto.fileprovider", tempFile);//通过FileProvider创建一个content类型的Uri
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);//设置Action为拍照
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);//将拍取的照片保存到指定URI
        if (intent.resolveActivity(EditProfileActivity.this.getPackageManager()) != null) {//判断是否有相机应用
            startActivityForResult(intent, RESULT_CAPTURE);
        } else {
            ToastUtil.showShortToast("没有找到相机");
        }
    }

    //打开相册
    private void startAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "请选择图片"), RESULT_PICK);
    }

    /**
     * 打开截图界面
     *
     * @param uri 原图的Uri
     */
    public void starCropPhoto(Uri uri) {

        if (uri == null) {
            return;
        }
        Intent intent = new Intent();
        intent.setClass(EditProfileActivity.this, ClipHeaderActivity.class);
        intent.setData(uri);
        intent.putExtra("side_length", 200);//裁剪图片宽高
        startActivityForResult(intent, CROP_PHOTO);
    }

    private void setPicToView(Intent picdata) {
        Uri uri = picdata.getData();

        if (uri == null) {
            return;
        }
         path = ImageUtils.getRealFilePathFromUri(EditProfileActivity.this, uri);
        GlideUtil.getInstance().LoadContextCircleBitmap(EditProfileActivity.this, path, imgHead, R.drawable.icon_head_normal, R.drawable.icon_head_normal);
        isUpHead=true;
//        uploadPic(path);
    }

    /**
     * 判断文件是否存在
     *
     * @param dirPath
     * @return
     */
    private static String checkDirPath(String dirPath) {
        if (TextUtils.isEmpty(dirPath)) {
            return "";
        }

        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dirPath;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (requestCode) {
            case RESULT_CAPTURE:
                if (resultCode == RESULT_OK) {
                    starCropPhoto(Uri.fromFile(tempFile));
                }
                break;
            case RESULT_PICK:
                if (resultCode == RESULT_OK) {
                    Uri uri = intent.getData();

                    starCropPhoto(uri);
                }

                break;
            case CROP_PHOTO:
                if (resultCode == RESULT_OK) {

                    if (intent != null) {
                        setPicToView(intent);
                    }
                }
                break;


            default:
                break;
        }
    }

    @Override
    public void upUserHeadSuccess(ThumbHeadBean model) {
        Map<String,String> map=new HashMap<>();
        map.put("user_id", SharedPreferencesUtil.getValue(EditProfileActivity.this,SharedPreferencesUtil.USER_ID,"")+"");
        map.put("nick_name",etNickName.getText().toString().trim());
        map.put("gender",tvSex.getText().toString().trim());
        map.put("thumb",AppConstant.BASE_URL+model.getThumb());
        mPresenter.updateInfo(map);
    }

    @Override
    public void updateInfoSuccess(BaseInfoBean model) {
        //个人中心获取信息
        RxBus.$().postEvent(new UserInfoEvent(new LoginBean(0,"", SharedPreferencesUtil.getValue(EditProfileActivity.this,SharedPreferencesUtil.TOKEN,"")+"",SharedPreferencesUtil.getValue(EditProfileActivity.this,SharedPreferencesUtil.USER_ID,"")+"")));
        ToastUtil.showShortToast(model.getMessage());
        finish_Activity(EditProfileActivity.this);

    }

    @Override
    public void loadFail(String msg) {
        ToastUtil.showShortToast(msg);
    }

    @Override
    public void showLoading() {
        mLoadingDialog = new LoadingDialog(EditProfileActivity.this, "加载中...", false);
        mLoadingDialog.show();
    }

    @Override
    public void hideLoading() {
        if (mLoadingDialog != null) {
            mLoadingDialog.cancelDialog();
        }
    }
}
