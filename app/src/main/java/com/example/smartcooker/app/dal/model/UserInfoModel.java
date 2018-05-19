package com.example.smartcooker.app.dal.model;

/**
 * Created by ke on 2018/4/17.
 */

public class UserInfoModel {
    private String UserName;//用户名
    private String UserPassword;//用户密码
    private String UserEmailAddress;//用户电子邮件
    private String UserPhoenNumber;//用户手机号
    private String DeviceId;//设备序列号
    private String LoginState;//登录状态

    public void setUserName(String userName) {
        UserName = userName;
    }

    public void setUserPassword(String userPassword) {
        UserPassword = userPassword;
    }

    public void setUserEmailAddress(String userEmailAddress) {
        UserEmailAddress = userEmailAddress;
    }

    public void setUserPhoenNumber(String userPhoenNumber) {
        UserPhoenNumber = userPhoenNumber;
    }

    public void setDeviceId(String deviceId) {
        DeviceId = deviceId;
    }

    public String getDeviceId() {
        return DeviceId;
    }

    public void setLoginState(String loginState) {
        LoginState = loginState;
    }

    public String getUserName() {
        return UserName;
    }

    public String getUserPassword() {
        return UserPassword;
    }

    public String getUserEmailAddress() {
        return UserEmailAddress;
    }

    public String getUserPhoenNumber() {
        return UserPhoenNumber;
    }


    public String getLoginState() {
        return LoginState;
    }
}
