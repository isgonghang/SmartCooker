package com.example.smartcooker.app.ui.actys;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.frame_lib.frame.actys.BaseActivity;
import com.example.smartcooker.R;

/**
 * Created by ke on 2018/5/6.
 */

public class SearchActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContent_layout(R.layout.activity_search);
        getTitle_Layout().setBackDismiss(false)
                .setBackSrc(R.drawable.icon_back_orange)
                .setRightDismiss(true)
                .setTitleText("搜索美味")
                .setTitleColor("#ff6600")
                .setBackgroundColor(getResources().getDrawable(R.drawable.back_white))
                .setBackOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });
    }

    @Override
    public void refreshUi(Object result, int taskId) {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }
}
