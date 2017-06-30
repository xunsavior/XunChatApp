package com.example.xunhu.xunchat.Model;

import com.example.xunhu.xunchat.Model.AsyTasks.CookieValidateTask;
import com.example.xunhu.xunchat.Model.Interfaces.CRUDCookieValidateOptions;
import com.example.xunhu.xunchat.Presenter.Interfaces.CookieValidateActionStatus;

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
