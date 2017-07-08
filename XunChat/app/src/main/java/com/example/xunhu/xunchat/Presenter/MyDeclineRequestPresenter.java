package com.example.xunhu.xunchat.Presenter;

import com.example.xunhu.xunchat.Model.UserDeclineRequestModel;
import com.example.xunhu.xunchat.Presenter.Interfaces.DeclineRequestAction;
import com.example.xunhu.xunchat.Presenter.Interfaces.DeclineRequestActionStatus;
import com.example.xunhu.xunchat.View.Interfaces.DeclineRequestView;

/**
 * Created by xunhu on 7/8/2017.
 */

public class MyDeclineRequestPresenter implements DeclineRequestAction,DeclineRequestActionStatus {
    DeclineRequestView declineRequestView;
    UserDeclineRequestModel userDeclineRequestModel;
    public MyDeclineRequestPresenter(DeclineRequestView declineRequestView){
        this.declineRequestView=declineRequestView;
        this.userDeclineRequestModel = new UserDeclineRequestModel(this);
    }

    @Override
    public void declineRequest(int myID, int targetID) {
        userDeclineRequestModel.operateDecline(myID,targetID);
    }

    @Override
    public void declineFail(String msg) {

    }

    @Override
    public void declineSuccessfully(String msg) {

    }
}
