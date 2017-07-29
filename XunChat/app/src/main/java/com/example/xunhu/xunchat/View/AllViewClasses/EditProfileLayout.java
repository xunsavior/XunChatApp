package com.example.xunhu.xunchat.View.AllViewClasses;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.example.xunhu.xunchat.Model.AsyTasks.MySingleton;
import com.example.xunhu.xunchat.Model.Entities.Me;
import com.example.xunhu.xunchat.R;
import com.example.xunhu.xunchat.View.Activities.ProfileThemeActivity;
import com.example.xunhu.xunchat.View.MainActivity;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xunhu on 6/14/2017.
 */
public class EditProfileLayout extends LinearLayout {
     ImageView etEditImage;
     RelativeLayout rlChangeProfileImage;
     RelativeLayout rlChangeUsername;
     TextView tvEditUsername;
     RelativeLayout rlChangeEmail;
     TextView tvEditEmail;
     TextView tvChangeGender;
     RelativeLayout rlEditGender;
     RelativeLayout rlChangeRegion;
     TextView tvEditRegion;
     RelativeLayout rlWhatsUp;
     TextView tvWhatsUp;
     RelativeLayout rlBirthday;
     TextView tvBirthday;
     LinearLayout llAll;
     TextView tvDisplayUsername;
     RelativeLayout rlQRCode;

    public EditProfileLayout(Context context) {
        super(context);
        LayoutInflater.from(getContext()).inflate(R.layout.edit_profile_layout,this);
        etEditImage = (ImageView) findViewById(R.id.iv_edit_image);
        tvEditUsername = (TextView) findViewById(R.id.tv_edit_username);
        tvEditEmail = (TextView) findViewById(R.id.tv_edit_email);
        tvChangeGender = (TextView) findViewById(R.id.tv_edit_gender);
        tvEditRegion = (TextView) findViewById(R.id.tv_edit_region);
        tvWhatsUp = (TextView) findViewById(R.id.tv_edit_whatsup);
        tvBirthday = (TextView) findViewById(R.id.tv_edit_birthday);
        tvDisplayUsername = (TextView) findViewById(R.id.tv_display_username);

        rlChangeEmail = (RelativeLayout) findViewById(R.id.layout_change_email);
        rlEditGender = (RelativeLayout) findViewById(R.id.layout_change_gender);
        rlChangeRegion = (RelativeLayout) findViewById(R.id.layout_change_region);
        rlChangeProfileImage = (RelativeLayout)findViewById(R.id.layout_change_profile_image);
        rlChangeUsername = (RelativeLayout) findViewById(R.id.layout_change_username);
        rlWhatsUp = (RelativeLayout) findViewById(R.id.layout_change_whatsup);
        rlBirthday = (RelativeLayout) findViewById(R.id.layout_change_birthday);
        rlQRCode = (RelativeLayout) findViewById(R.id.layout_change_QRCode);
    }

    public void setInformation(Me me){
        String username = me.getUsername();
        String nickname = me.getNickname();
        String email = me.getEmail();
        String gender = me.getGender();
        String region = me.getRegion();
        String whatsup = me.getWhatsup();
        String birthday = me.getBirthday();
        String url = MainActivity.domain_url+me.getUrl();
        tvDisplayUsername.setText(username);
        tvEditUsername.setText(nickname);
        tvEditEmail.setText(email);
        tvChangeGender.setText(gender);
        tvEditRegion.setText(region);
        tvWhatsUp.setText(whatsup);
        tvBirthday.setText(birthday);
        ImageRequest request = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                etEditImage.setImageBitmap(response);
            }
        }, 0, 0, ImageView.ScaleType.CENTER_CROP, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),"Error...",Toast.LENGTH_SHORT).show();
            }
        });
        MySingleton.getmInstance(getContext().getApplicationContext()).addImageRequestToRequestQueue(request);
    }

    public ImageView getEtEditImage() {
        return etEditImage;
    }

    public RelativeLayout getRlChangeEmail() {
        return rlChangeEmail;
    }

    public RelativeLayout getRlChangeProfileImage() {
        return rlChangeProfileImage;
    }

    public RelativeLayout getRlChangeRegion() {
        return rlChangeRegion;
    }

    public RelativeLayout getRlChangeUsername() {
        return rlChangeUsername;
    }

    public RelativeLayout getRlEditGender() {
        return rlEditGender;
    }

    public TextView getTvChangeGender() {
        return tvChangeGender;
    }

    public TextView getTvEditEmail() {
        return tvEditEmail;
    }

    public TextView getTvEditRegion() {
        return tvEditRegion;
    }

    public TextView getTvEditUsername() {
        return tvEditUsername;
    }

    public TextView getTvBirthday() {
        return tvBirthday;
    }

    public RelativeLayout getRlBirthday() {
        return rlBirthday;
    }

    public RelativeLayout getRlWhatsUp() {
        return rlWhatsUp;
    }

    public TextView getTvWhatsUp() {
        return tvWhatsUp;
    }

    public RelativeLayout getRlQRCode() {
        return rlQRCode;
    }
}
