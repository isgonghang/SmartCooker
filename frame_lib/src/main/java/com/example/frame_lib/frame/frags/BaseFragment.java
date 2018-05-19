package com.example.frame_lib.frame.frags;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.frame_lib.R;
import com.example.frame_lib.frame.callbacks.UiCallBack;
import com.example.frame_lib.frame.config.TaskIdConfig;
import com.example.mytitlelyoutlib.TitleLayout;

/**
 * Created by ke on 2018/4/17.
 */

abstract public class BaseFragment extends Fragment implements UiCallBack {
    protected UiCallBack uiCallBack;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        uiCallBack = (UiCallBack) getActivity();
    }
}
