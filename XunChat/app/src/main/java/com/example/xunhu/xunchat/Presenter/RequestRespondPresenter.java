package com.example.xunhu.xunchat.Presenter;

import com.example.xunhu.xunchat.Model.Entities.Me;
import com.example.xunhu.xunchat.Model.FriendRequestRespondModel;
import com.example.xunhu.xunchat.Presenter.Interfaces.FriendRespondActionStatus;
import com.example.xunhu.xunchat.Presenter.Interfaces.FriendRespondPresenterAction;
import com.example.xunhu.xunchat.View.Interfaces.RequestRespondView;

/**
 * Created by xunhu on 7/3/2017.
 */

public class RequestRespondPresenter implements FriendRespondActionStatus,FriendRespondPresenterAction {
    RequestRespondView requestRespondView;
    FriendRequestRespondModel friendRequestRespondModel;
    public RequestRespondPresenter(RequestRespondView requestRespondView){
        this.requestRespondView=requestRespondView;
        this.friendRequestRespondModel = new FriendRequestRespondModel(this);
    }

    public RequestRespondView getRequestRespondView() {
        return requestRespondView;
    }

    @Override
    public void respondSuccess(String msg) {
        getRequestRespondView().respondSuccess(msg);
    }

    @Override
    public void respondFail(String msg) {
        getRequestRespondView().respondFail(msg);
    }

    @Override
    public void sendRespond(int targetID, Me me,String remark) {
        friendRequestRespondModel.sendRequestRespond(targetID,me,remark);
    }
}
