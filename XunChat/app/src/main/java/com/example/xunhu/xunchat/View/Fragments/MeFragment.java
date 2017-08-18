package com.example.xunhu.xunchat.View.Fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.example.xunhu.xunchat.Model.AsyTasks.MySingleton;
import com.example.xunhu.xunchat.Model.Entities.Me;
import com.example.xunhu.xunchat.R;
import com.example.xunhu.xunchat.View.Activities.QRCodeActivity;
import com.example.xunhu.xunchat.View.Activities.SubActivity_;
import com.example.xunhu.xunchat.View.AllViewClasses.EditProfileLayout;
import com.example.xunhu.xunchat.View.MainActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.ViewById;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

/**
 * Created by xunhu on 6/7/2017.
 */
@EFragment(R.layout.me_fragment_layout)
public class MeFragment extends Fragment  {
    @ViewById(R.id.iv_my_profile) CircleImageView ivMyProfile;
    @ViewById(R.id.my_username) TextView tvMyUsername;
    @ViewById(R.id.iv_my_gender) ImageView ivMyGender;
    @ViewById(R.id.profile_layout) LinearLayout linearLayoutProfile;
    @ViewById(R.id.MeLayout) FrameLayout meFrameLayout;
    @ViewById(R.id.tv_my_age) TextView tvMyAge;
    @ViewById LinearLayout llMyPosts;
    String url ="";
    MeFragmentInterface conn;
    private Me me;
    private EditProfileLayout editProfileLayout=null;
    private static final int SELECT_IMAGE = 0;
    Uri uri;
    private static final int ACCESS_EXTERNAL = 1;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        conn = (MeFragmentInterface) activity;
    }
    @AfterViews void setMeFragmentViews(){
        llMyPosts.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        llMyPosts.setBackgroundColor(getResources().getColor(R.color.SkyBlue));
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        llMyPosts.setBackgroundColor(getResources().getColor(R.color.white));
                        break;
                    case MotionEvent.ACTION_UP:
                        llMyPosts.setBackgroundColor(getResources().getColor(R.color.white));
                        break;
                }
                return false;
            }
        });
    }
    @Click({R.id.iv_my_profile,R.id.profile_layout,R.id.llMyPosts})
    public void onRespond(View view){
        switch (view.getId()){
            case R.id.iv_my_profile:
                conn.enlargeProfileImage(url);
                break;
            case R.id.profile_layout:
                conn.setBottomMenuInvisible();
                editProfileLayout= new EditProfileLayout(getContext());
                meFrameLayout.addView(editProfileLayout);
                editProfileLayout.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getAction()){
                            case MotionEvent.ACTION_DOWN:
                                    v.getParent().requestDisallowInterceptTouchEvent(true);
                                    return true;
                            default:
                                break;
                        }
                        return false;
                    }
                });
                editProfileLayout.setInformation(me);
                editProfileLayout.getRlChangeProfileImage().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Build.VERSION.SDK_INT>Build.VERSION_CODES.M){
                            if (ContextCompat.checkSelfPermission(getContext(),
                                    Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED &&
                                    ContextCompat.checkSelfPermission(getContext(),
                                            Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
                                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Thumbnails.INTERNAL_CONTENT_URI);
                                startActivityForResult(Intent.createChooser(intent, "Select Your Profile Picture"), SELECT_IMAGE);
                            }else {
                                requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE,
                                        Manifest.permission.READ_EXTERNAL_STORAGE}, ACCESS_EXTERNAL);
                            }
                        }else {
                            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Thumbnails.INTERNAL_CONTENT_URI);
                            startActivityForResult(Intent.createChooser(intent, "Select Your Profile Picture"), SELECT_IMAGE);
                        }
                    }
                });
                editProfileLayout.getRlChangeProfileImage().setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getAction()){
                            case MotionEvent.ACTION_DOWN:
                                v.getParent().requestDisallowInterceptTouchEvent(true);
                                return false;
                            default:
                                break;
                        }
                        return false;
                    }
                });
                editProfileLayout.getRlChangeUsername().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        conn.launchUpdateDialog("edit nickname",editProfileLayout.getTvEditUsername().getText().toString());
                    }
                });
                editProfileLayout.getRlChangeUsername().setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getAction()){
                            case MotionEvent.ACTION_DOWN:
                                v.getParent().requestDisallowInterceptTouchEvent(true);
                                return false;
                            default:
                                break;
                        }
                        return false;
                    }
                });
                editProfileLayout.getRlChangeEmail().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        conn.launchUpdateDialog("edit email",editProfileLayout.getTvEditEmail().getText().toString());
                    }
                });
                editProfileLayout.getRlChangeEmail().setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getAction()){
                            case MotionEvent.ACTION_DOWN:
                                v.getParent().requestDisallowInterceptTouchEvent(true);
                                return false;
                            case MotionEvent.ACTION_UP:
                                v.getParent().requestDisallowInterceptTouchEvent(false);
                                break;
                            default:
                                break;
                        }
                        return false;
                    }
                });
                editProfileLayout.getRlChangeRegion().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        conn.launchLocationListDialog();
                    }
                });
                editProfileLayout.getRlChangeRegion().setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getAction()){
                            case MotionEvent.ACTION_DOWN:
                                v.getParent().requestDisallowInterceptTouchEvent(true);
                                return false;
                            default:
                                break;
                        }
                        return false;
                    }
                });
                editProfileLayout.getRlEditGender().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        conn.launchUpdateDialog("edit gender",editProfileLayout.getTvChangeGender().getText().toString());
                    }
                });
                editProfileLayout.getRlEditGender().setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getAction()){
                            case MotionEvent.ACTION_DOWN:
                                v.getParent().requestDisallowInterceptTouchEvent(true);
                                return false;
                            default:
                                break;
                        }
                        return false;
                    }
                });
                editProfileLayout.getRlWhatsUp().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        conn.launchUpdateDialog("edit whatsup",editProfileLayout.getTvWhatsUp().getText().toString());
                    }
                });
                editProfileLayout.getRlWhatsUp().setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getAction()){
                            case MotionEvent.ACTION_DOWN:
                                v.getParent().requestDisallowInterceptTouchEvent(true);
                                return false;
                            default:
                                break;
                        }
                        return false;
                    }
                });
                editProfileLayout.getRlBirthday().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        conn.launchUpdateDialog("edit birthday",editProfileLayout.getTvBirthday().getText().toString());
                    }
                });
                editProfileLayout.getRlBirthday().setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getAction()){
                            case MotionEvent.ACTION_DOWN:
                                v.getParent().requestDisallowInterceptTouchEvent(true);
                                return false;
                            default:
                                break;
                        }
                        return false;
                    }
                });
                editProfileLayout.getRlQRCode().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), QRCodeActivity.class);
                        intent.putExtra("username",MainActivity.me.getUsername());
                        startActivity(intent);
                    }
                });
                break;
            case R.id.llMyPosts:
                SubActivity_.intent(this).extra("type","moments").
                        extra("id",MainActivity.me.getId()).extra("nickname",MainActivity.me.getNickname()).start();
                break;
            default:
                break;
        }
    }
    public EditProfileLayout getEditProfileLayout() {
        return editProfileLayout;
    }
    public void removeEditProfileLayout(){
        meFrameLayout.removeViewInLayout(editProfileLayout);
        editProfileLayout=null;
    }
    public void setIvMyProfile(Me me){
            this.me = me;
            url= MainActivity.domain_url+me.getUrl();
            if (ivMyGender!=null &&
                    tvMyUsername!=null &&
                    tvMyAge!=null &&
                    ivMyProfile!=null){
                if (me.getGender().equals("Female")){
                    ivMyGender.setImageResource(R.drawable.female_icon);
                }else if (me.getGender().equals("Male")){
                    ivMyGender.setImageResource(R.drawable.male_icon);
                }
                tvMyUsername.setText(me.getNickname());
                tvMyAge.setText(String.valueOf(me.getAge()));
                ImageRequest request = new ImageRequest(url, new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        ivMyProfile.setImageBitmap(response);
                    }
                }, 0, 0, ImageView.ScaleType.CENTER_CROP, null, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(),"Error...",Toast.LENGTH_SHORT).show();
                    }
                });
                MySingleton.getmInstance(getContext().getApplicationContext()).addImageRequestToRequestQueue(request);
            }
    }
    @OnActivityResult(SELECT_IMAGE) void MeFragmentResult(int resultCode, Intent data){
        if (resultCode==RESULT_OK){
            uri = data.getData();
            conn.launchUpdateDialog("edit profile image",uri.toString());
        }
    }
    public interface MeFragmentInterface{
        void enlargeProfileImage(String url);
        void launchUpdateDialog(String title,String content);
        void launchLocationListDialog();
        void setBottomMenuInvisible();
    }
}
