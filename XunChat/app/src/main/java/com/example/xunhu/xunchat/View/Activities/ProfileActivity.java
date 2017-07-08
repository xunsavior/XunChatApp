package com.example.xunhu.xunchat.View.Activities;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.example.xunhu.xunchat.Model.AsyTasks.MySingleton;
import com.example.xunhu.xunchat.Model.Entities.User;
import com.example.xunhu.xunchat.Presenter.RequestRespondPresenter;
import com.example.xunhu.xunchat.R;
import com.example.xunhu.xunchat.View.AllViewClasses.MyDialog;
import com.example.xunhu.xunchat.View.Interfaces.RequestRespondView;
import com.example.xunhu.xunchat.View.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xunhu on 6/21/2017.
 */

public class ProfileActivity extends Activity implements RequestRespondView {
    @BindView(R.id.iv_profile_activity_back) ImageView btnBack;
    @BindView(R.id.iv_profile_activity_image) ImageView ivProfileImage;
    @BindView(R.id.tv_profile_activity_nickname) TextView tvNickname;
    @BindView(R.id.iv_profile_activity_gender) ImageView ivGender;
    @BindView(R.id.tv_profile_activity_age) TextView tvAge;
    @BindView(R.id.tv_profile_activity_username) TextView tvUsername;
    @BindView(R.id.tv_profile_activity_what) TextView tvWhatsup;
    @BindView(R.id.tv_profile_activity_region) TextView tvRegion;
    @BindView(R.id.llAlbum) LinearLayout llAlbum;
    @BindView(R.id.btn_send_or_add) Button btnSendOrAdd;
    User user;
    String profile_url="";
    RequestRespondPresenter presenter;
    private static final int STRANGER = -1;
    private static final int PENDING = 0;
    private static final int NEED_TO_ACCEPT = 1;
    private static final int FRIEND = 2;
    MyDialog myDialog;
    IntentFilter intentFilter = new IntentFilter();
    public static final String BUTTON_PENDING = "pending";
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()){
                case BUTTON_PENDING:
                    btnSendOrAdd.setText("Pending...");
                    btnSendOrAdd.setClickable(false);
                    btnSendOrAdd.setBackgroundColor(Color.RED);
                    break;
                default:
                    break;
            }
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity_layout);
        intentFilter.addAction("pending");
        registerReceiver(broadcastReceiver,intentFilter);
        myDialog = new MyDialog(this);
        ButterKnife.bind(this);
        user = (User) getIntent().getSerializableExtra("user");
        setViews(user.getRelationship_type());
    }
    private void setViews(int relationshipType){
        switch (relationshipType){
            case STRANGER:
                btnSendOrAdd.setText("Add");
                profile_url = MainActivity.domain_url + user.getUrl();
                btnSendOrAdd.setClickable(true);
                btnSendOrAdd.setBackgroundColor(Color.parseColor("#00BFFF"));
                break;
            case PENDING:
                btnSendOrAdd.setText("Pending...");
                btnSendOrAdd.setClickable(false);
                btnSendOrAdd.setBackgroundColor(Color.RED);
                profile_url = MainActivity.domain_url+user.getUrl();
                break;
            case NEED_TO_ACCEPT:
                btnSendOrAdd.setText("Accept");
                profile_url = MainActivity.domain_url+user.getUrl();
                btnSendOrAdd.setClickable(true);
                btnSendOrAdd.setBackgroundColor(Color.parseColor("#00BFFF"));
                break;
            case FRIEND:
                btnSendOrAdd.setText("Message");
                profile_url = MainActivity.domain_url+user.getUrl();
                btnSendOrAdd.setClickable(true);
                btnSendOrAdd.setBackgroundColor(Color.parseColor("#00BFFF"));
                break;
            default:
                break;
        }
        tvUsername.setText("username: "+user.getUsername());
        tvNickname.setText(user.getNickname());
        tvAge.setText(String.valueOf(user.getAge()));
        tvRegion.setText(user.getRegion());
        tvWhatsup.setText(user.getWhatsup());
        if (user.getGender().equals("female")){
            ivGender.setImageResource(R.drawable.female_icon);
        }else {
            ivGender.setImageResource(R.drawable.male_icon);
        }
        ImageRequest imageRequest = new ImageRequest(profile_url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                ivProfileImage.setImageBitmap(response);
            }
        }, 0, 0, ImageView.ScaleType.CENTER_CROP, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Loading image fail",Toast.LENGTH_SHORT).show();
            }
        });
        MySingleton.getmInstance(getApplicationContext()).addImageRequestToRequestQueue(imageRequest);
    }
    @OnClick({R.id.btn_send_or_add,R.id.iv_profile_activity_back,R.id.iv_profile_activity_image})
    public void onRespond(View view){
        switch (view.getId()){
            case R.id.iv_profile_activity_back:
                onBackPressed();
                break;
            case R.id.btn_send_or_add:
                if(btnSendOrAdd.getText().toString().equals("Add")){
                   Intent intent = new Intent(this,FriendRequestActivity.class);
                   intent.putExtra("user",user);
                   startActivity(intent);
                }else if (btnSendOrAdd.getText().toString().equals("Accept")){
                    myDialog.createBottomGifDialog();
                    presenter = new RequestRespondPresenter(this);
                    presenter.sendRespond(user.getUserID(),MainActivity.me);
                }
                break;
            case R.id.iv_profile_activity_image:
                Intent intent = new Intent(ProfileActivity.this,ProfileThemeActivity.class);
                intent.putExtra("url",MainActivity.domain_url+user.getUrl());
                startActivity(intent);
                break;
            default:
                break;
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }
    @Override
    public void respondSuccess(String msg) {
        myDialog.cancelBottomGifDialog();
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
    }
    @Override
    public void respondFail(String msg) {
        myDialog.cancelBottomGifDialog();
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
    }
}
