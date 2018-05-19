package com.example.smartcooker.app.bll.user;

import com.example.smartcooker.app.dal.dataGatter.UserInfoManager;
import com.example.frame_lib.frame.task.BaseTask;

/**
 * Created by ke on 2018/4/17.
 */

public class UserLoginTask extends BaseTask {
    //登录业务逻辑
    public static void login() {
        if (UserInfoManager.getLoginState()) {
            // TODO: 2018/4/17 提示已经登录
        } else {
            UserInfoManager.setLoginState();
            // TODO: 2018/4/17  HttpUtil进行登录
        }
    }

    @Override
    public void execute() {

    }
}
