package com.example.sidetablayout_lib;

import android.app.Fragment;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TableLayout;

import com.example.frame_lib.frame.config.TaskIdConfig;
import com.example.frame_lib.frame.frags.BaseFragment;
import com.example.mytitlelyoutlib.TitleLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ke on 2018/4/27.
 */

public class SideTabLayout extends RelativeLayout {
    private List<TopTabItem> items;
    private LinearLayout tab_root;
    private Scroller scroller;
    private boolean state = true;
    private static int s = 0;
    private int scrollerX;
    private int scrollerY;

    public SideTabLayout(Context context) {
        super(context);
    }

    public SideTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.side_tab, this);
        scroller = new Scroller(getContext());
        scrollerX = getScrollX();
        scrollerY = getScrollY();
        items = new ArrayList<>();
        tab_root = findViewById(R.id.tablayout_root);
        int count = tab_root.getChildCount();
        for (int i = 0; i < count; i++) {
            if (i != 1) {
                TopTabItem tempItem = (TopTabItem) tab_root.getChildAt(i);
                tempItem.index = i;
                if (i == 0)
                    tempItem.setTabText("云");
                else tempItem.setTabText("本地");
                items.add(tempItem);
            }
        }
    }

    public void setCurrentItem(int position) {
        switch (position) {
            case 0:
                items.get(1).setLineDismiss(true);
                items.get(0).setLineDismiss(false);
                break;
            case 2:
                items.get(1).setLineDismiss(false);
                items.get(0).setLineDismiss(true);
                break;
        }
    }

    public void setChangeListener(OnChangeListener changeListener) {
        for (int i = 0; i < items.size(); i++) {
            items.get(i).setChangeListener(changeListener);
        }
    }

    public void onTabSlide(float dx, float dy, BaseFragment fragment) {
        Log.i("f", "close: "+dy);

        int dealtX = this.getWidth();
        boolean isClose;
        if (dy > 0) {
            isClose = true;
        } else {
            isClose = false;
        }
        if (isClose && state) {
            Log.i("miao", "close: ");
            state = false;
            //关闭tab
            //fragment.refreshUi("", TaskIdConfig.CLOSE_TITLELAYOUT);
            scroller.startScroll(0, scrollerY, -dealtX, 0, 100);
        } else if (!isClose && !state) {
            state = true;
            //打开tab
            Log.i("miao", "open: ");
           // fragment.refreshUi("", TaskIdConfig.OPEN_TITLELAYPUT);
            scroller.startScroll(-dealtX, scrollerY, dealtX, 0, 100);
        }
        invalidate();
    }


    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.getCurrX(), scroller.getCurrY());
            postInvalidate();
        }
    }
}
