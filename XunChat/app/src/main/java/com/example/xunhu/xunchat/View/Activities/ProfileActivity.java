package com.example.xunhu.xunchat.View.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
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
import com.example.xunhu.xunchat.Presenter.SendFriendRequestPresenter;
import com.example.xunhu.xunchat.R;
import com.example.xunhu.xunchat.View.Interfaces.SendFriendRequestView;
import com.example.xunhu.xunchat.View.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xunhu on 6/21/2017.
 */

public class ProfileActivity extends Activity {
    @BindView(R.id.iv_profile_activity_back)
    ImageView btnBack;
    @BindView(R.id.iv_profile_activity_image)
    ImageView ivProfileImage;
    @BindView(R.id.tv_profile_activity_nickname)
    TextView tvNickname;
    @BindView(R.id.iv_profile_activity_gender)
    ImageView ivGender;
    @BindView(R.id.tv_profile_activity_age)
    TextView tvAge;
    @BindView(R.id.tv_profile_activity_username)
    TextView tvUsername;
    @BindView(R.id.tv_profile_activity_what)
    TextView tvWhatsup;
    @BindView(R.id.tv_profile_activity_region)
    TextView tvRegion;
    @BindView(R.id.llAlbum)
    LinearLayout llAlbum;
    @BindView(R.id.btn_send_or_add)
    Button btnSendOrAdd;
    String type = "";
    public  static User user;
    String profile_url="";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity_layout);
        ButterKnife.bind(this);
        retrieveData();
        setViews(type);
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
                   startActivity(intent);
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
    private void retrieveData(){
        type = getIntent().getStringExtra("type");
        user = (User) getIntent().getSerializableExtra("user");
    }
    private void setViews(String type){
        switch (type){
            case "Add":
                btnSendOrAdd.setText("Add");
                profile_url = MainActivity.domain_url + user.getUrl();
                break;
            case "accept":
                btnSendOrAdd.setText("Accept");
                profile_url = user.getUrl();
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
}
