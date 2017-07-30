package com.example.xunhu.xunchat.Presenter;

import android.content.Context;

import com.example.xunhu.xunchat.Model.UserLoginModel;
import com.example.xunhu.xunchat.Presenter.Interfaces.LoginActionStatus;
import com.example.xunhu.xunchat.Presenter.Interfaces.LoginPresenterAction;
import com.example.xunhu.xunchat.View.Interfaces.LoginView;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

/**
 * Created by xunhu on 6/12/2017.
 */
@EBean
public class MyLoginPresenter implements LoginActionStatus,LoginPresenterAction {
    private LoginView loginView;
    @Bean UserLoginModel userLoginModel;

    public MyLoginPresenter(Context context){
        this.userLoginModel = new UserLoginModel(context);
        userLoginModel.setLoginActionStatus(this);
    }
    public void setLoginView(LoginView loginView) {
        this.loginView = loginView;
    }

    public LoginView getLoginView(){
        return this.loginView;
    }

    @Override
    public void loginSuccess(String msg) {
        getLoginView().refreshActivity(msg);
    }

    @Override
    public void loginFail(String msg) {
        getLoginView().loginFail(msg);
    }

    @Override
    public void attemptLogin(String username, String password, String token) {
        userLoginModel.loginCheck(username,password,token);
    }
}
