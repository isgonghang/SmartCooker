package com.example.frame_lib.frame.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyboardShortcutGroup;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;


import com.example.frame_lib.R;

import java.util.ArrayList;
import java.util.List;

public class CustomDialog extends Dialog implements View.OnClickListener {
    private Context context;      // 上下文
    private int mLayoutResId;      // 布局文件id
    private int[] mIds = new int[]{};  // 要监听的控件id
    private int mAnimationResId = 0;//主题style
    private OnCustomDialogItemClickListener listener;
    private boolean mIsDismiss = true;//是否默认所有按钮点击后取消dialog显示，false时需要在点击事件后手动调用dismiss
    private boolean mIsDismissTouchOut = true;//是否允许触摸dialog外部区域取消显示dialog
    private int mPosition = 0; //Dialog 相对页面显示的位置
    private List<View> mViews = new ArrayList<>();//监听的View的集合
    private int offsetX = 0;
    private int offsetY = 0;//dialog显示的x,y轴偏移量

    public void setOnDialogItemClickListener(OnCustomDialogItemClickListener listener) {
        this.listener = listener;
    }

    public CustomDialog(Context context, int layoutResID) {
        super(context, R.style.CustomDialogStyle);
        this.context = context;
        this.mLayoutResId = layoutResID;

    }

    public CustomDialog(Context context, int layoutResID, int[] listenedItems) {
        super(context, R.style.CustomDialogStyle); //dialog的样式
        this.context = context;
        this.mLayoutResId = layoutResID;
        this.mIds = listenedItems;
    }

    public CustomDialog(Context context, int layoutResID, int[] listenedItems, int animationResId) {
        super(context, R.style.CustomDialogStyle); //dialog的样式
        this.context = context;
        this.mLayoutResId = layoutResID;
        this.mIds = listenedItems;
        this.mAnimationResId = animationResId;
    }

    public CustomDialog(Context context, int layoutResID, int[] listenedItems, boolean isDismiss) {
        super(context, R.style.CustomDialogStyle); //dialog的样式
        this.context = context;
        this.mLayoutResId = layoutResID;
        this.mIds = listenedItems;
        this.mIsDismiss = isDismiss;
    }

    public CustomDialog(Context context, int layoutResID, int[] listenedItems, boolean isDismiss, boolean isDismissTouchOut) {
        super(context, R.style.CustomDialogStyle); //dialog的样式
        this.context = context;
        this.mLayoutResId = layoutResID;
        this.mIds = listenedItems;
        this.mIsDismiss = isDismiss;
        this.mIsDismissTouchOut = isDismissTouchOut;
    }

    public CustomDialog(Context context, int layoutResID, int[] listenedItems, boolean isDismiss, int position) {
        super(context, R.style.CustomDialogStyle); //dialog的样式
        this.context = context;
        this.mLayoutResId = layoutResID;
        this.mIds = listenedItems;
        this.mPosition = position;


    }


    /**
     * @param context
     * @param layoutResID       布局Id
     * @param ids               需要监听的View id集合
     * @param animationResId    动画资源id
     * @param isDismiss         是否默认点击所有View 取消dialog显示
     * @param isDismissTouchOut 是否触摸dialog外部区域消失dialog显示
     * @param position          dialog显示的位置
     */
    public CustomDialog(Context context,
                        int layoutResID,
                        int[] ids,
                        int animationResId,
                        boolean isDismiss,
                        boolean isDismissTouchOut,
                        int position) {
        super(context, R.style.CustomDialogStyle);
        this.context = context;
        this.mLayoutResId = layoutResID;
        this.mIds = ids;
        this.mAnimationResId = animationResId;
        this.mIsDismiss = isDismiss;
        this.mIsDismissTouchOut = isDismissTouchOut;
        this.mPosition = position;

    }

    /**
     * @param context
     * @param layoutResID       布局Id
     * @param ids               需要监听的View id集合
     * @param animationResId    动画资源id
     * @param isDismiss         是否默认点击所有View 取消dialog显示
     * @param isDismissTouchOut 是否触摸dialog外部区域消失dialog显示
     * @param position          dialog显示的位置
     * @param offsetX           dialog显示的x偏移量
     * @param offsetY           dialog显示y偏移量
     */
    public CustomDialog(Context context,
                        int layoutResID,
                        int[] ids,
                        int animationResId,
                        boolean isDismiss,
                        boolean isDismissTouchOut,
                        @Nullable int position,
                        int offsetX,
                        int offsetY) {
        super(context, R.style.CustomDialogStyle);
        this.context = context;
        this.mLayoutResId = layoutResID;
        this.mIds = ids;
        this.mAnimationResId = animationResId;
        this.mIsDismiss = isDismiss;
        this.mIsDismissTouchOut = isDismissTouchOut;
        this.mPosition = position;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        if (0 == mPosition) {
            window.setGravity(Gravity.CENTER); // dialog默认显示的位置为居中
        } else {
            window.setGravity(mPosition);// 设置自定义的dialog位置
        }

        if (mAnimationResId == 0) {
            window.setWindowAnimations(R.style.BottomAnimation); // 添加默认动画效果
        } else if (mAnimationResId == 1) {
            window.setWindowAnimations(R.style.TopAnimation);    // 添加默认动画效果
        } else {
            window.setWindowAnimations(mAnimationResId);//添加自定义动画
        }
        setContentView(mLayoutResId);

        WindowManager.LayoutParams lp = window.getAttributes();
//        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        if ((offsetY != 0) || (offsetX != 0)) {
            lp.x = offsetX;
            lp.y = offsetY;  //弹出位置的y坐标
        }

        WindowManager windowManager = window.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        lp.width = display.getWidth(); // 设置dialog宽度为屏幕宽度
        window.setAttributes(lp);

        setCanceledOnTouchOutside(mIsDismissTouchOut);
        //遍历控件id,添加点击事件，添加资源到集合
        for (int id : mIds) {
            View view = findViewById(id);
            if (view instanceof ListView) {

            } else {
                view.setOnClickListener(this);
            }
            mViews.add(view);
        }
    }

    public View getView(int id) {
        return findViewById(id);
    }

    /**
     * 获取需要监听的View集合
     *
     * @return
     */
    public List<View> getViews() {
        return mViews;
    }

    @Override
    public void onProvideKeyboardShortcuts(List<KeyboardShortcutGroup> data, @Nullable Menu menu, int deviceId) {

    }

    public interface OnCustomDialogItemClickListener {
        void OnCustomDialogItemClick(CustomDialog dialog, View view);
    }


    @Override
    public void onClick(View view) {
        //是否默认所有按钮点击后取消dialog显示，false是需要在点击事件后手动调用dismiss。
        if (mIsDismiss) {
            dismiss();
        }
        if (listener != null) {
            listener.OnCustomDialogItemClick(this, view);
        }
    }

    // 获取控件下显示Dialog在窗口上的坐标，单位px
    public static int[] getDialogLocationUnderViewInWindow(View view) {
        int[] location = new int[2];
        view.getLocationInWindow(location); // 若是普通activity，则y坐标为可见的状态栏高度+可见的标题栏高度+view左上角到标题栏底部的距离
        Rect rect = new Rect();
        view.getWindowVisibleDisplayFrame(rect);
        // 状态栏的高度
        int statusBarHeight = rect.top;
        // 对话框顶部坐标（对话框或对话框式的activity，则y坐标为可见的标题栏高度+view到标题栏底部的距离）
        location[1] = location[1] + view.getHeight() - statusBarHeight;
        return location;
    }
}

