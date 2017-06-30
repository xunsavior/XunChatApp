package com.example.xunhu.xunchat.Model;

import com.example.xunhu.xunchat.Model.AsyTasks.LogoutTask;
import com.example.xunhu.xunchat.Model.Interfaces.CRUDLogoutOptions;
import com.example.xunhu.xunchat.Presenter.Interfaces.LogoutActionStatus;

/**
 * Created by xunhu on 6/27/2017.
 */

public class UserLogoutModel implements CRUDLogoutOptions {
    LogoutActionStatus logoutActionStatus;

    public UserLogoutModel(LogoutActionStatus logoutActionStatus){
        this.logoutActionStatus=logoutActionStatus;
    }

    @Override
    public void implementLogout(String username) {
        new LogoutTask(logoutActionStatus).execute(username);
    }
}
