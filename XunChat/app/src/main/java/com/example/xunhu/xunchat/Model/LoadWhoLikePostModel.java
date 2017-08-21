package com.example.xunhu.xunchat.Model;

import com.example.xunhu.xunchat.Model.AsyTasks.LoadWhoLikePostTask;
import com.example.xunhu.xunchat.Model.Interfaces.CRUDLoadWhoLikePostOptions;
import com.example.xunhu.xunchat.Presenter.Interfaces.LoadWhoLikePostActionStatus;

/**
 * Created by xunhu on 8/21/2017.
 */

public class LoadWhoLikePostModel implements CRUDLoadWhoLikePostOptions {
    LoadWhoLikePostActionStatus loadWhoLikePostActionStatus;

    public LoadWhoLikePostModel(LoadWhoLikePostActionStatus loadWhoLikePostActionStatus){
        this.loadWhoLikePostActionStatus=loadWhoLikePostActionStatus;
    }
    @Override
    public void loadWhoLikePost(int postID) {
        new LoadWhoLikePostTask(loadWhoLikePostActionStatus).execute(postID);
    }
}
