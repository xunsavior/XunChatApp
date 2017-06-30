package com.example.xunhu.xunchat.View.Fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xunhu.xunchat.R;
import com.example.xunhu.xunchat.View.MainActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by xunhu on 6/14/2017.
 */

@SuppressLint("ValidFragment")
public class UpdateProfileDialogFragment extends DialogFragment {
    String title;
    String content;
    EditText editText;
    TextView textView;
    DatePicker datePicker;
    ImageView ivSelectedImage;
    Bitmap bitmap;
    View view;
    UpdateProfileDialogFragmentInterface comm;
    LocationManager locationManager;
    LocationListener locationListener;
    private static final int  ACCESS_LOCATION =0;
    double lat =0.0;
    double lon = 0.0;
    RadioGroup radioGroup;
    public UpdateProfileDialogFragment(String title,String content){
        this.title=title;
        this.content=content;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        comm = (UpdateProfileDialogFragmentInterface) activity;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
       if (title.equals("edit gender")){
            view = inflater.inflate(R.layout.update_gender_dialog_layout,null);
            radioGroup = (RadioGroup) view.findViewById(R.id.group);
            if (content.equals("Female")){
                radioGroup.check(R.id.radio_female);
            }else if (content.equals("Male")){
                radioGroup.check(R.id.radio_male);
            }

        }else if (title.equals("edit birthday")){
            view = inflater.inflate(R.layout.date_picker_layout,null);
            datePicker = (DatePicker) view.findViewById(R.id.dp);
            datePicker.setMaxDate(new Date().getTime());
            String[] date = content.split("/");
            int year = Integer.parseInt(date[2]);
            int month = Integer.parseInt(date[1]);
            int day = Integer.parseInt(date[0]);
            datePicker.updateDate(year,month,day);
        }else if (title.equals("edit profile image")){
            view = inflater.inflate(R.layout.edit_profile_image_layout,null);
            ivSelectedImage = (ImageView) view.findViewById(R.id.ivSelectedProfileImage);
            ivSelectedImage.getLayoutParams().width = MainActivity.getScreenWidth();
            ivSelectedImage.getLayoutParams().height= MainActivity.getScreenWidth();
            try {
                Uri uri = Uri.parse(content);
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),uri);
                ivSelectedImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            view = inflater.inflate(R.layout.edit_username_dialog_layout,null);
            editText = (EditText) view.findViewById(R.id.et_information_dialog);
            editText.setText(content);
            textView = (TextView)view.findViewById(R.id.tv_hint);
        }

        builder.setTitle(title).setView(view).setPositiveButton("Set", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (title.equals("edit gender")){
                    RadioButton radioButton = (RadioButton) radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
                    comm.performUpdate(title,radioButton.getText().toString());
                }else if (title.equals("edit birthday")){
                    String year = String.valueOf(datePicker.getYear());
                    String month = String.valueOf(datePicker.getMonth());
                    String day = String.valueOf(datePicker.getDayOfMonth());
                    String birthday = day+"/"+month+"/"+year;
                    comm.performUpdate(title,birthday);
                }else if (title.equals("edit profile image")){
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG,70,byteArrayOutputStream);
                    byte[] imageBytes = byteArrayOutputStream.toByteArray();
                    String url = Base64.encodeToString(imageBytes,Base64.DEFAULT);
                    comm.performUpdate(title,url);
                }else {
                    comm.performUpdate(title,editText.getText().toString());
                }
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return builder.create();
    }

    public interface UpdateProfileDialogFragmentInterface{
        void performUpdate(String title,String content);
    }
}
