package com.example.mylibrary;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.mylibrary.navigationbar.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ke on 2018/4/23.
 */

public class NavigationBar extends RelativeLayout implements ChangeItem {
    private List<NavigationItem> itemList;
    private NavigationItem currentItem;
    private LinearLayout item_root;
    private int count;
    private List<Item> items;
    ViewPager viewPager;

    public NavigationBar(Context context) {
        super(context);
    }

    public NavigationBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        itemList = new ArrayList<>();
        items = new ArrayList<>();
        LayoutInflater.from(context).inflate(R.layout.navigation_layout, this);
        item_root = findViewById(R.id.item_root);
        count = item_root.getChildCount();
        for (int i = 0; i < count; i++) {
            NavigationItem tempItem = (NavigationItem) item_root.getChildAt(i);
            tempItem.navigationBar = this;
            tempItem.index = i;
            itemList.add(tempItem);
        }
        currentItem = itemList.get(0);
    }

    private void setItem(List<Item> item) {
        for (int i = 0; i < item.size(); i++) {
            if (itemList.get(i) != null)
                if (i == 0) itemList.get(0).setSelectedState(item.get(0));
                else
                    itemList.get(i).setNormalState(item.get(i));
        }
    }

    public NavigationBar addItem(int normalResId, int selectedResId, String text) {
        items.add(new Item(normalResId, selectedResId, text));
        return this;
    }

    public NavigationBar addNullItem() {
        items.add(new Item());
        return this;
    }

    public void build() {
        if (items != null)
            setItem(items);
    }

    public NavigationBar setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
        return this;
    }

    @Override
    public void onChange(int index) {
        if (index != 2) {

            //设置当前item为选中状态
            currentItem = itemList.get(index);
            for (int i = 0; i < itemList.size(); i++) {
                itemList.get(i).setNormalState(items.get(i));
            }
            currentItem.setSelectedState(items.get(index));

            //改变当前viewpager
            if (index == 3 || index == 4) {
                index -= 1;
            }
            viewPager.setCurrentItem(index);
        }
    }

    public void onPagerChanged(int state) {
        //设置当前item为选中状态
        if (state > 1) {
            state += 1;
        }
        currentItem = itemList.get(state);
        for (int i = 0; i < itemList.size(); i++) {
            itemList.get(i).setNormalState(items.get(i));
        }
        currentItem.setSelectedState(items.get(state));
    }
}
