package com.example.xunhu.xunchat.Model;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.xunhu.xunchat.Model.AsyTasks.LogoutTask;
import com.example.xunhu.xunchat.Model.AsyTasks.MySingleton;
import com.example.xunhu.xunchat.Model.Interfaces.CRUDLogoutOptions;
import com.example.xunhu.xunchat.Presenter.Interfaces.LogoutActionStatus;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.res.StringRes;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xunhu on 6/27/2017.
 */
@EBean
public class UserLogoutModel implements CRUDLogoutOptions {
    LogoutActionStatus logoutActionStatus;
    Context context;
    @org.androidannotations.annotations.res.StringRes static String logout;
    public UserLogoutModel(Context context){
        this.context=context;
    }
    public void setLogoutActionStatus(LogoutActionStatus logoutActionStatus) {
        this.logoutActionStatus = logoutActionStatus;
    }
    @Override
    public void implementLogout(String username) {
        new LogoutTask(logoutActionStatus,logout).execute(username);
    }
}
