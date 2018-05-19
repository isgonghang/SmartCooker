package com.example.smartcooker.app.ui.actys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.frame_lib.frame.actys.BaseActivity;
import com.example.smartcooker.R;


public class LoginActivity extends BaseActivity {
    private static final String TAG = "LoginActivity";
    private SharedPreferences sp;
    private CheckBox cb_auto_go;
    private ToggleButton tb_see_password;
    private EditText et_login_name;
    private EditText et_login_password;
    private Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getTitle_Layout().setBackDismiss(false)
                .setBackSrc(R.drawable.back_white)
                .setBackOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });


        cb_auto_go = findViewById(R.id.cb_auto_go);
        tb_see_password = (ToggleButton) findViewById(R.id.tb_see_password);

        et_login_name = (EditText) findViewById(R.id.et_login_name);
        et_login_password = (EditText) findViewById(R.id.et_password);
        btn_login = findViewById(R.id.btn_login);

        et_login_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String psw = et_login_password.getText().toString();
                if (psw.isEmpty()) {
                    tb_see_password.setVisibility(View.INVISIBLE);
                } else {
//                    tb_see_password.setVisibility(View.VISIBLE);
//                    if (ResetPasswordActivity.isValidPassword(psw)) {
//                        btn_login.setEnabled(true);
//                    } else {
//                        btn_login.setEnabled(false);
//                    }
                }
            }
        });

        sp = PreferenceManager.getDefaultSharedPreferences(this);

        //判断记住密码多选框的状态
        if (sp.getBoolean("ISCHECK", false)) {
            cb_auto_go.setChecked(true);
            String name = sp.getString("USERNAME", null);
            String password = sp.getString("PASSWORD", null);
            et_login_name.setText(name);
            et_login_password.setText(password);
            login(name, password);

//            initJpush(name);
//            //跳转界面
//            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            startActivity(intent);
//            finish();
        }

        cb_auto_go.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = sp.edit();
                if (isChecked) {
                    editor.putBoolean("ISCHECK", true);
                    editor.putString("USERNAME", et_login_name.getText().toString());
                    editor.putString("PASSWORD", et_login_password.getText().toString());
                } else {
                    editor.putBoolean("ISCHECK", false);
                }
                editor.apply();
            }
        });

        tb_see_password.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
//                    cb_see_password.setBackgroundResource(R.drawable.chakanmima_weijihuo);
                    et_login_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
//                    cb_see_password.setBackgroundResource(R.drawable.chakanmima_jihuo);
                    et_login_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }

        });

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }

//    public void onClick(View v) {
//        int id = v.getId();
//        Intent intent;
//        switch (id) {
//            case R.id.btn_forget_password:
////                Toast.makeText(this, "正在建设中！", Toast.LENGTH_SHORT).show();
//              //  intent = new Intent(this, ForgetPasswordActivity.class);
//               // startActivity(intent);
//                break;
//
//            case R.id.btn_login:
//                String name = et_login_name.getText().toString();
//                String password = et_login_password.getText().toString();
//
//                if (TextUtils.isEmpty(name.trim())) {
//                    Toast.makeText(this, "用户名不能为空", Toast.LENGTH_LONG).show();
//                } else if (TextUtils.isEmpty(password.trim())) {
//                    Toast.makeText(this, "密码不能为空", Toast.LENGTH_LONG).show();
//                } else if (!ResetPasswordActivity.isValidPassword(password)) {
//                    Toast.makeText(this, "密码长度不正确", Toast.LENGTH_LONG).show();
//                } else {
////                    if (cb_auto_go.isChecked()) {
////                        SharedPreferences.Editor editor = sp.edit();
////                        editor.putString("USERNAME", name);
////                        editor.putString("PASSWORD", password);
////                        editor.apply();
////                    }
//                    login(name, password);
//                }
//
//
//                break;
//
//            case R.id.tv_register:
//                intent = new Intent(this, RegisterActivity.class);
////                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
//                break;
//        }
//
//    }

    public void login(final String name, final String password) {
        String username = sp.getString("USERNAME", null);
        String psw = sp.getString("PASSWORD", null);
        SharedPreferences.Editor editor = sp.edit();
        if (name.equals(username) && password.equals(psw)) {
            if (cb_auto_go.isChecked()) {
                editor.putString("USERNAME", name);
                editor.putString("PASSWORD", password);
            }
            editor.putBoolean("signed_in", true);
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            this.finish();
        } else {
            editor.putBoolean("signed_in", false);
            Toast.makeText(this, "用户名或密码错误！请重新输入！", Toast.LENGTH_SHORT).show();
        }
        // 测试管理页面登录跳转
//        editor.putBoolean("signed_in", false);

        editor.apply();

    }

    @Override
    public void refreshUi(Object result, int taskId) {

    }


//    @Override
//    protected void onDestroy() {
//        setContentView(R.layout.layout_null);
//        super.onDestroy();
//        System.gc();
//    }

}
