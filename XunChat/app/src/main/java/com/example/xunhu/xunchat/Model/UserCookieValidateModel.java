package com.example.xunhu.xunchat.Model;

import android.annotation.SuppressLint;
import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.xunhu.xunchat.Model.AsyTasks.CookieValidateTask;
import com.example.xunhu.xunchat.Model.Interfaces.CRUDCookieValidateOptions;
import com.example.xunhu.xunchat.Presenter.Interfaces.CookieValidateActionStatus;
import com.example.xunhu.xunchat.R;
import com.example.xunhu.xunchat.View.MainActivity;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.SupposeBackground;
import org.androidannotations.annotations.res.StringRes;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by xunhu on 6/12/2017.
 */
@EBean
public class UserCookieValidateModel implements CRUDCookieValidateOptions {
    CookieValidateActionStatus cookieValidateActionStatus;
    Context context;

    @org.androidannotations.annotations.res.StringRes static String validate_login;
    public UserCookieValidateModel(Context context){
        this.context=context;
    }
    public void setCookieValidateActionStatus(CookieValidateActionStatus cookieValidateActionStatus) {
        this.cookieValidateActionStatus = cookieValidateActionStatus;
    }
    @Override
    public void cookieCheck(final String username, final String password) {
            new CookieValidateTask(cookieValidateActionStatus,validate_login).execute(username,password);
    }
    @Background
    void validateMyAccount(String username, String password){
        final JSONObject object = new JSONObject();


    }
}
