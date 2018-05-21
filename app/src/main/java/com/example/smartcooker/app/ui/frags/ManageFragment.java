package com.example.smartcooker.app.ui.frags;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.frame_lib.frame.config.TaskIdConfig;
import com.example.frame_lib.frame.frags.BaseFragment;
import com.example.frame_lib.frame.utils.PopDialogHelper;
import com.example.smartcooker.R;
import com.example.smartcooker.app.bll.device.DeviceBindTask;
import com.example.smartcooker.app.bll.recipe.GetLocalRecipeDetailTask;
import com.example.smartcooker.app.bll.recipe.GetLocalRecipeListTask;
import com.example.smartcooker.app.dal.model.DeviceModel;
import com.example.smartcooker.app.dal.model.LocalRecipeListModel;
import com.example.smartcooker.app.dal.model.LocalRecipeModel;
import com.example.smartcooker.app.dal.model.RecipeDetailModel;
import com.example.smartcooker.app.ui.actys.SelfDefineActivity;
import com.skyfishjy.library.RippleBackground;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ke on 2018/4/26.
 */

public class ManageFragment extends BaseFragment {
    private DeviceBindTask deviceBindTask;
    private ImageView imageView_add;
    private String[] choose_way_list = new String[]{"选择方式", "蓝牙", "WIFI"};
    private String[] start_list = new String[]{"选择加热程序", "选择本地程序", "自定义程序"};
    private String[] file_list = new String[]{"本地程序", "a", "b", "c"};
    private RippleBackground rippleBackground;
    private TextView textView_start, textView_choose, textView_deviceName, textView__deviceStatus,
            getTextView_deviceMac;
    private View view;
    private RelativeLayout info_root;
    private PopDialogHelper popDialogHelper;
    private boolean isConnected = false;
    private ImageView imageView_guo;
    private GetLocalRecipeListTask task;
    private LocalRecipeListModel localRecipeListModel;
    private List<String> localRecipeList;
    private GetLocalRecipeDetailTask getLocalRecipeDetailTask;
    private RecipeDetailModel recipeDetailModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_manage, container, false);
        initView();
        setViewListener();
        if (task == null) task = new GetLocalRecipeListTask(this);
        if (getLocalRecipeDetailTask == null)
            getLocalRecipeDetailTask = new GetLocalRecipeDetailTask(this);
        return view;
    }

    @Override
    public void refreshUi(Object result, int taskId) {
        switch (taskId) {
            case TaskIdConfig.ON_GET_LOCAL_RECIPE_DELATIL_SUCCESS:
                recipeDetailModel = (RecipeDetailModel) result;
                break;
            case TaskIdConfig.ON_GET_LOCAL_RECIPE_LIST_SUCCESS:
                final List<LocalRecipeListModel> listModels = (List<LocalRecipeListModel>) result;
                localRecipeList = new ArrayList<>();
                localRecipeList.add("本地程序");
                for (LocalRecipeListModel item : listModels
                        ) {
                    localRecipeList.add(item.getName() + "     " + item.getTime());
                }
                popDialogHelper.buildDialogInCenter()
                        .setListData(localRecipeList);
                popDialogHelper.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        localRecipeListModel = listModels.get(i - 1);
                        getLocalRecipeDetailTask.execute(localRecipeListModel.getId());
                        Toast.makeText(getContext(), "选择程序      " + localRecipeList.get(i), Toast.LENGTH_LONG).show();
                        // TODO: 2018/5/21  传送加数据到设备 task.execute()
                        popDialogHelper.dismiss();
                    }
                });
                break;
            case TaskIdConfig.DEVICE_CONNECT_SUCCESS_TASK:
                isConnected = true;
                Glide.with(getContext()).load(R.drawable.guoc).dontAnimate().into(imageView_guo);
                info_root.setVisibility(View.VISIBLE);
                DeviceModel deviceModel = (DeviceModel) result;
                rippleBackground.stopRippleAnimation();
                imageView_add.setVisibility(View.GONE);
                textView_deviceName.setText("设备名称：" + deviceModel.getDeviceName());
                textView__deviceStatus.setText("状态：" + deviceModel.getDeviceState());
                getTextView_deviceMac.setText("mac地址：" + deviceModel.getDeviceMac());
                break;
            case TaskIdConfig.DEVICE_BIND_START_TASK:
                rippleBackground.startRippleAnimation();
                deviceBindTask.connect();
                imageView_add.setImageResource(R.drawable.icon_add_circle);
                imageView_add.setClickable(false);
                break;
            case TaskIdConfig.DEVICE_CONNECT_FAILUER_TASK:
                rippleBackground.stopRippleAnimation();
                imageView_add.setImageResource(R.drawable.icon_add_big);
                imageView_add.setClickable(true);
                Toast.makeText(getActivity(), result.toString(), Toast.LENGTH_SHORT).show();
                break;
            case TaskIdConfig.DEVICE_SCAN_SUCCESS_TASK:
                Toast.makeText(getActivity(), "扫描到设备，连接中", Toast.LENGTH_SHORT).show();
                break;
            case TaskIdConfig.DEVICE_SCAN_FAILURE_TASK:
                rippleBackground.stopRippleAnimation();
                imageView_add.setImageResource(R.drawable.icon_add_big);
                imageView_add.setClickable(true);
                Toast.makeText(getActivity(), "扫描失败", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void initView() {
        info_root = view.findViewById(R.id.device_info_root);
        imageView_add = view.findViewById(R.id.image_add);
        imageView_guo = view.findViewById(R.id.image_guo);
        rippleBackground = view.findViewById(R.id.rp);
        textView_start = view.findViewById(R.id.start);
        textView_choose = view.findViewById(R.id.choose);
        popDialogHelper = new PopDialogHelper(getActivity());
        textView_deviceName = view.findViewById(R.id.text_device_name);
        textView__deviceStatus = view.findViewById(R.id.text_device_state);
        getTextView_deviceMac = view.findViewById(R.id.text_device_mac);
        if (isConnected) {
            info_root.setVisibility(View.VISIBLE);
            imageView_guo.setVisibility(View.GONE);
        } else {
            info_root.setVisibility(View.GONE);
            imageView_guo.setVisibility(View.VISIBLE);
        }
    }

    private void setViewListener() {
        if (deviceBindTask == null)
            deviceBindTask = new DeviceBindTask(this);
        imageView_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popDialogHelper.buildDialogInCenter()
                        .setListData(Arrays.asList(choose_way_list));
                popDialogHelper.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        popDialogHelper.dismiss();
                        switch (i) {
                            case 1:
                                if (deviceBindTask != null)
                                    deviceBindTask.execute();
                                break;
                            case 2:
                                refreshUi(new DeviceModel(), TaskIdConfig.DEVICE_CONNECT_SUCCESS_TASK);
                                break;
                        }
                    }
                });
            }
        });
        textView_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long second = (int) recipeDetailModel.getTime() * 60;
                ManageFragment.super.uiCallBack.refreshUi(second, TaskIdConfig.START_COOK);
            }
        });
        textView_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                popDialogHelper.buildDialogInCenter()
                        .setListData(Arrays.asList(start_list));
                popDialogHelper.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        popDialogHelper.dismiss();
                        switch (i) {
                            case 1:
                                task.execute();
                                break;
                            case 2:
                                Intent intent = new Intent(getActivity(), SelfDefineActivity.class);
                                startActivity(intent);
                                break;
                        }
                    }
                });
            }
        });
    }

}
