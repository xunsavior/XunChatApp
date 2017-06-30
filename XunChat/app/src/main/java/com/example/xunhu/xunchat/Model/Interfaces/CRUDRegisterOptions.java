package com.example.xunhu.xunchat.Model.Interfaces;

import com.example.xunhu.xunchat.View.Interfaces.RegisterView;

/**
 * Created by xunhu on 6/10/2017.
 */

public interface CRUDRegisterOptions {
    void createUser(String username, String nickname, String password, String email, String url, String birthday, String token,String gender,String region,String QRCode);
}
