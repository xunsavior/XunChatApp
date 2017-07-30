package com.example.xunhu.xunchat.Model;

import android.content.Context;
import com.example.xunhu.xunchat.Model.AsyTasks.RegisterTask;
import com.example.xunhu.xunchat.Model.Interfaces.CRUDRegisterOptions;
import com.example.xunhu.xunchat.Presenter.Interfaces.RegisterActionStatus;

import org.androidannotations.annotations.EBean;


/**
 * Created by xunhu on 6/10/2017.
 */
@EBean
public class UserRegisterModel implements CRUDRegisterOptions {
    RegisterActionStatus registerActionStatus;
    Context context;
    @org.androidannotations.annotations.res.StringRes static String register_url;
    public UserRegisterModel(Context context){
        this.context=context;
    }
    public void setRegisterActionStatus(RegisterActionStatus registerActionStatus) {
        this.registerActionStatus = registerActionStatus;
    }
    @Override
    public void createUser(final String username, final String nickname, final String password, final String email,
                           final String url, final String birthday, final String token, final  String gender, final String region, final String QRCode) {
        new RegisterTask(registerActionStatus,register_url).execute(username,nickname,password,email,url,birthday,token,gender,region,QRCode);
    }
}
