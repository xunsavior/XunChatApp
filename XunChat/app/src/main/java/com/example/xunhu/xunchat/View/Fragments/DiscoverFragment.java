package com.example.xunhu.xunchat.View.Fragments;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.example.xunhu.xunchat.Model.Entities.User;
import com.example.xunhu.xunchat.Presenter.MySearchFriendPresenter;
import com.example.xunhu.xunchat.R;
import com.example.xunhu.xunchat.View.Activities.CameraViewActivity_;
import com.example.xunhu.xunchat.View.Activities.ProfileActivity;
import com.example.xunhu.xunchat.View.Activities.ProfileActivity_;
import com.example.xunhu.xunchat.View.Activities.SubActivity;
import com.example.xunhu.xunchat.View.Activities.SubActivity_;
import com.example.xunhu.xunchat.View.AllViewClasses.MyDialog;
import com.example.xunhu.xunchat.View.Interfaces.SearchFriendInterface;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.Touch;
import org.androidannotations.annotations.ViewById;
import org.json.JSONException;
import org.json.JSONObject;
import static android.app.Activity.RESULT_OK;

/**
 * Created by xunhu on 6/7/2017.
 */
@EFragment(R.layout.discover_fragment)
public class DiscoverFragment extends Fragment implements SearchFriendInterface {
    @ViewById(R.id.ll_moments) LinearLayout momentLayout;
    @ViewById(R.id.iv_new_post_found) ImageView ivNewPostFound;
    @ViewById(R.id.llScanQRCode) LinearLayout scanQRCodeLayout;
    @ViewById(R.id.llPeopleNearby) LinearLayout peopleNearbyLayout;
    public static final int CAMERA_REQUEST_CODE = 100;
    MySearchFriendPresenter mySearchFriendPresenter;
    MyDialog myDialog;
    @Click({R.id.ll_moments, R.id.iv_new_post_found,R.id.llScanQRCode,R.id.llPeopleNearby})
    public void onDiscoverFragmentClick(View view){
        switch (view.getId()){
            case R.id.ll_moments:
                SubActivity_.intent(getActivity()).extra("type","moments").start();
                break;
            case R.id.llScanQRCode:
               launchScannerCamera();
                break;
            default:
                break;
        }
    }
    public void launchScannerCamera(){
        momentLayout.setClickable(false);
        Intent intent = new Intent(getContext(), CameraViewActivity_.class);
        startActivityForResult(intent,CAMERA_REQUEST_CODE);
    }
    @Override
    public void onResume() {
        super.onResume();
        if (!momentLayout.isClickable()){
            momentLayout.setClickable(true);
        }
    }
    @Touch({R.id.ll_moments})
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
    @Touch({R.id.llScanQRCode})
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
            ProfileActivity_.intent(getContext()).extra("user",user).start();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
