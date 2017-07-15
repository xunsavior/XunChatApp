package com.example.xunhu.xunchat.Presenter;

import com.example.xunhu.xunchat.Model.MyDeleteFriendModel;
import com.example.xunhu.xunchat.Presenter.Interfaces.DeleteFriendAction;
import com.example.xunhu.xunchat.Presenter.Interfaces.DeleteFriendActionStatus;
import com.example.xunhu.xunchat.View.Interfaces.DeleteFriendView;

/**
 * Created by xunhu on 7/14/2017.
 */

public class DeleteFriendActionPresenter implements DeleteFriendAction,DeleteFriendActionStatus {
    DeleteFriendView deleteFriendView;
    MyDeleteFriendModel myDeleteFriendModel;
    public DeleteFriendActionPresenter(DeleteFriendView deleteFriendView){
        this.deleteFriendView=deleteFriendView;
        myDeleteFriendModel = new MyDeleteFriendModel(this);
    }
    @Override
    public void deleteFriend(int myID, int myFriend) {
        myDeleteFriendModel.performDelete(myID,myFriend);
    }

    @Override
    public void deleteSuccessful(String msg) {
        deleteFriendView.deleteSuccessful(msg);
    }

    @Override
    public void deleteFail(String msg) {
        deleteFriendView.deleteFail(msg);
    }
}
