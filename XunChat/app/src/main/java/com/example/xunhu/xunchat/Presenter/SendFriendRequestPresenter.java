package com.example.xunhu.xunchat.Presenter;

import com.example.xunhu.xunchat.Model.Entities.Me;
import com.example.xunhu.xunchat.Model.Entities.User;
import com.example.xunhu.xunchat.Model.SendFriendRequestModel;
import com.example.xunhu.xunchat.Presenter.Interfaces.SendFriendRequestAction;
import com.example.xunhu.xunchat.Presenter.Interfaces.SendFriendRequestActionStatus;
import com.example.xunhu.xunchat.View.Interfaces.SendFriendRequestView;

/**
 * Created by xunhu on 6/23/2017.
 */

public class SendFriendRequestPresenter implements SendFriendRequestAction,SendFriendRequestActionStatus {
    SendFriendRequestView sendFriendRequestView;
    SendFriendRequestModel sendFriendRequestModel;
    public SendFriendRequestPresenter(SendFriendRequestView sendFriendRequestView){
        this.sendFriendRequestView=sendFriendRequestView;
        this.sendFriendRequestModel = new SendFriendRequestModel(this);
    }

    public SendFriendRequestView getSendFriendRequestView() {
        return sendFriendRequestView;
    }

    @Override
    public void onSentSuccess(String msg) {
        getSendFriendRequestView().friendRequestSent(msg);
    }

    @Override
    public void onSentFail(String msg) {
        getSendFriendRequestView().friendRequestFailtoSend(msg);
    }

    @Override
    public void sendFriendRequest(Me me, String extras, User user) {
        sendFriendRequestModel.sendFriendRequest(me,extras,user);
    }
}
