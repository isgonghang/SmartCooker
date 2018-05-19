package com.example.mylibrary;


import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mylibrary.navigationbar.R;

/**
 * Created by ke on 2018/4/23.
 */

public class NavigationItem extends RelativeLayout {
    ImageView image_icon;
    TextView text;
    NavigationBar navigationBar;
    int index;

    public NavigationItem(Context context) {
        super(context);
    }

    public NavigationItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.navigationitem, this);
        image_icon = findViewById(R.id.image_icon);
        text = findViewById(R.id.text_bar);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                navigationBar.onChange(index);
            }
        });
    }

    public void setNormalState(Item item) {
        image_icon.setImageResource(item.getId());
        text.setText(item.getText());
        text.setTextColor(Color.parseColor("#000000"));
    }

    public void setSelectedState(Item item) {
        image_icon.setImageResource(item.getId2());
        text.setText(item.getText());
        text.setTextColor(Color.parseColor("#a0ff6600"));
    }


}
