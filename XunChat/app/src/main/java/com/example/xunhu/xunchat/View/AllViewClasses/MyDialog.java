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
