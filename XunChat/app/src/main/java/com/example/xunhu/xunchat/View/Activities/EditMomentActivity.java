package com.example.xunhu.xunchat.View.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xunhu.xunchat.Model.Entities.Me;
import com.example.xunhu.xunchat.R;
import com.example.xunhu.xunchat.View.Fragments.LocationListDialog;
import com.example.xunhu.xunchat.View.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xunhu on 6/20/2017.
 */

public class EditMomentActivity extends Activity implements LocationListDialog.LocationDialogInterface{
    public static Me me = MainActivity.me;
    @BindView(R.id.iv_edit_moment_back)
    ImageView btnBack;
    @BindView(R.id.btn_edit_moment_send)
    Button btnSend;
    @BindView(R.id.et_edit_moment)
    EditText edMoment;
    @BindView(R.id.iv_get_current_location)
    ImageView ivCurrentLocation;
    @BindView(R.id.tv_display_current_location)
    TextView tvDisplayLocation;
    @BindView(R.id.iv_selected_post_image)
    ImageView ivSelectedImage;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_moment_layout);
        ButterKnife.bind(this);
        checkImagePostOrNot(getIntent().getExtras().getBoolean("image"));
    }

    @OnClick({R.id.iv_edit_moment_back,R.id.btn_edit_moment_send,R.id.iv_get_current_location})
    public void respond(View view){
        switch (view.getId()){
            case R.id.iv_edit_moment_back:
                onBackPressed();
                break;
            case R.id.btn_edit_moment_send:
                break;
            case R.id.iv_get_current_location:
                LocationListDialog dialog = new LocationListDialog("moment");
                dialog.show(getFragmentManager(),"Location Dialog");
                break;
            default:
                break;
        }
    }
    public void checkImagePostOrNot(boolean hasImage){
        if (!hasImage){
            ivSelectedImage.setVisibility(View.GONE);
        }
    }

    @Override
    public void setSelectedLocation(String location) {
        tvDisplayLocation.setText(location);
    }

    @Override
    public void setEditLocation(String title, String content) {

    }
}
