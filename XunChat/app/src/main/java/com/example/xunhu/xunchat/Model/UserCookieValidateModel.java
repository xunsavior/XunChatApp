package com.example.xunhu.xunchat.Model;

import android.annotation.SuppressLint;
import android.support.annotation.StringRes;

import com.example.xunhu.xunchat.Model.AsyTasks.CookieValidateTask;
import com.example.xunhu.xunchat.Model.Interfaces.CRUDCookieValidateOptions;
import com.example.xunhu.xunchat.Presenter.Interfaces.CookieValidateActionStatus;
import com.example.xunhu.xunchat.View.MainActivity;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by xunhu on 6/12/2017.
 */
public class UserCookieValidateModel implements CRUDCookieValidateOptions {
    CookieValidateActionStatus cookieValidateActionStatus;
    public UserCookieValidateModel(CookieValidateActionStatus cookieValidateActionStatus){
        this.cookieValidateActionStatus = cookieValidateActionStatus;
    }
    @Override
    public void cookieCheck(String username, String password) {
            new CookieValidateTask(cookieValidateActionStatus).execute(username,password);
    }
}
