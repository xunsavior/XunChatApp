package com.example.xunhu.xunchat.Presenter.Interfaces;

import com.example.xunhu.xunchat.Model.Entities.Me;

/**
 * Created by xunhu on 7/20/2017.
 */

public interface SendMessageAction {
    void sendingMessage(Me me,String targetUsername,int targetID,int messageType,String message,long timestamp);
}
