package com.example.smartcooker.app.bll.recipe;

import com.example.frame_lib.frame.callbacks.UiCallBack;
import com.example.frame_lib.frame.config.TaskIdConfig;
import com.example.frame_lib.frame.task.BaseTask;
import com.example.smartcooker.app.dal.model.LocalRecipeListModel;

import java.util.List;

import smartcookerDao.DaoMaster;
import smartcookerDao.DaoSession;
import smartcookerDao.LocalRecipeListModelDao;

import static com.example.frame_lib.frame.application.BaseApplication.getContext;

/**
 * Created by ke on 2018/4/18.
 */

public class GetLocalRecipeListTask extends BaseTask {
    private String circle =
            "http://imgsrc.baidu.com/forum/w=580/sign=06e93e0bbd12c8fcb4f3f6c5cc0392b4/7b8727292df5e0feed47c52a586034a85fdf72d2.jpg";
    private String content2 =
            "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=676483880,1399435458&fm=27&gp=0.jpg";

    private int taskId;
    private UiCallBack mCallBack;

    public GetLocalRecipeListTask(UiCallBack mCallBack) {
        taskId = TaskIdConfig.GET_CLOUD_RECIPE_TASK;
        this.mCallBack = mCallBack;
    }

    @Override
    public void execute() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(getContext(), "recipe.db");
        DaoMaster daoMaster = new DaoMaster(helper.getWritableDb());
        DaoSession daoSession = daoMaster.newSession();
        LocalRecipeListModelDao listDao = daoSession.getLocalRecipeListModelDao();
        List<LocalRecipeListModel> listModels = listDao.loadAll();
        mCallBack.refreshUi(listModels, TaskIdConfig.ON_GET_LOCAL_RECIPE_LIST_SUCCESS);
    }
    // TODO: 2018/5 数据库操作
    //  GetCloudRecipeData.test(userCallback);
//        List<LocalRecipeListModel> list = new ArrayList<>();
//        for (int i = 0; i < 20; i++) {
//            list.add(new LocalRecipeListModel(i, content2,  "煮羊肉", 200,circle,"30分钟"));
//        }
//        mCallBack.refreshUi(list, TaskIdConfig.ON_GET_LOCAL_RECIPE_LIST_SUCCESS);
}

