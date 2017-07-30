package com.example.xunhu.xunchat.View.Activities;

import android.app.Activity;
import android.view.WindowManager;

import com.example.xunhu.xunchat.R;
import com.example.xunhu.xunchat.View.MainActivity_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

/**
 * Created by xunhu on 7/29/2017.
 */
@EActivity(R.layout.background_layout)
public class SplashActivity extends Activity {
    @AfterViews void setSplashViews(){
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        MainActivity_.intent(this).start();
    }
}
