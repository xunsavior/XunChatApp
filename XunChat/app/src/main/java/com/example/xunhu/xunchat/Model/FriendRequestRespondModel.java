package com.example.xunhu.xunchat.Model;

import com.example.xunhu.xunchat.Model.AsyTasks.FriendRequestRespondTask;
import com.example.xunhu.xunchat.Model.Entities.Me;
import com.example.xunhu.xunchat.Model.Interfaces.CRUDRequestRespondOptions;
import com.example.xunhu.xunchat.Presenter.Interfaces.FriendRespondActionStatus;

/**
 * Created by xunhu on 7/3/2017.
 */

public class FriendRequestRespondModel implements CRUDRequestRespondOptions {
    FriendRespondActionStatus friendRespondActionStatus;
    public FriendRequestRespondModel(FriendRespondActionStatus friendRespondActionStatus){
        this.friendRespondActionStatus=friendRespondActionStatus;
    }
    @Override
    public void sendRequestRespond(String targetUsername, Me me) {
        String responder_username = me.getUsername();
        String responder_url = me.getUrl();
        String responder_gender = me.getUrl();
        String responder_region = me.getRegion();
        String responder_whatsup = me.getWhatsup();
        String responder_age = String.valueOf(me.getAge());
        String responder_nickname = me.getNickname();
        new FriendRequestRespondTask(friendRespondActionStatus).execute(targetUsername,
                responder_username,responder_url,responder_gender,responder_region,
                responder_whatsup, responder_age,responder_nickname);
    }
}
