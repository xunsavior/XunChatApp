package com.example.xunhu.xunchat.Presenter;

import com.example.xunhu.xunchat.Model.UserSearchFriendModel;
import com.example.xunhu.xunchat.Presenter.Interfaces.SearchFriendsActionStatus;
import com.example.xunhu.xunchat.Presenter.Interfaces.SearchFriendsPresenterAction;
import com.example.xunhu.xunchat.View.Interfaces.SearchFriendInterface;

/**
 * Created by xunhu on 6/21/2017.
 */

public class MySearchFriendPresenter implements SearchFriendsActionStatus,SearchFriendsPresenterAction {
    UserSearchFriendModel userSearchFriendModel;
    SearchFriendInterface searchFriendView;

    public MySearchFriendPresenter(SearchFriendInterface searchFriendView){
        this.searchFriendView=searchFriendView;
        userSearchFriendModel = new UserSearchFriendModel(this);
    }

    public SearchFriendInterface getSearchFriendView() {
        return searchFriendView;
    }

    @Override
    public void searchFriendsSuccess(String msg) {
        getSearchFriendView().loadResults(msg);
    }

    @Override
    public void searchFriendFail(String msg) {
        getSearchFriendView().searchFriendFail(msg);
    }

    @Override
    public void attemptSearchFriends(String nickname) {
        userSearchFriendModel.checkUserTable(nickname);
    }
}
