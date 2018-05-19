package com.example.smartcooker.app.ui.actys;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.frame_lib.frame.callbacks.UiCallBack;
import com.example.smartcooker.R;
import com.example.smartcooker.app.bll.recipe.GetCloudRecipeDetailTask;
import com.example.smartcooker.app.dal.model.LocalRecipeListModel;
import com.example.smartcooker.app.dal.model.RecipeDetailModel;
import com.example.smartcooker.app.ui.views.ChartHelper;

import lecho.lib.hellocharts.view.LineChartView;
import smartcookerDao.DaoMaster;
import smartcookerDao.DaoSession;
import smartcookerDao.LocalRecipeListModelDao;
import smartcookerDao.RecipeDetailModelDao;

/**
 * Created by ke on 2018/5/3.
 */

public class CloudRecipeDetailActivity extends Activity implements UiCallBack {
    private ImageView imageView;
    private LineChartView lineChart;
    private TextView textView_score;
    private TextView textView_time;
    private TextView textView_shicai;
    private TextView textView_peiliao;
    private TextView textView_down;
    private ChartHelper chartHelper;
    private GetCloudRecipeDetailTask detailTask;
    private RecipeDetailModel recipeDetailModel;

    @Override
    public void refreshUi(Object result, @Nullable int taskId) {
        if (result != null) {
            recipeDetailModel = (RecipeDetailModel) result;
            textView_peiliao.setText(recipeDetailModel.getOther());
            textView_shicai.setText(recipeDetailModel.getSource());
            textView_time.setText(recipeDetailModel.getTime() + "分钟");
            textView_score.setText("评分：" + recipeDetailModel.getScore());
            chartHelper.setMap(recipeDetailModel.getMap());
            Glide.with(imageView.getContext()).load(recipeDetailModel.getImage()).crossFade().into(imageView);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        initView();
    }

    private void initView() {
        //设置全屏、透明状态栏
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            decorView.setSystemUiVisibility(option);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        //find view
        imageView = findViewById(R.id.image_content);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        lineChart = findViewById(R.id.chart);
        textView_score = findViewById(R.id.textView_score);
        textView_time = findViewById(R.id.textView_time);
        textView_shicai = findViewById(R.id.shicai);
        textView_peiliao = findViewById(R.id.peiliao);
        textView_down = findViewById(R.id.text_down);
        //初始化real_chart
        chartHelper = new ChartHelper(lineChart);
        //初始化task
        detailTask = new GetCloudRecipeDetailTask(this);
        detailTask.execute();
        //设置下载按钮监听器
        textView_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(getApplicationContext(), "recipe.db");

                DaoMaster daoMaster = new DaoMaster(helper.getWritableDb());

                DaoSession daoSession = daoMaster.newSession();

                RecipeDetailModelDao recipeDetailModelDao = daoSession.getRecipeDetailModelDao();
                LocalRecipeListModelDao localRecipeListModelDao = daoSession.getLocalRecipeListModelDao();
                long insertID = recipeDetailModelDao.insertOrReplace(recipeDetailModel);
                Log.i("db", "insertid" + insertID);
                long insertID2 = localRecipeListModelDao.insertOrReplace(new LocalRecipeListModel(recipeDetailModel));
                Log.i("db", "insertid" + insertID2);
                if (insertID >= 0) {
                    Toast.makeText(getApplicationContext(), "保存成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "保存失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

