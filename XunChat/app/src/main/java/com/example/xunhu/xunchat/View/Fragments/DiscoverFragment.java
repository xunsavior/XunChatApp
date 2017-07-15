package com.example.xunhu.xunchat.View.Fragments;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.xunhu.xunchat.Manifest;
import com.example.xunhu.xunchat.Model.Entities.User;
import com.example.xunhu.xunchat.Presenter.MySearchFriendPresenter;
import com.example.xunhu.xunchat.R;
import com.example.xunhu.xunchat.View.Activities.CameraViewActivity;
import com.example.xunhu.xunchat.View.Activities.ProfileActivity;
import com.example.xunhu.xunchat.View.Activities.SubActivity;
import com.example.xunhu.xunchat.View.AllViewClasses.MyDialog;
import com.example.xunhu.xunchat.View.Interfaces.SearchFriendInterface;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import butterknife.OnTouch;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;

/**
 * Created by xunhu on 6/7/2017.
 */

public class DiscoverFragment extends Fragment implements SearchFriendInterface {
    @BindView(R.id.ll_moments)
    LinearLayout momentLayout;
    @BindView(R.id.iv_new_post_found)
    ImageView ivNewPostFound;
    @BindView(R.id.llScanQRCode)
    LinearLayout scanQRCodeLayout;
    @BindView(R.id.llPeopleNearby)
    LinearLayout peopleNearbyLayout;
    private Unbinder unbinder;
    public static final int CAMERA_REQUEST_CODE = 100;
    MySearchFriendPresenter mySearchFriendPresenter;
    MyDialog myDialog;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.discover_fragment,container,false);
        unbinder = ButterKnife.bind(this,view);
        return view;
    }
    @OnClick({R.id.ll_moments, R.id.iv_new_post_found,R.id.llScanQRCode,R.id.llPeopleNearby})
    public void onResponse(View view){
        switch (view.getId()){
            case R.id.ll_moments:
                Intent intent = new Intent(getActivity(), SubActivity.class);
                intent.putExtra("type","moments");
                startActivity(intent);
                break;
            case R.id.llScanQRCode:
               launchScannerCamera();
                break;
            default:
                break;
        }
    }
    public void launchScannerCamera(){
        Intent intent = new Intent(getContext(), CameraViewActivity.class);
        startActivityForResult(intent,CAMERA_REQUEST_CODE);
    }
    @OnTouch({R.id.ll_moments})
    public boolean onTouchRespond(MotionEvent event){
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                momentLayout.setBackgroundColor(Color.parseColor("#E1E0DE"));
                break;
            case MotionEvent.ACTION_CANCEL:
                momentLayout.setBackgroundColor(Color.WHITE);
                break;
            case MotionEvent.ACTION_UP:
                momentLayout.setBackgroundColor(Color.WHITE);
                break;
            default:
                break;
        }
        return false;
    }
    @OnTouch({R.id.llScanQRCode})
    public boolean onTouchQRBarRespond(MotionEvent event){
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                scanQRCodeLayout.setBackgroundColor(Color.parseColor("#E1E0DE"));
                break;
            case MotionEvent.ACTION_CANCEL:
                scanQRCodeLayout.setBackgroundColor(Color.WHITE);
                break;
            case MotionEvent.ACTION_UP:
                scanQRCodeLayout.setBackgroundColor(Color.WHITE);
                break;
            default:
                break;
        }
            return false;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==CAMERA_REQUEST_CODE && resultCode==RESULT_OK){
            if (data!=null){
                myDialog = new MyDialog(getActivity());
                myDialog.createLoadingGifDialog();
                mySearchFriendPresenter = new MySearchFriendPresenter(this);
                mySearchFriendPresenter.attemptSearchFriends(data.getStringExtra("qr_code"));
            }
        }
    }

    @Override
    public void searchFriendFail(String msg) {
        myDialog.cancelLoadingGifDialog();
        Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadResults(String msg) {
        myDialog.cancelLoadingGifDialog();
        try {
            JSONObject object = new JSONObject(msg);
            int user_id = object.getInt("user_id");
            String username = object.getString("username");
            String nickname = object.getString("nickname");
            String whatsup = object.getString("what_is_up");
            String url = object.getString("url");
            int age = object.getInt("age");
            String gender = object.getString("gender");
            String region = object.getString("region");
            int relationshipType = object.getInt("relationship_type");
            User user = new User(user_id,username,nickname,url,gender,region,whatsup,age,relationshipType);
            Intent intent = new Intent(getContext(),ProfileActivity.class);
            intent.putExtra("user",user);
            startActivity(intent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
