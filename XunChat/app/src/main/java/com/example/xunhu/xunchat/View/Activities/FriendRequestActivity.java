package com.example.xunhu.xunchat.View.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.xunhu.xunchat.Model.Entities.User;
import com.example.xunhu.xunchat.Presenter.SendFriendRequestPresenter;
import com.example.xunhu.xunchat.R;
import com.example.xunhu.xunchat.View.Interfaces.SendFriendRequestView;
import com.example.xunhu.xunchat.View.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xunhu on 6/30/2017.
 */

public class FriendRequestActivity extends Activity implements SendFriendRequestView {
    @BindView(R.id.iv_friend_request_back) ImageView btnBack;
    @BindView(R.id.btn_friend_request_send) Button btnSend;
    @BindView(R.id.et_extra_information) EditText etExtra;
    @BindView(R.id.et_remarks) EditText etRemark;
    SendFriendRequestPresenter presenter;
    AlertDialog alertDialog;
    User user;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_request_send_layout);
        ButterKnife.bind(this);
        user = (User) getIntent().getSerializableExtra("user");
        etExtra.setText("Hello, my name is "+MainActivity.me.getUsername());
        etRemark.setText(user.getNickname());
        presenter = new SendFriendRequestPresenter(this);
    }

    @OnClick({R.id.iv_friend_request_back,R.id.btn_friend_request_send})
    public void onRespond(View view) {
        switch (view.getId()){
            case R.id.btn_friend_request_send:
                createLogoutDialog();
                presenter.sendFriendRequest(MainActivity.me,etExtra.getText().toString(),
                        user,etRemark.getText().toString());
                break;
            case R.id.iv_friend_request_back:
                onBackPressed();
                break;
            default:
                break;
        }
    }
    public void createLogoutDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(FriendRequestActivity.this);
        View myView = getLayoutInflater().inflate(R.layout.gif_dialog,null);
        pl.droidsonroids.gif.GifImageView webView = (pl.droidsonroids.gif.GifImageView) myView.findViewById(R.id.iv_gif);
        webView.setBackgroundColor(Color.TRANSPARENT);
        webView.setBackgroundResource(R.drawable.loading);
        builder.setView(myView);
        alertDialog = builder.create();
        alertDialog.getWindow().setDimAmount(0);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.show();
    }
    @Override
    public void friendRequestSent(String msg) {
        alertDialog.cancel();
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(ProfileActivity.BUTTON_PENDING);
        sendBroadcast(intent);
        finish();
    }

    @Override
    public void friendRequestFailtoSend(String msg) {
        alertDialog.cancel();
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
    }
}
