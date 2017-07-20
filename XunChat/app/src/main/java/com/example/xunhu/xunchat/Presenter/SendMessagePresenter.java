package com.example.xunhu.xunchat.Presenter;

import com.example.xunhu.xunchat.Model.Entities.Me;
import com.example.xunhu.xunchat.Model.MySendMessageModel;
import com.example.xunhu.xunchat.Presenter.Interfaces.SendMessageAction;
import com.example.xunhu.xunchat.Presenter.Interfaces.SendMessageActionStatus;
import com.example.xunhu.xunchat.View.Interfaces.SendChatView;

/**
 * Created by xunhu on 7/20/2017.
 */

public class SendMessagePresenter implements SendMessageAction,SendMessageActionStatus {
    SendChatView sendChatView;
    MySendMessageModel mySendMessageModel;
    public SendMessagePresenter(SendChatView sendChatView){
        this.sendChatView = sendChatView;
        mySendMessageModel = new MySendMessageModel(this);
    }
    @Override
    public void sendingMessage(Me me, String targetUsername,int targetID,int messageType,String message,long timestamp) {
        mySendMessageModel.attemptSendMessage(me,targetUsername,targetID,messageType,message,timestamp);
    }

    @Override
    public void sendMessageFail(long timestamp) {
        sendChatView.sendingMessageFail(timestamp);
    }

    @Override
    public void sendMessageSuccessful(String msg) {

    }
}
