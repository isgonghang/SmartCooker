package com.example.frame_lib.frame.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.frame_lib.R;
import com.example.frame_lib.frame.adapter.ListDialogAdapter;

import java.util.ArrayList;
import java.util.List;

public class PopDialogHelper {
    ListDialogAdapter popupListViewAdapter;
    private ListView listView;
    private TextView tvTitle;
    private TextView tvCancel;

    List<String> listData;
    private Context context;
    public static final int CHOOSE_LOCATION = 00;
    public static final int CHOOSE_PINPAI = 01;
    public static final int CHOOSE_ACTER = 02;
    public static final int CHOOSE_RENT_ORDER = 03;
    public static final int CHOOSE_ACT_ORDER = 04;
    private CustomDialog dialog;
    int[] resIds = {};

    public PopDialogHelper(Context context) {
        this.context = context;
        listData = new ArrayList();
        popupListViewAdapter = new ListDialogAdapter(context);
    }


    public PopDialogHelper buildDialog(int offsetx, int offsety) {
        dialog = new CustomDialog(context, R.layout.popup_location_choose, resIds, 1,
                true, true, Gravity.TOP | Gravity.LEFT, offsetx, offsety);
        dialog.show();
        initView();
        initAdapter(listData);
        return this;
    }
    public PopDialogHelper buildMenu(int offsetx, int offsety) {
        dialog = new CustomDialog(context, R.layout.popup_menu_choose, resIds, 0,
                true, true, Gravity.TOP | Gravity.RIGHT, offsetx, offsety);
        dialog.show();
        initView();
        initAdapter(listData);
        return this;
    }
    public PopDialogHelper buildDialogInCenter() {
        dialog = new CustomDialog(context, R.layout.popup_location_choose, resIds, 1,
                true, true, 0, 0, 0);
        dialog.show();
        initView();
        initAdapter(listData);
        return this;
    }
    public PopDialogHelper buildLayoutDialogInCenter() {
        dialog = new CustomDialog(context, R.layout.layout_dialog_real, resIds, 1,
                true, true, 0, 0, 0);
        dialog.show();
        return this;
    }
    public PopDialogHelper buildDialog(int offsetx, int offsety, List<String> list) {
        dialog = new CustomDialog(context, R.layout.popup_location_choose, resIds, 1,
                true, true, Gravity.TOP | Gravity.LEFT, offsetx, offsety);
        listData = list;

        dialog.show();
        initView();
        initAdapter(listData);
        return this;
    }

    public PopDialogHelper setColor() {
        listView.setDivider(context.getDrawable(R.drawable.listview_devider_shape));

        return this;
    }

    private void initView() {
        listView = dialog.findViewById(R.id.lv_dialog);
        tvCancel = dialog.findViewById(R.id.dialog_cancel);
        tvTitle = dialog.findViewById(R.id.tv_choose_type);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    public PopDialogHelper setTitle(String title) {
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText(title);
        return this;
    }

    public PopDialogHelper showButtonCancel(boolean shown) {
        if (shown) {
            tvCancel.setVisibility(View.VISIBLE);
        } else {
            tvCancel.setVisibility(View.GONE);
        }
        return this;
    }

    public PopDialogHelper showButtonCancel(View.OnClickListener listener) {
        tvCancel.setVisibility(View.VISIBLE);
        tvCancel.setOnClickListener(listener);
        return this;
    }

    public void setOnCancelClickListener(View.OnClickListener listener) {
        tvCancel.setOnClickListener(listener);
    }

    public PopDialogHelper showTitleBar(boolean shown) {
        RelativeLayout rlTitleBar = dialog.findViewById(R.id.dialog_title_bar);
        if (shown) {
            rlTitleBar.setVisibility(View.VISIBLE);
        } else {
            rlTitleBar.setVisibility(View.GONE);
        }
        return this;
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        listView.setOnItemClickListener(onItemClickListener);
    }

    public void show() {
        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }

    public PopDialogHelper initAdapter(List brandiList) {
        popupListViewAdapter.setListData(brandiList);
        listView.setAdapter(popupListViewAdapter);
        return this;
    }


    public List<String> getListData() {
        return listData;
    }

    public PopDialogHelper setListData(List<String> listData) {
        this.listData = listData;
        popupListViewAdapter.setListData(listData);
        return this;
    }
}
