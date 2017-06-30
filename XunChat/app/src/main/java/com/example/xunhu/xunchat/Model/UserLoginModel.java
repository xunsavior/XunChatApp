package com.example.xunhu.xunchat.Model;

import com.example.xunhu.xunchat.Model.AsyTasks.LoginTask;
import com.example.xunhu.xunchat.Model.Interfaces.CRUDLoginOptions;
import com.example.xunhu.xunchat.Presenter.Interfaces.LoginActionStatus;

/**
 * Created by xunhu on 6/12/2017.
 */

public class UserLoginModel implements CRUDLoginOptions{
    LoginActionStatus loginActionStatus;

    public UserLoginModel(LoginActionStatus loginActionStatus){
        this.loginActionStatus=loginActionStatus;
    }
    @Override
    public void loginCheck(String username, String password, String token) {
        new LoginTask(loginActionStatus).execute(username,password,token);
    }
}
