package com.example.smartcooker.app.ui.frags;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aspsine.irecyclerview.IRecyclerView;
import com.aspsine.irecyclerview.OnRefreshListener;
import com.example.frame_lib.frame.config.TaskIdConfig;
import com.example.frame_lib.frame.frags.BaseFragment;
import com.example.smartcooker.R;
import com.example.smartcooker.app.bll.adapter.CloudRecipeListAdapter;
import com.example.smartcooker.app.bll.adapter.OnItemClickListener;
import com.example.smartcooker.app.bll.recipe.GetCloudRecipeListTask;
import com.example.smartcooker.app.dal.model.CloudRecipeListModel;
import com.example.smartcooker.app.ui.actys.CloudRecipeDetailActivity;

import java.util.List;


/**
 * Created by ke on 2018/4/29.
 */

public class LikeFragment extends BaseFragment {

    private IRecyclerView iRecyclerView;
    private CloudRecipeListAdapter mAdapter;
    private GetCloudRecipeListTask getCloudRecipeListTask;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化对象
        mAdapter = new CloudRecipeListAdapter();
        if (getCloudRecipeListTask == null)
            getCloudRecipeListTask = new GetCloudRecipeListTask(this);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_like, container, false);
        init(view);
        return view;
    }

    @Override
    public void refreshUi(Object result, int taskId) {
        switch (taskId) {
            case TaskIdConfig.ON_GET_CLOUD_RECIPE_LIST_SUCCESS:
                mAdapter.setList((List<CloudRecipeListModel>) result);
                iRecyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        iRecyclerView.setRefreshing(false);
                    }
                });

                break;
        }
    }

    private void init(View view) {
        //findView
        iRecyclerView = view.findViewById(R.id.iRecyclerView_like);

        //初始化recycleView
        iRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        iRecyclerView.setIAdapter(mAdapter);

        //设置onRefresh时候的动作
        iRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 2018/5/5 调用task拉取list数据  task.execute()
               //getLocalRecipeTask.execute();
             getCloudRecipeListTask.execute();
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
    }

    @Override
    public void onResume() {
        super.onResume();
      iRecyclerView.setRefreshing(true);
    }
}
