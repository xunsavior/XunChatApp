package com.example.xunhu.xunchat.View.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.xunhu.xunchat.Model.Entities.User;
import com.example.xunhu.xunchat.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xunhu on 7/18/2017.
 */

public class ChatBoardActivity extends Activity {
    @BindView(R.id.iv_chat_activity_back) ImageView ivBack;
    @BindView(R.id.tv_remark) TextView tvRemark;
    @BindView(R.id.lv_message) ListView lvMessage;
    @BindView(R.id.ib_add) ImageButton ibAdd;
    @BindView(R.id.et_message) EditText etMessage;
    @BindView(R.id.ib_emoji) ImageButton ibEmoji;
    @BindView(R.id.ib_voice) ImageButton ibVoice;
    @BindView(R.id.ib_sending) ImageButton ibSending;
    private User user;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity_layout);
        ButterKnife.bind(this);
        user = (User) getIntent().getSerializableExtra("user");
        tvRemark.setText(user.getRemark());
    }
    @OnClick({R.id.iv_chat_activity_back,R.id.ib_sending})
    public void onClickView(View view){
        switch (view.getId()){
            case R.id.iv_chat_activity_back:
                onBackPressed();
                break;
            case R.id.ib_sending:

                break;
            default:
                break;
        }
    }
}
