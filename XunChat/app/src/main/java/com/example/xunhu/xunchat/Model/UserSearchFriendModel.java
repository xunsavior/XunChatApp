package com.example.xunhu.xunchat.Model;

import com.example.xunhu.xunchat.Model.AsyTasks.SearchFriendsTask;
import com.example.xunhu.xunchat.Model.Interfaces.CRUDSearchFriendsOptions;
import com.example.xunhu.xunchat.Presenter.Interfaces.SearchFriendsActionStatus;

/**
 * Created by xunhu on 6/21/2017.
 */

public class UserSearchFriendModel implements CRUDSearchFriendsOptions {
    SearchFriendsActionStatus searchFriendsActionStatus;

    public UserSearchFriendModel(SearchFriendsActionStatus searchFriendsActionStatus){
        this.searchFriendsActionStatus=searchFriendsActionStatus;
    }

    @Override
    public void checkUserTable(String username){
        new SearchFriendsTask(searchFriendsActionStatus).execute(username);
    }
}
