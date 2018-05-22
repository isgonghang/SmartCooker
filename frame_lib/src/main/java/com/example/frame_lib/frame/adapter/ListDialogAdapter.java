package com.example.frame_lib.frame.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.frame_lib.R;

import java.util.List;

/**
 * Created by ke on 2018/2/27.
 */

public class ListDialogAdapter extends BaseAdapter {

    List<String> mData;
    private LayoutInflater mInflater;
    private TextView content;

    public ListDialogAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }

    public ListDialogAdapter(Context context, List<String> list) {
        this.mInflater = LayoutInflater.from(context);
        mData = list;
    }

    public void setListData(List<String> mData) {
        this.mData = mData;
    }

    @Override
    public int getCount() {
        return mData.size();

    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.popup_list_item, null);
            content = (TextView) convertView.findViewById(R.id.tv_popup_1);
            convertView.setTag(content);
        } else {
            content = (TextView) convertView.getTag();
        }
        if (position == 0) {
            content.setTextColor(Color.parseColor("#000000"));
        }
        content.setText((String) mData.get(position));

        return convertView;
    }

    @Override
    public boolean isEnabled(int position) {
        if (position == 0){
            return false;
        }
        else return true;
    }
}
