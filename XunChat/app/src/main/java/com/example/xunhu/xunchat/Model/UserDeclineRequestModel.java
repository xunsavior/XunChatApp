package com.example.xunhu.xunchat.Model;

import com.example.xunhu.xunchat.Model.AsyTasks.DeclineRequestTask;
import com.example.xunhu.xunchat.Model.Interfaces.CRUDDeclineRequestOptions;
import com.example.xunhu.xunchat.Presenter.MyDeclineRequestPresenter;

/**
 * Created by xunhu on 7/8/2017.
 */

public class UserDeclineRequestModel implements CRUDDeclineRequestOptions{
    MyDeclineRequestPresenter myDeclineRequestPresenter;

    public UserDeclineRequestModel(MyDeclineRequestPresenter myDeclineRequestPresenter){
        this.myDeclineRequestPresenter=myDeclineRequestPresenter;
    }
    @Override
    public void operateDecline(int myID, int targetID) {
        new DeclineRequestTask(this).execute(myID,targetID);
    }
}
