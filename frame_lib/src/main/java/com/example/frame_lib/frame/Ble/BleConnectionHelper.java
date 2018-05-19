package com.example.frame_lib.frame.Ble;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.example.frame_lib.frame.application.BaseApplication;
import com.vise.baseble.ViseBle;
import com.vise.baseble.callback.IBleCallback;
import com.vise.baseble.callback.IConnectCallback;
import com.vise.baseble.callback.scan.IScanCallback;
import com.vise.baseble.callback.scan.ScanCallback;
import com.vise.baseble.callback.scan.UuidFilterScanCallback;
import com.vise.baseble.model.BluetoothLeDevice;
import com.vise.baseble.model.BluetoothLeDeviceStore;
import com.vise.baseble.utils.BleUtil;

import java.util.UUID;


/**
 * Created by ke on 2018/4/26.
 */

public class BleConnectionHelper {


    public static void initBle() {
        //蓝牙相关配置修改
        ViseBle.config()
                .setScanTimeout(5000)//扫描超时时间，这里设置为永久扫描
                .setScanRepeatInterval(1 * 1000)//扫描间隔5秒
                .setConnectTimeout(5 * 1000)//连接超时时间
                .setOperateTimeout(5 * 1000)//设置数据操作超时时间
                .setConnectRetryCount(1)//设置连接失败重试次数
                .setConnectRetryInterval(1000)//设置连接失败重试间隔时间
                .setOperateRetryCount(3)//设置数据操作失败重试次数
                .setOperateRetryInterval(1000)//设置数据操作失败重试间隔时间
                .setMaxConnectCount(3);//设置最大连接设备数量
        //蓝牙信息初始化，全局唯一，必须在应用初始化时调用
        ViseBle.getInstance().init(BaseApplication.getContext());
    }

    /**
     * 检查蓝牙权限
     */
    public static void checkBlePermission(Activity activity) {
        //Android6.0需要动态申请权限
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //请求权限
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {
//                //判断是否跟用户做一个说明
//                DialogUtils.shortT(getApplicationContext(), "需要蓝牙权限");
            }
        }
    }

    /**
     * 开启蓝牙
     */
    public static void enableBluetooth(Activity activity) {
            BleUtil.enableBluetooth(activity, 1);
    }


    public static void connect(UuidFilterScanCallback uuidFilterScanCallback) {
        ViseBle.getInstance().startScan(uuidFilterScanCallback);
    }
    public static void connectByMac(String mac, IConnectCallback callback) {
        ViseBle.getInstance().connectByMac(mac,callback);
    }
    public static void stopScan(ScanCallback scanCallback){
        ViseBle.getInstance().stopScan(scanCallback);
    }
}
