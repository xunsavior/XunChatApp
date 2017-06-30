package com.example.xunhu.xunchat.Presenter.Interfaces;

import com.example.xunhu.xunchat.Model.Entities.Me;

/**
 * Created by xunhu on 6/23/2017.
 */

public interface SendFriendRequestAction {
    void sendFriendRequest(Me me, String extras, String targetToken);
}
