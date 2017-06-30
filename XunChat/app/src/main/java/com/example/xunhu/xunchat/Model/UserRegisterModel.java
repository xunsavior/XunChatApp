package com.example.xunhu.xunchat.Model;

import com.example.xunhu.xunchat.Model.AsyTasks.RegisterTask;
import com.example.xunhu.xunchat.Model.Interfaces.CRUDRegisterOptions;
import com.example.xunhu.xunchat.Presenter.Interfaces.RegisterActionStatus;


/**
 * Created by xunhu on 6/10/2017.
 */

public class UserRegisterModel implements CRUDRegisterOptions {
    RegisterActionStatus registerActionStatus;

    public UserRegisterModel(RegisterActionStatus registerActionStatus){
        this.registerActionStatus=registerActionStatus;
    }
    @Override
    public void createUser(final String username, final String nickname, final String password, final String email,
                           final String url, final String birthday, final String token, final  String gender, final String region, final String QRCode) {
        new RegisterTask(registerActionStatus).execute(username,nickname,password,email,url,birthday,token,gender,region,QRCode);
    }
}
