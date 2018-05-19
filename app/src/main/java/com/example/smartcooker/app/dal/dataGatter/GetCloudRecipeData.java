package com.example.smartcooker.app.dal.dataGatter;

import com.example.frame_lib.frame.config.HttpConfig;
import com.example.smartcooker.app.bll.recipe.GetCloudRecipeListTask;
import com.zhy.http.okhttp.OkHttpUtils;

/**
 * Created by ke on 2018/4/18.
 */

public class GetCloudRecipeData {

    public static void get(GetCloudRecipeListTask.RecipeListCallback recipeListCallback) {

        String url = HttpConfig.GET_CLOUD_RECIPE_LIST_URL;
        OkHttpUtils.get()
                .url(url)
                .build()
                .execute(recipeListCallback);
    }
}


