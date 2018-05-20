package com.example.smartcooker.app.bll.recipe;

import android.util.Log;

import com.example.frame_lib.frame.config.TaskIdConfig;
import com.example.frame_lib.frame.task.BaseTask;
import com.example.smartcooker.app.dal.dataGatter.GetCloudRecipeData;
import com.example.frame_lib.frame.callbacks.UiCallBack;
import com.example.smartcooker.app.dal.model.CloudRecipeListModel;
import com.example.smartcooker.app.dal.model.RecipeDetailModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Response;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by ke on 2018/4/18.
 */

public class GetCloudRecipeListTask extends BaseTask {
    private String content =
            "http://p1.pstatp.com/large/31db000435586d471cb0";
    private String circle =
            "http://imgsrc.baidu.com/forum/w=580/sign=06e93e0bbd12c8fcb4f3f6c5cc0392b4/7b8727292df5e0feed47c52a586034a85fdf72d2.jpg";
    private String content2 =
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1525849362978&di=60a8407011e3b2e7e4d5e42ca711ad0c&imgtype=0&src=http%3A%2F%2Fimg4.duitang.com%2Fuploads%2Fitem%2F201508%2F31%2F20150831162526_AYiLQ.jpeg";
    private String circle2 = "https://ps.ssl.qhimg.com/t01acd8b8929d9f9bb4.jpg";

    private String content3 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1526807948554&di=077cfb3d684252bfcb981aa0ada3cf51&imgtype=0&src=http%3A%2F%2F5b0988e595225.cdn.sohucs.com%2Fimages%2F20171109%2F9ef5f9ead3bc4b62a6fb3a40ca3bced1.gif";
    private String content4 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1526807782355&di=2dfc192c10c42acecef346fc390c3cd7&imgtype=0&src=http%3A%2F%2F5b0988e595225.cdn.sohucs.com%2Fimages%2F20171215%2Faadc8c3a5752429986786b0cdca70e4a.gif";
private String content5="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1526808426145&di=181989ca6f929b437e8c480a2f956d99&imgtype=0&src=http%3A%2F%2Fimg.tom61.com%2Ffile%2Fjiachangcaidaquan%2Fjiandanjiachangcai%2F2017-07-14%2Fad9063f43bb9c3b1bde67dda098ec547.gif";
    private String circle3 = "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2328138743,208104425&fm=27&gp=0.jpg";
    private String circle4="https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=816147309,838163382&fm=27&gp=0.jpg";
    private int taskId;
    private UiCallBack mCallBack;
    private RecipeListCallback recipeListCallback = new RecipeListCallback() {
        @Override
        public void onError(Call call, Exception e, int id) {
            Log.i("miao", "onResponse: eee" + e.toString());

        }

        @Override
        public void onResponse(List<CloudRecipeListModel> response, int id) {
            Log.i("miaoc", "onResponse: " + response.get(0).getCount_image());
            mCallBack.refreshUi(response, TaskIdConfig.ON_GET_CLOUD_RECIPE_LIST_SUCCESS);
        }
    };

    public GetCloudRecipeListTask(UiCallBack mCallBack) {
        taskId = TaskIdConfig.GET_CLOUD_RECIPE_TASK;
        this.mCallBack = mCallBack;
    }

    @Override
    public void execute() {
        //  GetCloudRecipeData.get(recipeListCallback);

        List<CloudRecipeListModel> list = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            if (i  == 0)
                list.add(new CloudRecipeListModel(i, content, circle, "开心的鱼", i * 10 + 57));
            else if (i ==1)
                list.add(new CloudRecipeListModel(i, content3, circle3, "小厨李xx", i * 10 + 59));
            else if (i  == 2)
                list.add(new CloudRecipeListModel(i, content2, circle4, "小厨李xx", i * 10 + 75));
            else if (i  == 3)
                list.add(new CloudRecipeListModel(i, content4, circle, "小厨李xx", i * 10 + 71));
            else if (i  ==4)
                list.add(new CloudRecipeListModel(i, content5, circle3, "小厨李xx", i * 10 + 77));

            else
                list.add(new CloudRecipeListModel(i, content2, circle2, "大厨张xx", i * 10 + 23));

        }
        Gson gson = new Gson();
        String jsonStr = gson.toJson(list);
        Log.i("miao", "生成的json为：" + jsonStr);
        Type listType = new TypeToken<ArrayList<CloudRecipeListModel>>() {
        }.getType();
        List<CloudRecipeListModel> user = gson.fromJson(jsonStr, listType);
        Log.i("miao", "解析后的结果为: " + user.toString());

        mCallBack.refreshUi(user, TaskIdConfig.ON_GET_CLOUD_RECIPE_LIST_SUCCESS);
    }

    public abstract class RecipeListCallback extends Callback<List<CloudRecipeListModel>> {
        @Override
        public List<CloudRecipeListModel> parseNetworkResponse(okhttp3.Response response, int id) throws Exception {
            Log.i("miaob", "parseNetworkResponse: " + response);
            String string = response.body().string();
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<CloudRecipeListModel>>() {
            }.getType();
            List<CloudRecipeListModel> user = gson.fromJson(string, listType);
            return user;
        }
    }
}
