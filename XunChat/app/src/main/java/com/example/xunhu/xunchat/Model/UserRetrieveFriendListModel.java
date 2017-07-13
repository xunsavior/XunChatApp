package com.example.xunhu.xunchat.Model;

import com.example.xunhu.xunchat.Model.AsyTasks.RetrieveFriendListTask;
import com.example.xunhu.xunchat.Model.Interfaces.CRUDRetrieveFriendListOptions;
import com.example.xunhu.xunchat.Presenter.Interfaces.RetrieveFriendListActionStatus;

/**
 * Created by xunhu on 7/13/2017.
 */

public class UserRetrieveFriendListModel implements CRUDRetrieveFriendListOptions {
    RetrieveFriendListActionStatus retrieveFriendListActionStatus;

    public UserRetrieveFriendListModel(RetrieveFriendListActionStatus retrieveFriendListActionStatus){
        this.retrieveFriendListActionStatus=retrieveFriendListActionStatus;
    }

    @Override
    public void attemptRetrieveFriendList(int myID) {
        new RetrieveFriendListTask(retrieveFriendListActionStatus).execute(myID);
    }
}
