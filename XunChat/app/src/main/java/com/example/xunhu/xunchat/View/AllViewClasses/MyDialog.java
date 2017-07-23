package com.example.xunhu.xunchat.View.AllViewClasses;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.xunhu.xunchat.R;
import com.example.xunhu.xunchat.View.Activities.ProfileActivity;

/**
 * Created by xunhu on 7/7/2017.
 */

public class MyDialog {
    Activity activity;
    AlertDialog logoutDialog;
    AlertDialog loadingDialog;
    AlertDialog searchDialog;
    AlertDialog microphoneDialog;
    View view0;
    View view1;
    View view2;
    View view3;
    public MyDialog(Activity activity){
        this.activity = activity;
    }
    public void createGifLogoutDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        View view = activity.getLayoutInflater().inflate(R.layout.gif_dialog,null);
        pl.droidsonroids.gif.GifImageView gifImageView = (pl.droidsonroids.gif.GifImageView) view.findViewById(R.id.iv_gif);
        gifImageView.setBackgroundColor(Color.TRANSPARENT);
        gifImageView.setBackgroundResource(R.drawable.logout);
        builder.setView(view);
        logoutDialog = builder.create();
        logoutDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        logoutDialog.show();
    }
    public void createLoadingGifDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        View view = activity.getLayoutInflater().inflate(R.layout.gif_dialog,null);
        pl.droidsonroids.gif.GifImageView gifImageView = (pl.droidsonroids.gif.GifImageView) view.findViewById(R.id.iv_gif);
        gifImageView.setBackgroundColor(Color.TRANSPARENT);
        gifImageView.setBackgroundResource(R.drawable.loading);
        builder.setView(view);
        loadingDialog = builder.create();
        loadingDialog.getWindow().setDimAmount(0);
        loadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        loadingDialog.show();
    }
    public void createBottomGifDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        View myView = activity.getLayoutInflater().inflate(R.layout.gif_dialog,null);
        pl.droidsonroids.gif.GifImageView gifImageView = (pl.droidsonroids.gif.GifImageView) myView.findViewById(R.id.iv_gif);
        gifImageView.setBackgroundColor(Color.TRANSPARENT);
        gifImageView.setBackgroundResource(R.drawable.loading);
        builder.setView(myView);
        searchDialog = builder.create();
        Window window = searchDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        window.setAttributes(wlp);
        searchDialog.getWindow().setDimAmount(0);
        searchDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        searchDialog.show();
    }
    public void createVoiceLevelDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        View view = activity.getLayoutInflater().inflate(R.layout.microphone_dialog_layout,null);
         view0 =view.findViewById(R.id.v_0);
         view1 =view.findViewById(R.id.v_1);
         view2 =view.findViewById(R.id.v_2);
         view3 =view.findViewById(R.id.v_3);
        ImageView ivMicrophone = (ImageView) view.findViewById(R.id.iv_microphone);
        ivMicrophone.setBackgroundColor(Color.TRANSPARENT);
        builder.setView(view);
        microphoneDialog = builder.create();
        microphoneDialog.getWindow().setDimAmount(0);
        microphoneDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        microphoneDialog.show();
    }
    public void cancelVoiceLevelDialog(){
        microphoneDialog.cancel();
    }
    public void setDBResult(int db){
        int number = db/5000;
        switch (number){
            case 0:
                view0.setVisibility(View.INVISIBLE);
                view1.setVisibility(View.INVISIBLE);
                view2.setVisibility(View.INVISIBLE);
                view3.setVisibility(View.INVISIBLE);
                break;
            case 1:
                view0.setVisibility(View.VISIBLE);
                view1.setVisibility(View.INVISIBLE);
                view2.setVisibility(View.INVISIBLE);
                view3.setVisibility(View.INVISIBLE);
                break;
            case 2:
                view0.setVisibility(View.VISIBLE);
                view1.setVisibility(View.VISIBLE);
                view2.setVisibility(View.INVISIBLE);
                view3.setVisibility(View.INVISIBLE);
                break;
            case 3:
                view0.setVisibility(View.VISIBLE);
                view1.setVisibility(View.VISIBLE);
                view2.setVisibility(View.VISIBLE);
                view3.setVisibility(View.INVISIBLE);
                break;
            default:
                view0.setVisibility(View.VISIBLE);
                view1.setVisibility(View.VISIBLE);
                view2.setVisibility(View.VISIBLE);
                view3.setVisibility(View.VISIBLE);
                break;
        }
    }
    public void cancelLogoutDialog(){
        logoutDialog.cancel();
    }
    public void cancelLoadingGifDialog(){
        loadingDialog.cancel();
    }
    public void cancelBottomGifDialog(){
        searchDialog.cancel();
    }
}
