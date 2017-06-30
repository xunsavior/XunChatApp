package com.example.xunhu.xunchat.Presenter;

import com.example.xunhu.xunchat.Model.UserRegisterModel;
import com.example.xunhu.xunchat.Presenter.Interfaces.RegisterActionStatus;
import com.example.xunhu.xunchat.Presenter.Interfaces.RegisterPresenterAction;
import com.example.xunhu.xunchat.View.Interfaces.RegisterView;

/**
 * Created by xunhu on 6/10/2017.
 */

public class MyRegisterPresenter implements RegisterPresenterAction,RegisterActionStatus {
    private RegisterView registerView;
    private UserRegisterModel userModel;

    public MyRegisterPresenter(RegisterView registerView){
        this.registerView=registerView;
        this.userModel = new UserRegisterModel(this);
    }
    public RegisterView getRegisterView(){
        return registerView;
    }

    @Override
    public void registerSuccess(String msg) {
        getRegisterView().switchToLoginInterface();
    }

    @Override
    public void registerFail(String msg) {
        getRegisterView().registerFail(msg);
    }

    @Override
    public void attemptRegister(String username, String nickname, String password, String email, String url, String birthday,String token, String gender, String region, String QRCode) {
        userModel.createUser(username,nickname,password,email,url,birthday,token,gender,region,QRCode);
    }
}
