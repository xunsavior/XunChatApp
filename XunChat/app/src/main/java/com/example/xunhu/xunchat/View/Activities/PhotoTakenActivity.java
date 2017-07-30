package com.example.xunhu.xunchat.View.Activities;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.example.xunhu.xunchat.R;
import com.example.xunhu.xunchat.View.Fragments.CameraViewFragment;
import com.example.xunhu.xunchat.View.Fragments.CameraViewFragment_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.WindowFeature;


/**
 * Created by xunhu on 7/30/2017.
 */
@Fullscreen
@WindowFeature({Window.FEATURE_NO_TITLE})
@EActivity(R.layout.photo_taken_activity)
public class PhotoTakenActivity extends AppCompatActivity implements CameraViewFragment.CameraViewFragmentInterface {
    CameraViewFragment cameraViewFragment;
    @AfterViews void setPhotoTakenActivity(){
        cameraViewFragment = new CameraViewFragment_();
        getFragmentManager().beginTransaction().replace(R.id.photo_container,cameraViewFragment).commit();
    }
    @Override
    public void convertPhoto(byte[] bytes) {

    }
}
