package com.example.smartcooker.app.ui.actys;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.frame_lib.frame.adapter.FragmentAdapter;
import com.example.frame_lib.frame.config.TaskIdConfig;
import com.example.frame_lib.frame.frags.BaseFragment;
import com.example.mylibrary.NavigationBar;

import com.example.mytitlelyoutlib.TitleLayout;

import com.example.smartcooker.R;
import com.example.smartcooker.app.bll.recipe.GetCloudRecipeListTask;
import com.example.frame_lib.frame.actys.BaseActivity;
import com.example.smartcooker.app.ui.frags.LikeFragment;
import com.example.smartcooker.app.ui.frags.ManageFragment;
import com.example.smartcooker.app.ui.frags.MeFragment;
import com.example.smartcooker.app.ui.frags.RecipeFragment;
import com.example.smartcooker.app.ui.views.ChartHelper;
import com.example.voice_lib.MyVoiceHelper;
import com.example.voice_lib.recognization.IRecogListener;
import com.example.voice_lib.recognization.RecogResult;
import com.skyfishjy.library.RippleBackground;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import lecho.lib.hellocharts.view.LineChartView;

/**
 * Created by ke on 2018/4/17.
 */

public class MainActivity extends BaseActivity {
    private static final int BLE_REQUEST_CODE = 1;
    private NavigationBar navigationBar;
    private List<Fragment> list;
    private FragmentAdapter mFragmentAdapter;
    private ViewPager viewPager;
    private Animation circle_anim;
    private ImageView imageView;
    private View realView;
    private TitleState titleState;
    private LineChartView chartView;
    private ChartHelper chartHelp;
    private TreeMap<Integer, Float> map;
    private TextView stopCook, tt, time, dingshi, yali, wendu, baowen, hour, min, nowyali, nowwendu;
    private SeekBar seekBar;
    private CountDownTimer timer;
    private long time_count;
    private int init_temp;
    private RelativeLayout voice_root;
    private MyVoiceHelper voiceHelper;
    private RippleBackground voice_rp;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContent_layout(R.layout.activity_main);
        chartView = findViewById(R.id.real_chart);
        viewPager = findViewById(R.id.viewPager);
        navigationBar = findViewById(R.id.navigationBar);
        initView();
        initPermission();

        //  initData();
    }

    @Override
    public void initData() {
        GetCloudRecipeListTask getCloudRecipeTask = new GetCloudRecipeListTask(this);
        getCloudRecipeTask.execute();
    }

    @Override
    public void initView() {

        tt = findViewById(R.id.tt);
        voice_rp = findViewById(R.id.voice_rp);
        voice_root = findViewById(R.id.voice_root);
        hour = findViewById(R.id.hour);
        nowwendu = findViewById(R.id.nowtemp);
        nowyali = findViewById(R.id.now_yali);
        min = findViewById(R.id.min);
        stopCook = findViewById(R.id.start_textView);
        seekBar = findViewById(R.id.seek_bar);
        time = findViewById(R.id.shumaguan);
        yali = findViewById(R.id.tiaojieyali);
        dingshi = findViewById(R.id.dingshi);
        wendu = findViewById(R.id.tiaojiewendu);
        realView = findViewById(R.id.real_root);
        imageView = findViewById(R.id.start_image);

        voiceHelper = new MyVoiceHelper(getApplicationContext());
        voiceHelper.setiRecogListener(iRecogListener);
        voice_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                voiceHelper.start();
            }
        });
        time.setTextSize(30);
        //初始化控制按钮字体
        List<TextView> textViews = new ArrayList<>();
        textViews.add(stopCook);
        textViews.add(yali);
        textViews.add(wendu);
        textViews.add(dingshi);
        textViews.add(hour);
        textViews.add(min);
        Typeface face2 = Typeface.createFromAsset(getAssets(),
                "fonts/yy.TTF");
        for (TextView t : textViews
                ) {
            t.setTypeface(face2);
        }
        Typeface face = Typeface.createFromAsset(getAssets(),
                "fonts/digifaw.ttf");
        time.setTypeface(face);
        nowyali.setTypeface(face);
        nowwendu.setTypeface(face);
        yali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seekBar.setMax(4);
                seekBar.setProgress(0);
                hour.setEnabled(false);
                min.setEnabled(false);
            }
        });
        wendu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seekBar.setMax(78);
                seekBar.setProgress(0);
                hour.setEnabled(false);
                min.setEnabled(false);
            }
        });
        dingshi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                time.setText("00:00");
                seekBar.setMax(5);
                seekBar.setProgress(0);
                hour.setEnabled(true);
                min.setEnabled(true);
            }
        });
        hour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seekBar.setMax(5);
                seekBar.setProgress(0);
            }
        });
        min.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seekBar.setMax(59);
                seekBar.setProgress(0);
            }
        });
        time.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String s = time.getText().toString();
                if (s.contains(":") && s.length() < 6) {
                    //将时间转化为秒
                    int h = Integer.parseInt(s.substring(1, 2));
                    int m = Integer.parseInt(s.substring(3, 4));
                    int mm = Integer.parseInt(s.substring(4, 5));
                    time_count = h * 3600 + m * 10 * 60 + mm * 60;
                }
            }
        });
        //初始化seekbar
        seekBar.setMax(5);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (seekBar.getMax() == 5) {
                    refreshUi(i, TaskIdConfig.REFRESH_HOUR);
                } else if (seekBar.getMax() == 59) {
                    String s = null;
                    if (i < 10) {
                        s = "0" + i;
                        refreshUi(s, TaskIdConfig.REFRESH_MIN);
                    } else refreshUi(i, TaskIdConfig.REFRESH_MIN);
                } else if (seekBar.getMax() == 78) {
                    refreshUi((i + 22) + "°C", TaskIdConfig.REFRESH_TEMP);
                } else if (seekBar.getMax() == 4) {
                    switch (i) {
                        case 0:
                            refreshUi("40KP", TaskIdConfig.REFRESH_YALI);
                            break;
                        case 1:
                            refreshUi("60KP", TaskIdConfig.REFRESH_YALI);
                            break;
                        case 2:
                            refreshUi("80KP", TaskIdConfig.REFRESH_YALI);
                            break;
                        case 3:
                            refreshUi("100KP", TaskIdConfig.REFRESH_YALI);
                            break;
                        case 4:
                            refreshUi("120KP", TaskIdConfig.REFRESH_YALI);
                            break;
                    }

                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        //初始化标题栏
        if (titleState == null) {
            titleState = new TitleState();
            titleState.setRight(true).setLeft(true).setTitle("每日推荐");
        }
        //设置展开图表监听器
        tt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chartView.getVisibility() == View.GONE) {
                    //初始化real_chart
                    chartHelp = new ChartHelper(chartView);
                    chartHelp.setMap(map);
                    chartView.setVisibility(View.VISIBLE);
                } else chartView.setVisibility(View.GONE);
            }
        });

        stopCook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("time", "onClick: " + time_count);
                //  start(time_count);
                refreshUi(time_count, TaskIdConfig.START_COOK);
            }
        });
        //初始化中间按钮

        realView.setVisibility(View.GONE);

        circle_anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.round_anim);
        circle_anim.setInterpolator(new LinearInterpolator());
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getTitle_Layout().setTitleText("加热动态")
                        .setBackDismiss(true)
                        .setRightDismiss(true);
                if (realView.getVisibility() == View.GONE) {
                    viewPager.setVisibility(View.GONE);
                    realView.setVisibility(View.VISIBLE);
                    //     Glide.with(imageView.getContext()).load(R.drawable.close_icon).override(72,72).dontAnimate().into(imageView);
                    imageView.setImageResource(R.drawable.close_icon);

                } else {

                    chartHelp = null;
                    Log.i("miao", ": " + titleState.title + titleState.left + titleState.right);

                    getTitle_Layout().setTitleText(titleState.title)
                            .setRightDismiss(titleState.right)
                            .setBackDismiss(titleState.left);
                    viewPager.setVisibility(View.VISIBLE);
                    realView.setVisibility(View.GONE);
                    imageView.setImageResource(R.drawable.icon);
                }

            }
        });


        //初始化底部导航栏
        navigationBar.addItem(R.drawable.icon_like_normal, R.drawable.icon_like_selected, "推荐")
                .addItem(R.drawable.icon_cloud_normal, R.drawable.icon_cloud_selected, "云食谱")
                .addNullItem()
                .addItem(R.drawable.icon_add_normal, R.drawable.icon_add_selected, "管理")
                .addItem(R.drawable.icon_me_normal, R.drawable.icon_me_selected, "我的")
                .setViewPager(viewPager)
                .build();
        //初始化viewpager
        if (list == null || list.size() == 0) {
            list = new ArrayList<>();
            list.add(new LikeFragment());
            list.add(new RecipeFragment());
            list.add(new ManageFragment());
            list.add(new MeFragment());
        }
        initViewPager(list);

        map = new TreeMap<>();
        map.put(0, (float) 15.0);
        map.put(5, (float) 50);
        map.put(20, (float) 50);
        map.put(30, (float) 70);
        map.put(40, (float) 100);
        map.put(50, (float) 100);
    }

    private void initViewPager(List<Fragment> fragments) {
        getTitle_Layout().setTitleText("每日推荐");
        viewPager.setOffscreenPageLimit(fragments.size());
        mFragmentAdapter = new FragmentAdapter(
                super.getSupportFragmentManager(), fragments);
        viewPager.setAdapter(mFragmentAdapter);
        viewPager.setCurrentItem(0);//默认的第一个加载的界面
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                realView.setVisibility(View.GONE);
                viewPager.setVisibility(View.VISIBLE);
                imageView.setImageResource(R.drawable.icon);
                navigationBar.onPagerChanged(position);
                getTitle_Layout().setVisibility(View.VISIBLE);
                switch (position) {
                    case 0:
                        getTitle_Layout().setTitleText("每日推荐")
                                .setBackDismiss(true).setRightDismiss(true);
                        break;
                    case 1:
                        getTitle_Layout().setBackDismiss(true).setTitleText("云食谱")
                                .setRightDismiss(false);
                        break;
                    case 2:
                        getTitle_Layout().setBackDismiss(true).setTitleText("管理").setRightDismiss(true);
                        break;
                    case 3:
                        getTitle_Layout().setBackDismiss(true).setTitleText("我的").
                                setRightDismiss(true);
                        break;
                }
                titleState.setTitle(getTitle_Layout().getTitleString())
                        .setLeft(getTitle_Layout().getLeftState())
                        .setRight(getTitle_Layout().getRightState());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public TitleLayout getTitle_Layout() {
        return super.getTitle_Layout();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        DeviceBindTask deviceBindTask = new DeviceBindTask(this);
//        deviceBindTask.execute();
    }

    @Override
    public void refreshUi(Object result, int taskId) {
        switch (taskId) {
            case TaskIdConfig.REFRESH_TEMP:
                time.setText((String) result);
                nowwendu.setText((String) result);
                break;
            case TaskIdConfig.REFRESH_YALI:
                time.setText((String) result.toString());
                nowyali.setText((String) result);
                break;
            case TaskIdConfig.REFRESH_HOUR:
                String s = time.getText().toString();
                time.setText("0" + result + s.substring(2, 5));
                break;
            case TaskIdConfig.REFRESH_MIN:
                String ss = time.getText().toString();
                time.setText(ss.substring(0, 2) + ":" + result);
                break;
            case TaskIdConfig.GET_CLOUD_RECIPE_TASK:
                Toast.makeText(this, result.toString(), Toast.LENGTH_SHORT).show();
                break;
            case TaskIdConfig.DEVICE_BIND_TASK:
                Toast.makeText(this, result.toString(), Toast.LENGTH_SHORT).show();
                break;
            case TaskIdConfig.CHANGE_TITLE_TASK:
                getTitle_Layout().setTitleText(result.toString());
                break;
            case TaskIdConfig.SET_RIGHT_ONCLICK_LISTENER:
                getTitle_Layout().setRightOnClickListener((View.OnClickListener) result);
                break;
            case TaskIdConfig.SET_LEFT_ONCLICK_LISTENER:
                getTitle_Layout().setBackOnClickListener((View.OnClickListener) result);
                break;
            case TaskIdConfig.START_COOK:
                imageView.startAnimation(circle_anim);
                start((Long) result);
                break;
        }
    }

    private void start(long a) {
        time_count = a;
        if (timer == null) {
            seekBar.setEnabled(false);
            stopCook.setText("停 止");
            hour.setEnabled(false);
            min.setEnabled(false);
            wendu.setEnabled(false);
            yali.setEnabled(false);
            dingshi.setEnabled(false);
            timer = new CountDownTimer(time_count * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    int a = (int) ((millisUntilFinished / 1000) / 3600);
                    int b = (int) ((millisUntilFinished / 1000) % 3600) / 60;
                    int c = (int) ((millisUntilFinished / 1000) % 3600) % 60;
                    if (c < 10 && b > 9) {
                        time.setText(a + ":" + b + ":" + "0" + c);
                    } else if (c > 9 && b < 10) {
                        time.setText(a + ":" + "0" + b + ":" + c);

                    } else if (c < 10 && b < 10) {
                        time.setText(a + ":" + "0" + b + ":" + "0" + c);

                    } else time.setText(a + ":" + b + ":" + c);
                }

                @Override
                public void onFinish() {
                    time.setText("完 成");
                }
            }.start();
        } else {
            imageView.clearAnimation();
            seekBar.setEnabled(true);
            time.setText("00:00");
            stopCook.setText("开 始");
            dingshi.setEnabled(true);
            wendu.setEnabled(true);
            yali.setEnabled(true);
            timer.cancel();
            timer = null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case BLE_REQUEST_CODE:
                BaseFragment fragment = null;
                fragment = (BaseFragment) list.get(2);
                if (resultCode == RESULT_OK)
                    fragment.refreshUi("", TaskIdConfig.DEVICE_BIND_START_TASK);
                break;
        }
    }

    class TitleState {
        String title;
        boolean left;
        boolean right;

        public TitleState() {
        }

        public TitleState setTitle(String t) {
            title = t;
            return this;
        }

        public TitleState setLeft(boolean t) {
            left = t;
            return this;
        }

        public TitleState setRight(boolean t) {
            right = t;
            return this;
        }
    }

    private IRecogListener iRecogListener = new IRecogListener() {
        @Override
        public void onAsrReady() {
            voice_rp.startRippleAnimation();
        }

        @Override
        public void onAsrBegin() {
            Toast.makeText(MainActivity.this, "begin", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onAsrEnd() {

        }

        @Override
        public void onAsrPartialResult(String[] results, RecogResult recogResult) {

        }

        @Override
        public void onAsrFinalResult(String[] results, RecogResult recogResult) {
            voice_rp.stopRippleAnimation();
            if (results[0].equals("开始") || results[0].equals("停止")) {
                refreshUi(time_count, TaskIdConfig.START_COOK);
            }
            voiceHelper.onFinished();
        }

        @Override
        public void onAsrFinish(RecogResult recogResult) {

        }

        @Override
        public void onAsrFinishError(int errorCode, int subErrorCode, String errorMessage, String descMessage, RecogResult recogResult) {

        }

        @Override
        public void onAsrLongFinish() {

        }

        @Override
        public void onAsrVolume(int volumePercent, int volume) {

        }

        @Override
        public void onAsrAudio(byte[] data, int offset, int length) {

        }

        @Override
        public void onAsrExit() {

        }

        @Override
        public void onAsrOnlineNluResult(String nluResult) {

        }

        @Override
        public void onOfflineLoaded() {

        }

        @Override
        public void onOfflineUnLoaded() {

        }
    };

    private void initPermission() {
        String permissions[] = {Manifest.permission.RECORD_AUDIO,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.INTERNET,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        ArrayList<String> toApplyList = new ArrayList<String>();

        for (String perm : permissions) {
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(this, perm)) {
                toApplyList.add(perm);
                //进入到这里代表没有权限.

            }
        }
        String tmpList[] = new String[toApplyList.size()];
        if (!toApplyList.isEmpty()) {
            ActivityCompat.requestPermissions(this, toApplyList.toArray(tmpList), 123);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // 此处为android 6.0以上动态授权的回调，用户自行实现。
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        voiceHelper.onDestory();
    }
}

