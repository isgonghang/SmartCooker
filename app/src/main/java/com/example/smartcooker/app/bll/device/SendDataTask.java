package com.example.smartcooker.app.bll.device;

import android.util.Log;

import com.example.frame_lib.frame.callbacks.UiCallBack;
import com.example.frame_lib.frame.config.TaskIdConfig;
import com.example.frame_lib.frame.task.BaseTask;
import com.example.smartcooker.app.dal.dataGatter.SendDataToCloud;
import com.example.smartcooker.app.dal.model.CloudRecipeListModel;
import com.example.smartcooker.app.dal.model.HeatingArgsModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.callback.Callback;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by ke on 2018/4/18.
 */

public class SendDataTask extends BaseTask {
    private UiCallBack mCallBack;
    private Object params;
    private HeatControlCallback controlCallback = new HeatControlCallback() {
        @Override
        public void onError(Call call, Exception e, int id) {

        }

        @Override
        public void onResponse(Boolean response, int id) {
            if (response)
                mCallBack.refreshUi(response, TaskIdConfig.ON_SEND_CONTROL_SUCCESS);
        }
    };

    public SendDataTask(UiCallBack mCallBack) {
        this.mCallBack = mCallBack;
    }

    @Override
    public void execute() {

      //  SendDataToCloud.send(controlCallback, (HeatingArgsModel) params);
    }

    public void addParams(Object params) {
        this.params = params;
    }

    public abstract class HeatControlCallback extends Callback<Boolean> {
        @Override
        public Boolean parseNetworkResponse(okhttp3.Response response, int id) throws Exception {
            Log.i("miaob", "parseNetworkResponse: " + response);
            String string = response.body().string();
            Gson gson = new Gson();
            boolean is = gson.fromJson(string, Boolean.class);
            return is;
        }
    }
}
