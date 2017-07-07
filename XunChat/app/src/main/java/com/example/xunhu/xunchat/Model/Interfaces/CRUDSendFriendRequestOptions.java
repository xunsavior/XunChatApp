package com.example.xunhu.xunchat.Model.Interfaces;

import com.example.xunhu.xunchat.Model.Entities.Me;
import com.example.xunhu.xunchat.Model.Entities.User;

/**
 * Created by xunhu on 6/23/2017.
 */

public interface CRUDSendFriendRequestOptions {
    void sendFriendRequest(Me me, String extras,User user);
}
