package com.example.frame_lib.frame.actys;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;


import com.example.frame_lib.R;
import com.example.frame_lib.frame.callbacks.UiCallBack;
import com.example.mytitlelyoutlib.TitleLayout;


/**
 * Created by ke on 2018/4/17.
 */

abstract public class BaseActivity extends AppCompatActivity implements UiCallBack {
    private TitleLayout title_Layout;
    private FrameLayout content_layout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root);
        //设置沉浸式标题栏
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            //透明导航栏
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
            //透明标题栏
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        content_layout = findViewById(R.id.content_layout);
        title_Layout = (TitleLayout) findViewById(R.id.title_layout);
        title_Layout.setBackgroundColor(getResources().getDrawable(R.drawable.title_drable))
                .setBackSrc(R.drawable.fenlei)
                .setBackDismiss(true)
                .setBackgroundAlpha(90)
                .setRightDismiss(true)
                .setRightSrc(R.drawable.icon_search_write)
                .setTitleColor("#ffffff");
    }

    protected void setContent_layout(int layout_id) {
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(layout_id, null);
        content_layout.addView(view);
    }


    protected void setTilteDismiss() {
        title_Layout.setVisibility(View.GONE);
    }

    public TitleLayout getTitle_Layout() {
        return title_Layout;
    }

    abstract protected void initData();

    abstract protected void initView();

}
