package com.example.smartcooker.app.ui.actys;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.frame_lib.frame.actys.BaseActivity;
import com.example.frame_lib.frame.utils.PopDialogHelper;
import com.example.smartcooker.BuildConfig;
import com.example.smartcooker.R;
import com.example.smartcooker.app.dal.model.ChartArgsModel;
import com.example.smartcooker.app.dal.model.Image;
import com.example.smartcooker.app.ui.views.ChartHelper;
import com.example.smartcooker.app.ui.views.DefineTime;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import lecho.lib.hellocharts.view.LineChartView;

/**
 * Created by ke on 2018/5/15.
 */

public class SelfDefineActivity extends BaseActivity {
    private ImageView imageView_pic;
    private String fileName;//图片名称
    private Uri uri;//图片路径
    private TextView textView_add_pic, add_time, go;
    private PopDialogHelper popDialogHelper;
    String[] choose_way_list = {"图片来源", "拍照", "本地图库"};
    String[] save_way_list = {"请选择", "仅保存", "保存并分享", "放弃并退出"};
    private LinearLayout time_root;
    private List<TreeMap<Integer, Float>> pointList;
    private ChartHelper chartHelper;
    private List<ChartArgsModel> argsModelList;
    private LineChartView chartView;

    @Override
    public void refreshUi(Object result, int taskId) {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        //初始化图表
        chartView = findViewById(R.id.mchart);
        chartHelper = new ChartHelper(chartView);
        argsModelList = new ArrayList<>();
        //初始化弹出框
        popDialogHelper = new PopDialogHelper(this);
        //初始化标题栏
        getTitle_Layout().setTitleText("自定义食谱")
                .setBackSrc(R.drawable.icon_back_white)
                .setBackDismiss(false)
                .setRightSrc(R.drawable.fenlei)
                .setRightDismiss(false)
                .setRightOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popDialogHelper.buildMenu(0, 120);
                        popDialogHelper.setListData(Arrays.asList(save_way_list));
                        popDialogHelper.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                popDialogHelper.dismiss();
                                switch (i) {
                                    case 1:
                                        // TODO: 2018/5/16 保存到本地
                                        Toast.makeText(SelfDefineActivity.this, "已保存", Toast.LENGTH_SHORT).show();
                                        finish();
                                        break;
                                    case 2:
                                        break;
                                    case 3:
                                        finish();
                                        break;
                                }
                            }
                        });
                    }
                });
        //find view
        time_root = findViewById(R.id.time_root);
        go = findViewById(R.id.go);
        textView_add_pic = findViewById(R.id.add_pic);
        add_time = findViewById(R.id.add_time);
        imageView_pic = findViewById(R.id.image_pic);
        add_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View v = new DefineTime(getApplicationContext(), time_root);
                time_root.addView(v);
                ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
                p.topMargin = 10;
                v.setLayoutParams(p);
                go.bringToFront();
            }
        });
        textView_add_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popDialogHelper.buildDialogInCenter()
                        .setListData(Arrays.asList(choose_way_list));
                popDialogHelper.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        popDialogHelper.dismiss();
                        switch (i) {
                            case 1:
                                takePhoto();
                                break;
                            case 2:
                                choosePhoto();
                                break;
                        }
                    }
                });
            }
        });
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                go.bringToFront();
                if (go.getText().equals("生 成 加 热 曲 线")) { //生成曲线
                    //判断有无数据
                    if (time_root.getChildCount() != 0) {
                        //添加数据
                        argsModelList.clear();
                        for (int i = 0; i < time_root.getChildCount(); i++) {
                            DefineTime defineTime = (DefineTime) time_root.getChildAt(i);
                            argsModelList.add(defineTime.getArgs());
                        }
                        //生成图表
                        if (argsModelList.get(0) != null) {
                            chartHelper = null;
                            chartHelper = new ChartHelper(chartView);
                            time_root.setVisibility(View.GONE);
                            chartHelper.setMap(chartHelper.toMap(argsModelList));
                            chartView.setVisibility(View.VISIBLE);

                            go.setText("编   辑");
                        } else
                            Toast.makeText(getApplicationContext(), "请添加数据", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "请添加数据", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    //重新编辑
                    go.setText("生 成 加 热 曲 线");
                    time_root.setVisibility(View.VISIBLE);
                    chartView.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContent_layout(R.layout.activity_self_define);
        requestPermission();//请求存储权限
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        initView();
    }

    private void requestPermission() {
        //判断当前Activity是否已经获得了该权限
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            //如果App的权限申请曾经被用户拒绝过，就需要在这里跟用户做出解释
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(this, "please give me the permission", Toast.LENGTH_SHORT).show();
            } else {
                //进行权限请求
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        10);
            }
        }
    }

    public void takePhoto() {
        requestCameraPermission();
        //图片名称 时间命名
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date(System.currentTimeMillis());
        fileName = format.format(date);
        //存储至DCIM文件夹
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        File outputImage = new File(path, fileName + ".jpg");
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        uri = FileProvider.getUriForFile(this, "com.master.smartcooker.fileProvider", outputImage);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, 1);
    }

    /**
     * 动态请求相机使用权限
     */
    public void requestCameraPermission() {
        // android 6.0 获取摄像头的使用权限
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                //先判断有没有权限 ，没有就在这里进行权限的申请
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.CAMERA}, 88);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            imageView_pic.setVisibility(View.VISIBLE);
            switch (requestCode) {
                case 1:
                    Glide.with(this)
                            .load(uri)
                            .centerCrop()
                            .error(R.drawable.add_time_gray)
                            .into(imageView_pic);
                    break;
                case 0:
                    Uri curi = data.getData();
                    Glide.with(this)
                            .load(curi)
                            .centerCrop()
                            .error(R.drawable.add_time_gray)
                            .into(imageView_pic);
                    break;

            }
        }
    }

    public void choosePhoto() {
        Intent photoIntent = new Intent(Intent.ACTION_PICK);
        photoIntent.setType("image/*");//相片类型
        startActivityForResult(photoIntent, 0);
    }
}
