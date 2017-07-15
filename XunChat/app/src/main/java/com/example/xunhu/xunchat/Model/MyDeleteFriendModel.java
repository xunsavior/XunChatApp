package com.example.xunhu.xunchat.Model;

import com.example.xunhu.xunchat.Model.AsyTasks.DeleteFriendTask;
import com.example.xunhu.xunchat.Model.Interfaces.CRUDDeleteFriendOptions;
import com.example.xunhu.xunchat.Presenter.Interfaces.DeleteFriendActionStatus;

/**
 * Created by xunhu on 7/14/2017.
 */

public class MyDeleteFriendModel implements CRUDDeleteFriendOptions {
    DeleteFriendActionStatus deleteFriendActionStatus;
    public MyDeleteFriendModel(DeleteFriendActionStatus deleteFriendActionStatus){
        this.deleteFriendActionStatus=deleteFriendActionStatus;
    }
    @Override
    public void performDelete(int myID, int friendID) {
        new DeleteFriendTask(deleteFriendActionStatus).execute(myID,friendID);
    }
}
