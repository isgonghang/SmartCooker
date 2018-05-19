package com.example.smartcooker.app.bll.user;

import com.example.smartcooker.app.dal.dataGatter.UserInfoManager;
import com.example.frame_lib.frame.task.BaseTask;

/**
 * Created by ke on 2018/4/17.
 */

public class UserLogoutTask extends BaseTask{
   //登录业务逻辑
    public static void logout() {
        if (UserInfoManager.getLoginState()) {
            // TODO: 2018/4/17 提示登录状态
        } else {
            UserInfoManager.setLogoutState();
            // TODO: 2018/4/17  HttpUtil进行登出
        }
    }

    @Override
    public void execute() {

    }
}
