package com.example.xunhu.xunchat.Model;

import com.example.xunhu.xunchat.Model.AsyTasks.SendFriendRequestTask;
import com.example.xunhu.xunchat.Model.Entities.Me;
import com.example.xunhu.xunchat.Model.Entities.User;
import com.example.xunhu.xunchat.Model.Interfaces.CRUDSendFriendRequestOptions;
import com.example.xunhu.xunchat.Presenter.Interfaces.SendFriendRequestAction;
import com.example.xunhu.xunchat.Presenter.Interfaces.SendFriendRequestActionStatus;

/**
 * Created by xunhu on 6/23/2017.
 */
public class SendFriendRequestModel implements CRUDSendFriendRequestOptions {
    SendFriendRequestActionStatus sendFriendRequestActionStatus;

    public SendFriendRequestModel(SendFriendRequestActionStatus sendFriendRequestActionStatus){
        this.sendFriendRequestActionStatus=sendFriendRequestActionStatus;
    }
    @Override
    public void sendFriendRequest(Me me, String extras,User user) {
        new SendFriendRequestTask(sendFriendRequestActionStatus).execute(
                String.valueOf(me.getId()),
                me.getUsername(),
                me.getNickname(),
                String.valueOf(me.getAge()),
                me.getGender(),
                me.getRegion(),
                me.getUrl(),
                me.getWhatsup(),
                String.valueOf(user.getUserID()),
                extras
        );
    }
}
