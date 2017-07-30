package com.example.xunhu.xunchat.View.Activities;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.example.xunhu.xunchat.R;
import com.example.xunhu.xunchat.View.Fragments.CameraViewFragment;
import com.example.xunhu.xunchat.View.Fragments.CameraViewFragment_;
import com.example.xunhu.xunchat.View.Fragments.CapturedPhotoFragment;
import com.example.xunhu.xunchat.View.Fragments.CapturedPhotoFragment_;
import com.google.android.gms.vision.CameraSource;

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
    CapturedPhotoFragment capturedPhotoFragment;
    boolean isBackShowing = false;
    @AfterViews void setPhotoTakenActivity(){
        cameraViewFragment = new CameraViewFragment_();
        capturedPhotoFragment = new CapturedPhotoFragment_();
        getFragmentManager().beginTransaction().replace(R.id.photo_container,cameraViewFragment).commit();
    }
    @Override
    public void convertPhoto(byte[] bytes,int cameraFace) {
        if (cameraFace== CameraSource.CAMERA_FACING_BACK){
            capturedPhotoFragment.setBackPhoto(bytes);
        }else {
            capturedPhotoFragment.setFrontPhoto(bytes);
        }
        flipFragment();
    }

    @Override
    public void convertSelectedPhoto(Bitmap bitmap) {
        capturedPhotoFragment.setSelectedPhoto(bitmap);
        flipFragment();
    }

    private void flipFragment(){
        if (isBackShowing){
            getFragmentManager().popBackStack();
            isBackShowing=false;
        }else {
            isBackShowing=true;
            getFragmentManager().beginTransaction().setCustomAnimations(R.animator.card_flip_right_in,
                    R.animator.card_flip_right_out,R.animator.card_flip_left_in,
                    R.animator.card_flip_left_out).replace(R.id.photo_container,capturedPhotoFragment).
                    addToBackStack(null).commit();
        }
    }
    @Override
    public void onBackPressed() {
        isBackShowing=false;
        super.onBackPressed();
    }
}
