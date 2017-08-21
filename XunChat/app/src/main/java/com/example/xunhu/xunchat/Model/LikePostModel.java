package com.example.xunhu.xunchat.Model;

import com.example.xunhu.xunchat.Model.AsyTasks.LikePostTask;
import com.example.xunhu.xunchat.Model.Interfaces.CRUDLikePostOptions;
import com.example.xunhu.xunchat.Presenter.Interfaces.LikePostActionStatus;

/**
 * Created by xunhu on 8/20/2017.
 */

public class LikePostModel implements CRUDLikePostOptions {
    LikePostActionStatus likePostActionStatus;
    public LikePostModel(LikePostActionStatus likePostActionStatus){
        this.likePostActionStatus=likePostActionStatus;
    }
    @Override
    public void likePost(int postID, int userID) {
        new LikePostTask(likePostActionStatus).execute(postID,userID,0);
    }
    @Override
    public void cancelDislikedPost(int postID, int userID) {
        new LikePostTask(likePostActionStatus).execute(postID,userID,1);
    }
}
