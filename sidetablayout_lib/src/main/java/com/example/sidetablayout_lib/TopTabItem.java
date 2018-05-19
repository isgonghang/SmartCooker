package com.example.sidetablayout_lib;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by ke on 2018/4/27.
 */

public class TopTabItem extends RelativeLayout implements View.OnClickListener {
    private RelativeLayout item_root;
    private TextView line, text;
    public int index;
    private OnChangeListener changeListener;

    public TopTabItem(Context context) {
        super(context);
    }

    public TopTabItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.top_tab_item, this);
        setOnClickListener(this);
        line = findViewById(R.id.tab_line);
        text = findViewById(R.id.text);
        item_root = findViewById(R.id.item_root);
    }

    public void setChangeListener(OnChangeListener changeListener) {
        this.changeListener = changeListener;
    }

    @Override
    public void onClick(View view) {
        this.changeListener.onSelected(index, null);
    }

    public void setLineDismiss(boolean dis) {
        if (dis) {
            line.setBackgroundColor(Color.parseColor("#e1e1e1"));
        } else   line.setBackgroundColor(Color.parseColor("#ff6600"));
    }

    public void setTabText(String text) {
        this.text.setText(text);
    }
}
