package com.example.smartcooker.app.ui.frags;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.aspsine.irecyclerview.IRecyclerView;
import com.aspsine.irecyclerview.OnRefreshListener;
import com.example.frame_lib.frame.actys.BaseActivity;
import com.example.frame_lib.frame.config.TaskIdConfig;
import com.example.frame_lib.frame.frags.BaseFragment;
import com.example.sidetablayout_lib.OnChangeListener;
import com.example.sidetablayout_lib.SideTabLayout;
import com.example.smartcooker.R;
import com.example.smartcooker.app.bll.adapter.CloudRecipeListAdapter;
import com.example.smartcooker.app.bll.adapter.LocalRecipeListAdapter;
import com.example.smartcooker.app.bll.adapter.OnItemClickListener;
import com.example.smartcooker.app.bll.recipe.GetCloudRecipeListTask;
import com.example.smartcooker.app.bll.recipe.GetLocalRecipeListTask;
import com.example.smartcooker.app.dal.model.CloudRecipeListModel;
import com.example.smartcooker.app.dal.model.Image;
import com.example.smartcooker.app.dal.model.LocalRecipeListModel;
import com.example.smartcooker.app.ui.actys.CloudRecipeDetailActivity;
import com.example.smartcooker.app.ui.actys.SearchActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by ke on 2018/4/29.
 */

public class RecipeFragment extends BaseFragment {

    private SideTabLayout sideTabLayout;
    private IRecyclerView iRecyclerView;
    private int currentRecycleIndex;
    private View.OnClickListener onSearchClickListener, onLeftClickListener;
    private CloudRecipeListAdapter mAdapter;
    private LocalRecipeListAdapter adapter;
    private GetCloudRecipeListTask getCloudRecipeListTask;
    private GetLocalRecipeListTask getLocalRecipeListTask;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化对象
        mAdapter = new CloudRecipeListAdapter();
        adapter = new LocalRecipeListAdapter();
        if (getCloudRecipeListTask == null)
            getCloudRecipeListTask = new GetCloudRecipeListTask(this);
        if (getLocalRecipeListTask == null)
            getLocalRecipeListTask = new GetLocalRecipeListTask(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_recipe, container, false);
        init(view);
        return view;
    }

    @Override
    public void refreshUi(Object result, int taskId) {
        switch (taskId) {
            case TaskIdConfig.ON_GET_CLOUD_RECIPE_LIST_SUCCESS:
                mAdapter.setList((List<CloudRecipeListModel>) result);
                stopRefresh();
                break;
            case TaskIdConfig.ON_GET_LOCAL_RECIPE_LIST_SUCCESS:
                if (result != null) {
                    adapter.setList((List<LocalRecipeListModel>) result);
                    stopRefresh();
                }
                break;
        }
    }

    private void init(View view) {
        //findView
        iRecyclerView = view.findViewById(R.id.iRecyclerView);
        sideTabLayout = view.findViewById(R.id.mSideTab);
        //初始化侧边栏状态
        sideTabLayout.setCurrentItem(0);
        //设置标题栏左右按钮监听器
        onSearchClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 2018/4/30 跳转到搜索界面
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        };
        onLeftClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 2018/4/30 跳转到搜索界面
                Toast.makeText(getContext(), "dianji ", Toast.LENGTH_SHORT).show();
            }
        };
        //绑定监听器
        RecipeFragment.super.uiCallBack.refreshUi(onSearchClickListener, TaskIdConfig.SET_RIGHT_ONCLICK_LISTENER);
        RecipeFragment.super.uiCallBack.refreshUi(onLeftClickListener, TaskIdConfig.SET_LEFT_ONCLICK_LISTENER);
        //初始化recycleView
        iRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        iRecyclerView.setIAdapter(mAdapter);
        iRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //监听recycleView滑动状态，显示/隐藏侧边选项卡
                sideTabLayout.onTabSlide(dx, dy, RecipeFragment.this);
            }
        });
        //设置onRefresh时候的动作
        iRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (currentRecycleIndex == 0) {
                    // 2018/5/5 调用task拉取list数据  task.execute()
                    getCloudRecipeListTask.execute();
                } else {
                    getLocalRecipeListTask.execute();
                }
            }
        });

        //设置侧边栏的监听事件
        sideTabLayout.setChangeListener(new OnChangeListener() {
            @Override
            public void onSelected(int position, SideTabLayout tabLayout) {
                tabLayout = sideTabLayout;
                tabLayout.setCurrentItem(position);
                switch (position) {
                    case 0:
                        RecipeFragment.super.uiCallBack.refreshUi("云食谱", TaskIdConfig.CHANGE_TITLE_TASK);
                        currentRecycleIndex = 0;
                        iRecyclerView.setIAdapter(mAdapter);
                        break;
                    case 2:
                        RecipeFragment.super.uiCallBack.refreshUi("本地食谱", TaskIdConfig.CHANGE_TITLE_TASK);
                        currentRecycleIndex = 1;
                        iRecyclerView.setIAdapter(adapter);
                        break;
                }
                iRecyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        iRecyclerView.setRefreshing(true);
                    }
                });
            }
        });
        //设置item点击事件
        mAdapter.setOnItemClickListener(new OnItemClickListener<CloudRecipeListModel>() {
            @Override
            public void onItemClick(long id) {
                Intent intent = new Intent(getActivity(), CloudRecipeDetailActivity.class);
                startActivity(intent);
            }
        });
        adapter.setOnItemClickListener(new OnItemClickListener<LocalRecipeListModel>() {
            @Override
            public void onItemClick(long id) {
                Intent intent = new Intent(getActivity(), CloudRecipeDetailActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        iRecyclerView.setRefreshing(true);
    }

    private void stopRefresh() {
        iRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                iRecyclerView.setRefreshing(false);
            }
        });
    }
}
