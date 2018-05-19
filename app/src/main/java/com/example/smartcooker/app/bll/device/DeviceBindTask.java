package com.example.smartcooker.app.bll.device;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.example.frame_lib.frame.Ble.BleConnectionHelper;
import com.example.frame_lib.frame.callbacks.UiCallBack;
import com.example.frame_lib.frame.config.BleConfig;
import com.example.frame_lib.frame.config.TaskIdConfig;
import com.example.frame_lib.frame.task.BaseTask;
import com.example.smartcooker.app.dal.model.DeviceModel;
import com.vise.baseble.ViseBle;
import com.vise.baseble.callback.IConnectCallback;
import com.vise.baseble.callback.scan.IScanCallback;
import com.vise.baseble.callback.scan.ScanCallback;
import com.vise.baseble.callback.scan.UuidFilterScanCallback;
import com.vise.baseble.core.DeviceMirror;
import com.vise.baseble.exception.BleException;
import com.vise.baseble.model.BluetoothLeDevice;
import com.vise.baseble.model.BluetoothLeDeviceStore;
import com.vise.baseble.utils.BleUtil;

/**
 * Created by ke on 2018/4/18.
 */

public class DeviceBindTask extends BaseTask {
    private int taskId;
    private UiCallBack uiCallBack;
    private Activity activity;
    private UuidFilterScanCallback uuidFilterScanCallback = new UuidFilterScanCallback(new IScanCallback() {
        @Override
        public void onDeviceFound(BluetoothLeDevice bluetoothLeDevice) {
            Log.i("miao", "onDeviceFound: " + bluetoothLeDevice);
            uiCallBack.refreshUi("", TaskIdConfig.DEVICE_SCAN_SUCCESS_TASK);

            ViseBle.getInstance().connect(bluetoothLeDevice, new IConnectCallback() {
                @Override
                public void onConnectSuccess(DeviceMirror deviceMirror) {
                    Log.i("miao", "onDeviceFound: " + deviceMirror);
                    // TODO: 2018/4/28 处理device设备信息
                    uiCallBack.refreshUi("连接成功", TaskIdConfig.DEVICE_CONNECT_SUCCESS_TASK);
                }

                @Override
                public void onConnectFailure(BleException exception) {
                    uiCallBack.refreshUi("连接失败", TaskIdConfig.DEVICE_CONNECT_FAILUER_TASK);
                }

                @Override
                public void onDisconnect(boolean isActive) {

                }
            });

        }

        @Override
        public void onScanFinish(BluetoothLeDeviceStore bluetoothLeDeviceStore) {

        }

        @Override
        public void onScanTimeout() {
            uiCallBack.refreshUi("", TaskIdConfig.DEVICE_SCAN_FAILURE_TASK);
        }
    }).setUuid(BleConfig.uuid);

    public DeviceBindTask(Activity activity) {
        taskId = TaskIdConfig.DEVICE_BIND_TASK;
        this.activity = activity;
        this.uiCallBack = (UiCallBack) activity;
    }

    public DeviceBindTask(UiCallBack fragment) {
        taskId = TaskIdConfig.DEVICE_BIND_TASK;
        Fragment tempFragment;
        tempFragment = (Fragment) fragment;
        this.activity = tempFragment.getActivity();
        this.uiCallBack = fragment;
    }

    public void execute() {
        BleConnectionHelper.checkBlePermission(activity);
        if (BleUtil.isBleEnable(activity)) {
            uiCallBack.refreshUi("", TaskIdConfig.DEVICE_BIND_START_TASK);
        } else {
            BleConnectionHelper.enableBluetooth(activity);
        }
    }

    public void connect() {
        BleConnectionHelper.connectByMac(BleConfig.mac, iConnectCallback);
        // BleConnectionHelper.connect(uuidFilterScanCallback);
    }

    IConnectCallback iConnectCallback = new IConnectCallback() {
        @Override
        public void onConnectSuccess(final DeviceMirror deviceMirror) {

            Log.i("lanya", "onConnectSuccess: " + deviceMirror.toString());
            activity.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    uiCallBack.refreshUi("连接成功" + deviceMirror.toString(), TaskIdConfig.DEVICE_CONNECT_SUCCESS_TASK);
                    BleConnectionHelper.stopScan(new ScanCallback(new IScanCallback() {
                        @Override
                        public void onDeviceFound(BluetoothLeDevice bluetoothLeDevice) {

                        }

                        @Override
                        public void onScanFinish(BluetoothLeDeviceStore bluetoothLeDeviceStore) {

                        }

                        @Override
                        public void onScanTimeout() {

                        }
                    }));
                    uiCallBack.refreshUi(deviceMirror.getBluetoothLeDevice().getAddress(), TaskIdConfig.DEVICE_CONNECT_SUCCESS_TASK);
                }
            });
        }

        @Override
        public void onConnectFailure(BleException exception) {
            DeviceModel deviceModel = new DeviceModel();
            deviceModel.setDeviceName("我的智能锅");
            deviceModel.setDeviceMac(BleConfig.mac);
            deviceModel.setDeviceState(2);
            uiCallBack.refreshUi(deviceModel, TaskIdConfig.DEVICE_CONNECT_SUCCESS_TASK);
        }

        @Override
        public void onDisconnect(boolean isActive) {

        }
    };
}
