package com.example.smartcooker.app.ui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.smartcooker.R;
import com.example.smartcooker.app.dal.model.ChartArgsModel;

/**
 * Created by ke on 2018/5/15.
 */

public class DefineTime extends LinearLayout {
    private ImageView close_image;
    private LinearLayout mRoot;
    private View v;
    private EditText editText_grow, editText_heat, editText_temp;

    public DefineTime(Context context) {
        super(context);
    }

    public DefineTime(Context context, View root) {
        super(context);
        mRoot = (LinearLayout) root;
        v = inflate(context, R.layout.item_define_time, this);
        close_image = findViewById(R.id.close);
        editText_grow = findViewById(R.id.grow);
        editText_heat = findViewById(R.id.zhufei);
        editText_temp = findViewById(R.id.temp);
        close_image.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mRoot.removeView(v);
            }
        });
    }

    public ChartArgsModel getArgs() {
        ChartArgsModel argsModel = new ChartArgsModel();
        if (!(editText_grow.getText().toString().equals("") && editText_heat.getText().toString().equals("") && editText_temp.getText().toString().equals(""))) {
            argsModel.setGrow_heat_time(Integer.valueOf(editText_grow.getText().toString()));
            argsModel.setTemp(Integer.valueOf(editText_temp.getText().toString()));
            argsModel.setHeating_time(Integer.valueOf(editText_heat.getText().toString()));
            return argsModel;
        }
        return null;
    }
}
