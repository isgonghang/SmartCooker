package com.example.smartcooker.app.dal.dataGatter;

/**
 * Created by ke on 2018/4/17.
 */

public class UserInfoManager {
    public static boolean getLoginState() {
        boolean result = false;
        // TODO: 2018/4/17 查询登录状态 sqlite
        return result;
    }

    public static synchronized void setLoginState() {
        // TODO: 2018/4/17 设置登录状态 sqlite
    }

    public static synchronized void setLogoutState() {
        // TODO: 2018/4/17 设置登出状态 sqlite
    }

    public static String getUserToker() {
        String result = "";
        // TODO: 2018/4/17  获取用户token

        return result;
    }
}
