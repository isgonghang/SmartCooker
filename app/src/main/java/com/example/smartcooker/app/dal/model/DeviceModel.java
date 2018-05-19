package com.example.smartcooker.app.dal.model;

/**
 * Created by ke on 2018/4/17.
 */

public class DeviceModel {
    private String DeviceId;
    //设备状态
    private static final int Status_working = 0;//工作中
    private static final int Status_disconnect = 1;//未连接
    private static final int Status_connect = 2;//已连接
    private static final int Status_wraning = 3;//设备异常
    private static final int Status_waiting = 4;//待机中
    private String deviceName;
    private String deviceState;
    private String deviceMac;
    private int[] Location;//设备定位

    public String getDeviceId() {
        return DeviceId;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public void setDeviceState(int deviceState) {
        switch (deviceState) {
            case 2:
                this.deviceState = "已连接";
                break;
        }
    }

    public void setDeviceMac(String deviceMac) {
        this.deviceMac = deviceMac;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public String getDeviceState() {
        return deviceState;
    }

    public String getDeviceMac() {
        return deviceMac;
    }

    public static int getStatus_working() {
        return Status_working;
    }

    public static int getStatus_disconnect() {
        return Status_disconnect;
    }

    public static int getStatus_connect() {
        return Status_connect;
    }

    public static int getStatus_wraning() {
        return Status_wraning;
    }

    public static int getStatus_waiting() {
        return Status_waiting;
    }

    public int[] getLocation() {
        return Location;
    }

    public void setDeviceId(String deviceId) {
        DeviceId = deviceId;
    }

    public void setLocation(int[] location) {
        Location = location;
    }
}
