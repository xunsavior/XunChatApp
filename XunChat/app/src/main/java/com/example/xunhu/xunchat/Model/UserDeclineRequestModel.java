package com.example.xunhu.xunchat.Model;

import com.example.xunhu.xunchat.Model.AsyTasks.DeclineRequestTask;
import com.example.xunhu.xunchat.Model.Interfaces.CRUDDeclineRequestOptions;
import com.example.xunhu.xunchat.Presenter.Interfaces.DeclineRequestActionStatus;
import com.example.xunhu.xunchat.Presenter.MyDeclineRequestPresenter;

/**
 * Created by xunhu on 7/8/2017.
 */

public class UserDeclineRequestModel implements CRUDDeclineRequestOptions{
    DeclineRequestActionStatus declineRequestActionStatus;

    public UserDeclineRequestModel(DeclineRequestActionStatus declineRequestActionStatus){
        this.declineRequestActionStatus=declineRequestActionStatus;
    }
    @Override
    public void operateDecline(int myID, int targetID) {
        new DeclineRequestTask(declineRequestActionStatus).execute(myID,targetID);
    }
}
