package com.example.xunhu.xunchat.Presenter;

import android.app.Application;
import android.content.Context;

import com.example.xunhu.xunchat.Model.UserUpdateProfileModel;
import com.example.xunhu.xunchat.Presenter.Interfaces.UpdateProfileActionStatus;
import com.example.xunhu.xunchat.Presenter.Interfaces.UpdateProfilePresenterAction;
import com.example.xunhu.xunchat.View.Interfaces.UpdateProfileView;

/**
 * Created by xunhu on 6/14/2017.
 */

public class MyProfileUpdatePresenter implements UpdateProfileActionStatus,UpdateProfilePresenterAction {
    UpdateProfileView updateProfileView;
    UserUpdateProfileModel userUpdateProfileModel;
    Context context;

    public MyProfileUpdatePresenter(UpdateProfileView updateProfileView, Context context){
        this.updateProfileView=updateProfileView;
        this.context = context;
        userUpdateProfileModel = new UserUpdateProfileModel(this,context);
    }

    public UpdateProfileView getUpdateProfileView() {
        return updateProfileView;
    }

    @Override
    public void updateSuccess(String msg) {
        getUpdateProfileView().changeMyProfile(msg);
    }

    @Override
    public void updateFail(String msg) {
        getUpdateProfileView().updateFail(msg);
    }

    @Override
    public void attemptUpdateProfile(String username, String title, String content) {
        userUpdateProfileModel.updateCheck(username,title,content);
    }
}
