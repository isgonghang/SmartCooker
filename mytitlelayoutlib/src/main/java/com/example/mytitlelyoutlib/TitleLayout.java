package com.example.mytitlelyoutlib;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;


/**
 * Created by ke on 2018/4/23.
 */

public class TitleLayout extends RelativeLayout {
    private ImageView image_back, image_right;
    private TextView text_title;
    RelativeLayout title_root, title_re;
    private Scroller scroller;
    private boolean state = true;


    public TitleLayout(Context context) {
        super(context);
    }

    public TitleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.title_layout, this);
        scroller = new Scroller(context);
        title_root = findViewById(R.id.title_root);
        title_re = findViewById(R.id.title_re);
        image_back = findViewById(R.id.image_back);
        image_right = findViewById(R.id.image_right);
        text_title = findViewById(R.id.textView_title);
        image_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) getContext()).onBackPressed();
            }
        });
    }

    public void setRightOnClickListener(OnClickListener clickListener) {
        image_right.setOnClickListener(clickListener);
    }

    public void setBackOnClickListener(OnClickListener clickListener) {
        image_back.setOnClickListener(clickListener);
    }

    public TitleLayout setBackSrc(int id) {
        image_back.setImageResource(id);
        return this;
    }

    public TitleLayout setRightSrc(int id) {
        image_right.setBackground(getResources().getDrawable(id));
        return this;
    }

    public TitleLayout setBackDismiss(Boolean dismiss) {
        if (dismiss)
            image_back.setVisibility(GONE);
        else
            image_back.setVisibility(VISIBLE);
        return this;
    }

    public TitleLayout setRightDismiss(boolean dismiss) {
        if (dismiss)
            image_right.setVisibility(GONE);
        else
            image_right.setVisibility(VISIBLE);
        return this;
    }

    public TitleLayout setTitleText(String text) {
        text_title.setText(text);
        return this;
    }

    public TitleLayout setTitleSize(int size) {
        text_title.setTextSize(size);
        return this;
    }

    public TitleLayout setTitleColor(String c) {
        text_title.setTextColor(Color.parseColor(c));
        return this;
    }

    public TitleLayout setBackgroundAlpha(float alpha) {
        title_root.setAlpha(alpha);
        return this;
    }

    public TitleLayout setBackgroundColor(String c) {
        title_root.setBackgroundColor(Color.parseColor(c));
        return this;
    }

    public TitleLayout setBackgroundColor(Drawable drawable) {
        title_re.setBackground(drawable);
        return this;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.getCurrX(), scroller.getCurrY());
            postInvalidate();
        }
    }

    public String getTitleString() {
        return text_title.getText().toString();
    }

    public boolean getLeftState() {
        if (image_back.getVisibility() == GONE)
            return true;
        return false;
    }
    public boolean getRightState() {
        if (image_right.getVisibility() == GONE)
            return true;
        return false;
    }

    public void onHide(boolean isClose) {
        int dealtY = this.getHeight();
        if (isClose && state) {
            //关闭tab
            scroller.startScroll(0, 0, 0, dealtY, 500);
            setVisibility(GONE);
            state = false;
        } else if (!isClose && !state) {
            //打开tab
            scroller.startScroll(0, -dealtY, 0, dealtY, 500);
            setVisibility(VISIBLE);
            state = true;
        }
        invalidate();
    }
}