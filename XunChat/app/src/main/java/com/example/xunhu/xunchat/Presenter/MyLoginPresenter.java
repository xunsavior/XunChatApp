package com.example.xunhu.xunchat.Presenter;

import com.example.xunhu.xunchat.Model.UserLoginModel;
import com.example.xunhu.xunchat.Presenter.Interfaces.LoginActionStatus;
import com.example.xunhu.xunchat.Presenter.Interfaces.LoginPresenterAction;
import com.example.xunhu.xunchat.View.Interfaces.LoginView;

/**
 * Created by xunhu on 6/12/2017.
 */

public class MyLoginPresenter implements LoginActionStatus,LoginPresenterAction {
    private LoginView loginView;
    private UserLoginModel userLoginModel;

    public MyLoginPresenter(LoginView loginView){
        this.loginView=loginView;
        this.userLoginModel = new UserLoginModel(this);
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
