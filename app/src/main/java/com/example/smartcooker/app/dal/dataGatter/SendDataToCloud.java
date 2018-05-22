package com.example.smartcooker.app.dal.dataGatter;

import com.example.frame_lib.frame.config.HttpConfig;
import com.example.smartcooker.app.bll.device.SendDataTask;
import com.example.smartcooker.app.bll.recipe.GetCloudRecipeListTask;
import com.example.smartcooker.app.dal.model.HeatingArgsModel;
import com.example.smartcooker.app.ui.frags.MeFragment;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;

/**
 * Created by ke on 2018/4/18.
 */

public class SendDataToCloud {

    public static void send(SendDataTask.HeatControlCallback controlCallback, HeatingArgsModel model) {
        Gson gson = new Gson();
        String jstr = gson.toJson(model);
        //    String url = HttpConfig.SEND_HEAT_ARGS_URL;
        OkHttpUtils.get()
                .url(MeFragment.url)
                .addParams("0", jstr)//0表示控制信息
                .build()
                .execute(controlCallback);
    }
}


