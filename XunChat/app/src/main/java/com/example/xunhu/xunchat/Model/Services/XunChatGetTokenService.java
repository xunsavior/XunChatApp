package com.example.xunhu.xunchat.Model.Services;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by xunhu on 6/23/2017.
 */

public class XunChatGetTokenService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        System.out.println("@ token "+ FirebaseInstanceId.getInstance().getToken());
    }
}
