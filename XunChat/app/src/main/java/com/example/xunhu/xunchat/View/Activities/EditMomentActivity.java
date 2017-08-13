package com.example.xunhu.xunchat.View.Activities;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.xunhu.xunchat.Model.Entities.Me;
import com.example.xunhu.xunchat.R;
import com.example.xunhu.xunchat.View.Fragments.LocationListDialog;
import com.example.xunhu.xunchat.View.MainActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.IOException;

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
    @ViewById
    ImageView ivSelectedOne,ivSelectedTwo,ivSelectedThree;
    @ViewById
    LinearLayout selectedImageLayout;
    String uriOne,uriTwo,uriThree;
    @AfterViews void setEditMomentActivityViews(){
        boolean hasImage = getIntent().getExtras().getBoolean("image");
        try {
            checkImagePostOrNot(hasImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    public void postImages(){
       Bitmap bitmapOne = resizeBitmap(((BitmapDrawable)ivSelectedOne.getDrawable()).getBitmap());
       if (uriTwo!=null){

       }
       if (uriThree!=null){

       }
    }
    public void checkImagePostOrNot(boolean hasImage) throws IOException {
        if (!hasImage){
            selectedImageLayout.setVisibility(View.GONE);
        }else {
             uriOne = getIntent().getStringExtra("image_0");
             uriTwo = getIntent().getStringExtra("image_1");
             uriThree = getIntent().getStringExtra("image_2");
            ivSelectedOne.setImageURI(Uri.parse(uriOne));
            if (uriTwo!=null){
                ivSelectedTwo.setImageURI(Uri.parse(uriTwo));
            }
            if (uriThree!=null){
                ivSelectedThree.setImageURI(Uri.parse(uriThree));
            }
        }
    }
    @Override
    public void setSelectedLocation(String location) {
        tvDisplayLocation.setText(location);
    }

    @Override
    public void setEditLocation(String title, String content) {

    }
    Bitmap resizeBitmap(Bitmap bitmap){
        float ratio = (float) bitmap.getByteCount()/(float) 1024/(float)1024;
        if (ratio>=1){
            float originalWidth = bitmap.getWidth();
            float originalHeight = bitmap.getHeight();
            float resizeWidth = (float) (originalWidth/Math.sqrt(ratio));
            float resizeHeight = (float) (originalHeight/Math.sqrt(ratio));
            bitmap=Bitmap.createScaledBitmap(bitmap, (int) resizeWidth, (int) resizeHeight, true);
        }
        return bitmap;
    }
}
