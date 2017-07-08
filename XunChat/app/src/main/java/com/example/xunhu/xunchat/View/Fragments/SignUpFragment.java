package com.example.xunhu.xunchat.View.Fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.method.KeyListener;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xunhu.xunchat.R;
import com.example.xunhu.xunchat.View.MainActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;

/**
 * Created by xunhu on 6/10/2017.
 */

public class SignUpFragment extends Fragment {
    private static final int SELECT_IMAGE = 0;
    @BindView(R.id.iv_profile_image) ImageView ivNewImageProfile;
    @BindView(R.id.et_register_username) EditText etRegisterUsername;
    @BindView(R.id.et_register_password) EditText etRegisterPassword;
    @BindView(R.id.et_email) EditText etRegisterEmail;
    @BindView(R.id.btnRegister) Button btnRegister;
    @BindView(R.id.et_register_gender) EditText etGender;
    @BindView(R.id.et_register_region) EditText etRegion;
    @BindView(R.id.et_register_birth) EditText etBirthday;
    @BindView(R.id.et_register_nickname) EditText etNickname;
    private Unbinder unbinder;
    private Uri profileImageURI;
    public SignUpInterface comm;
    Bitmap bitmap =null;
    private static final int ACCESS_EXTERNAL = 1;
    ProgressDialog registerProgressDialog;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        comm = (SignUpInterface) activity;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.register_fragment_layout,container,false);
        unbinder = ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.iv_profile_image,R.id.btnRegister,R.id.et_register_gender,R.id.et_register_region,R.id.et_register_birth})
    public void respond(View view){
        switch (view.getId()){
            case R.id.iv_profile_image:
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
                break;
            case R.id.btnRegister:
                if (!etRegisterUsername.getText().toString().isEmpty() &&
                        !etRegisterPassword.getText().toString().isEmpty() &&
                        !etRegisterEmail.getText().toString().isEmpty() && !etGender.getText().toString().isEmpty()
                        && !etRegion.getText().toString().isEmpty()){
                    String username = etRegisterUsername.getText().toString();
                    String password = etRegisterPassword.getText().toString();
                    String email = etRegisterEmail.getText().toString();
                    if (bitmap==null){
                        bitmap = ((BitmapDrawable)ivNewImageProfile.getDrawable()).getBitmap();
                    }
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG,80,byteArrayOutputStream);
                    byte[] imageBytes = byteArrayOutputStream.toByteArray();
                    String url = Base64.encodeToString(imageBytes,Base64.DEFAULT);
                    String token = "";
                    String gender = etGender.getText().toString();
                    String region = etRegion.getText().toString();
                    String birthday =etBirthday.getText().toString();
                    String nickname = etNickname.getText().toString();
                    String QRCode = "";
                    comm.operateSignUp(username,nickname,password,email,url,birthday,token,gender,region,QRCode);
                }else{
                        if (etRegisterUsername.getText().toString().isEmpty()) {
                            etRegisterUsername.setError("Please enter your username");
                            return;
                        }else if (etRegisterPassword.getText().toString().isEmpty()){
                            etRegisterPassword.setError("Please enter your password");
                            return;
                        }else if (etRegisterEmail.getText().toString().isEmpty()){
                            etRegisterEmail.setError("Please enter your email");
                            return;
                        }else if (etGender.getText().toString().isEmpty()){
                            etGender.setError("Please select your gender");
                            return;
                        }else if (etRegion.getText().toString().isEmpty()){
                            etRegion.setError("Please enter your region");
                            return;
                        }else if (etBirthday.getText().toString().isEmpty()){
                            etBirthday.setError("Please enter your birthday");
                            return;
                        }else if (etNickname.getText().toString().isEmpty()){
                            etNickname.setError("Please enter your nickname");
                        }
                    }
                    break;
            case R.id.et_register_gender:
                comm.launchGenderSelectionFragment();
                break;
            case R.id.et_register_region:
                comm.launchLocationListsFragment();
                break;
            case R.id.et_register_birth:
                comm.launchBirthdayPickerFragment();
                break;
            default:
                break;
        }
    }
    public void setEtBirthday(String birthday){
        etBirthday.setText(birthday);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case SELECT_IMAGE:
                if (resultCode==RESULT_OK){
                    profileImageURI = data.getData();
                        //bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),profileImageURI);
                    bitmap = MediaStore.Images.Thumbnails.getThumbnail(getActivity().getContentResolver(),
                            Long.parseLong(profileImageURI.getLastPathSegment()),  MediaStore.Images.Thumbnails.MICRO_KIND,null);
                        ivNewImageProfile.setImageBitmap(bitmap);
                }
                break;
            default:
                break;
        }
    }
    public void setLocation(String location){
        etRegion.setText(location);
    }
    public void existError(String msg){
        etRegisterUsername.setError(msg);
    }
    public void setGender(String gender){
            etGender.setText(gender);
    }

    public interface SignUpInterface{
        void operateSignUp(String username, String nickname,String password, String email, String url, String birthday, String token,String gender,String region,String QRCode);
        void launchGenderSelectionFragment();
        void launchBirthdayPickerFragment();
        void launchLocationListsFragment();
    }
}
