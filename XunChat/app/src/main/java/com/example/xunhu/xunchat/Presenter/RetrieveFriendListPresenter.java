package com.example.xunhu.xunchat.Presenter;

import com.example.xunhu.xunchat.Model.UserRetrieveFriendListModel;
import com.example.xunhu.xunchat.Presenter.Interfaces.RetrieveFriendListAction;
import com.example.xunhu.xunchat.Presenter.Interfaces.RetrieveFriendListActionStatus;
import com.example.xunhu.xunchat.View.Interfaces.RetrieveFriendListView;

/**
 * Created by xunhu on 7/13/2017.
 */

public class RetrieveFriendListPresenter implements RetrieveFriendListAction,RetrieveFriendListActionStatus {
    RetrieveFriendListView retrieveFriendListView;
    UserRetrieveFriendListModel userRetrieveFriendListModel;
    public RetrieveFriendListPresenter(RetrieveFriendListView retrieveFriendListView){
        this.retrieveFriendListView=retrieveFriendListView;
        userRetrieveFriendListModel = new UserRetrieveFriendListModel(this);
    }

    @Override
    public void retrieveFriendList(int myID) {
        userRetrieveFriendListModel.attemptRetrieveFriendList(myID);
    }

    @Override
    public void retrieveFriendListSuccessful(String msg) {
        retrieveFriendListView.onRetrieveFriendListSuccessful(msg);
    }

    @Override
    public void retrieveFriendListFail(String msg) {
        retrieveFriendListView.onRetrieveFriendListFail(msg);
    }
}
