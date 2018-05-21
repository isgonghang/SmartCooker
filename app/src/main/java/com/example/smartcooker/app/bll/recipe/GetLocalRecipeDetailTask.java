package com.example.smartcooker.app.bll.recipe;

import com.example.frame_lib.frame.callbacks.UiCallBack;
import com.example.frame_lib.frame.config.TaskIdConfig;
import com.example.frame_lib.frame.task.BaseTask;
import com.example.smartcooker.app.dal.model.LocalRecipeListModel;
import com.example.smartcooker.app.dal.model.RecipeDetailModel;

import java.util.List;

import smartcookerDao.DaoMaster;
import smartcookerDao.DaoSession;
import smartcookerDao.LocalRecipeListModelDao;
import smartcookerDao.RecipeDetailModelDao;

import static com.example.frame_lib.frame.application.BaseApplication.getContext;

/**
 * Created by ke on 2018/4/18.
 */

public class GetLocalRecipeDetailTask extends BaseTask {
    private UiCallBack mCallBack;

    public GetLocalRecipeDetailTask(UiCallBack mCallBack) {
        this.mCallBack = mCallBack;
    }


    public void execute(long key) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(getContext(), "recipe.db");
        DaoMaster daoMaster = new DaoMaster(helper.getWritableDb());
        DaoSession daoSession = daoMaster.newSession();

        RecipeDetailModelDao detailModelDao = daoSession.getRecipeDetailModelDao();
        RecipeDetailModel recipeDetailModel = detailModelDao.load(key);

        mCallBack.refreshUi(recipeDetailModel, TaskIdConfig.ON_GET_LOCAL_RECIPE_DELATIL_SUCCESS);
    }

    @Override
    public void execute() {

    }
}
