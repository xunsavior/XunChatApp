package com.example.xunhu.xunchat.Model;

import android.content.Context;

import com.example.xunhu.xunchat.Model.AsyTasks.LoginTask;
import com.example.xunhu.xunchat.Model.Interfaces.CRUDLoginOptions;
import com.example.xunhu.xunchat.Presenter.Interfaces.LoginActionStatus;

import org.androidannotations.annotations.EBean;

/**
 * Created by xunhu on 6/12/2017.
 */
@EBean
public class UserLoginModel implements CRUDLoginOptions{
    LoginActionStatus loginActionStatus;
    Context context;
    @org.androidannotations.annotations.res.StringRes static String login_url;
    public UserLoginModel(Context context){
        this.context=context;
    }
    public void setLoginActionStatus(LoginActionStatus loginActionStatus) {
        this.loginActionStatus = loginActionStatus;
    }
    @Override
    public void loginCheck(String username, String password, String token) {
        new LoginTask(loginActionStatus,login_url).execute(username,password,token);
    }
}
