package com.example.xunhu.xunchat.Presenter.Interfaces;

import com.example.xunhu.xunchat.Model.Entities.Me;

/**
 * Created by xunhu on 7/3/2017.
 */

public interface FriendRespondPresenterAction {
    void sendRespond(int targetID, Me me,String remark);
}
