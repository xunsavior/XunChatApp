package com.example.xunhu.xunchat.Model;

import com.example.xunhu.xunchat.Model.AsyTasks.SendMessageTask;
import com.example.xunhu.xunchat.Model.Entities.Me;
import com.example.xunhu.xunchat.Model.Interfaces.CRUDSendMessageOptions;
import com.example.xunhu.xunchat.Presenter.Interfaces.SendMessageActionStatus;

/**
 * Created by xunhu on 7/20/2017.
 */

public class MySendMessageModel implements CRUDSendMessageOptions {
    SendMessageActionStatus sendMessageActionStatus;
    public MySendMessageModel(SendMessageActionStatus sendMessageActionStatus){
        this.sendMessageActionStatus=sendMessageActionStatus;
    }
    @Override
    public void attemptSendMessage(Me me, String targetUsername,int targetID,int messageType,
                                   String message,long timestamp) {
        int senderID = me.getId();
        String senderUsername = me.getUsername();
        String senderURL = me.getUrl();
        new SendMessageTask(sendMessageActionStatus).execute(String.valueOf(senderID),
                senderUsername,senderURL,targetUsername,String.valueOf(targetID),String.valueOf(messageType),message,String.valueOf(timestamp));
    }
}
