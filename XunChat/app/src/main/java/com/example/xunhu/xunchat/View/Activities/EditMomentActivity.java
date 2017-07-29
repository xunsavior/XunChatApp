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

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xunhu on 6/20/2017.
 */
@EActivity(R.layout.edit_moment_layout)
public class EditMomentActivity extends Activity implements LocationListDialog.LocationDialogInterface{
    public static Me me = MainActivity.me;
    @ViewById(R.id.iv_edit_moment_back) ImageView btnBack;
    @ViewById(R.id.btn_edit_moment_send) Button btnSend;
    @ViewById(R.id.et_edit_moment) EditText edMoment;
    @ViewById(R.id.iv_get_current_location) ImageView ivCurrentLocation;
    @ViewById(R.id.tv_display_current_location) TextView tvDisplayLocation;
    @ViewById(R.id.iv_selected_post_image) ImageView ivSelectedImage;
    @AfterViews void setEditMomentActivityViews(){
        boolean hasImage = getIntent().getExtras().getBoolean("image");
        System.out.println("@ image "+hasImage);
        checkImagePostOrNot(hasImage);
    }
    @Click({R.id.iv_edit_moment_back,R.id.btn_edit_moment_send,R.id.iv_get_current_location})
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
