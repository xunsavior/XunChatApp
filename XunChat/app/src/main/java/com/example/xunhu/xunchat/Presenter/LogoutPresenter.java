package com.example.xunhu.xunchat.Presenter;

import com.example.xunhu.xunchat.Model.UserLogoutModel;
import com.example.xunhu.xunchat.Presenter.Interfaces.LogoutActionStatus;
import com.example.xunhu.xunchat.Presenter.Interfaces.LogoutPresenterAction;
import com.example.xunhu.xunchat.View.Interfaces.LogoutView;

/**
 * Created by xunhu on 6/27/2017.
 */

public class LogoutPresenter implements LogoutPresenterAction,LogoutActionStatus {
    LogoutView logoutView;
    UserLogoutModel userLogoutModel;
    public LogoutPresenter(LogoutView logoutView){
        this.logoutView=logoutView;
        this.userLogoutModel= new UserLogoutModel(this);
    }

    public LogoutView getLogoutView() {
        return logoutView;
    }

    @Override
    public void LogoutSuccess(String msg) {
        getLogoutView().logoutSuccessful(msg);
    }

    @Override
    public void LogoutFail(String msg) {
        getLogoutView().logoutFail(msg);
    }

    @Override
    public void implementLogout(String username) {
        userLogoutModel.implementLogout(username);
    }
}
