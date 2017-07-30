package com.example.xunhu.xunchat.Presenter;

import android.content.Context;

import com.example.xunhu.xunchat.Model.UserLogoutModel;
import com.example.xunhu.xunchat.Presenter.Interfaces.LogoutActionStatus;
import com.example.xunhu.xunchat.Presenter.Interfaces.LogoutPresenterAction;
import com.example.xunhu.xunchat.View.Interfaces.LogoutView;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

/**
 * Created by xunhu on 6/27/2017.
 */
@EBean
public class LogoutPresenter implements LogoutPresenterAction,LogoutActionStatus {
    LogoutView logoutView;
    @Bean UserLogoutModel userLogoutModel;
    public LogoutPresenter(Context context){
        this.userLogoutModel= new UserLogoutModel(context);
        userLogoutModel.setLogoutActionStatus(this);
    }
    public void setLogoutView(LogoutView logoutView) {
        this.logoutView = logoutView;
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
