package com.example.frame_lib.frame.application;

import android.app.Application;
import android.content.Context;

import com.example.frame_lib.frame.Ble.BleConnectionHelper;

/**
 * Created by ke on 2018/4/17.
 */

public class BaseApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        if (context == null)
            context = getApplicationContext();
        BleConnectionHelper.initBle();
    }

    public static Context getContext() {
        return context;
    }
}
