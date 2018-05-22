package com.example.smartcooker.app.ui.frags;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.frame_lib.frame.config.HttpConfig;
import com.example.frame_lib.frame.config.TaskIdConfig;
import com.example.frame_lib.frame.frags.BaseFragment;
import com.example.smartcooker.R;
import com.example.smartcooker.app.ui.actys.LoginActivity;
import com.example.smartcooker.app.ui.actys.SelfDefineActivity;


/**
 * Created by ke on 2018/4/26.
 */

public class MeFragment extends BaseFragment {
    private TextView textView_self_define, login;
    private EditText editText;
    public static String url;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_me, container, false);
        initView(view);
        return view;
    }

    @Override
    public void refreshUi(Object result, int taskId) {

    }

    private void initView(View view) {
        editText = view.findViewById(R.id.edit_url);
        textView_self_define = view.findViewById(R.id.text_zidingyi);
        login = view.findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              url= editText.getText().toString();

//                Intent intent = new Intent(getActivity(), LoginActivity.class);
//                startActivity(intent);
            }
        });
        textView_self_define.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SelfDefineActivity.class);
                startActivity(intent);
            }
        });
    }
}
