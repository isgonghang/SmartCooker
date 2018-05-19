package com.example.smartcooker.app.bll.recipe;

import android.util.Log;

import com.example.frame_lib.frame.callbacks.UiCallBack;
import com.example.frame_lib.frame.config.TaskIdConfig;
import com.example.frame_lib.frame.task.BaseTask;
import com.example.smartcooker.app.dal.dataGatter.GetCloudRecipeData;
import com.example.smartcooker.app.dal.model.CloudRecipeListModel;
import com.example.smartcooker.app.dal.model.RecipeDetailModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.callback.Callback;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import okhttp3.Call;

/**
 * Created by ke on 2018/4/18.
 */

public class GetCloudRecipeDetailTask extends BaseTask {
    private String content =
            "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1782158040,768410558&fm=27&gp=0.jpg";
    private String circle =
            "http://imgsrc.baidu.com/forum/w=580/sign=06e93e0bbd12c8fcb4f3f6c5cc0392b4/7b8727292df5e0feed47c52a586034a85fdf72d2.jpg";
    private String content2 =
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1525849362978&di=60a8407011e3b2e7e4d5e42ca711ad0c&imgtype=0&src=http%3A%2F%2Fimg4.duitang.com%2Fuploads%2Fitem%2F201508%2F31%2F20150831162526_AYiLQ.jpeg";

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

    public GetCloudRecipeDetailTask(UiCallBack mCallBack) {
        taskId = TaskIdConfig.GET_CLOUD_RECIPE_TASK;
        this.mCallBack = mCallBack;
    }

    @Override
    public void execute() {
        // GetCloudRecipeData.get(recipeListCallback);
        TreeMap<Integer, Float> map = new TreeMap<>();
        map.put( 0, (float) 15.0);
        map.put( 5, (float) 50);
        map.put( 20, (float) 50);
        map.put( 30, (float) 70);
        map.put( 35, (float) 70);
        map.put( 40, (float) 100);
        map.put( 50, (float) 100);
        RecipeDetailModel mm = new RecipeDetailModel((long)9, content2, (float) 8.5, (float) 30, "红烧排骨", "食材：...... ", map);

        Gson gson = new Gson();
        String jsonStr = gson.toJson(mm);
        Log.i("miaoa", "生成的json为：" + jsonStr);

        RecipeDetailModel model = gson.fromJson(jsonStr, RecipeDetailModel.class);
        Log.i("miaoa", "解析后的结果为: " + model.toString());
        mCallBack.refreshUi(model, 0);
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
