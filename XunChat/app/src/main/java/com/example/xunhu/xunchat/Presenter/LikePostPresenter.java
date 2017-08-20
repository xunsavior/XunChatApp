package com.example.xunhu.xunchat.Presenter;

import com.example.xunhu.xunchat.Model.LikePostModel;
import com.example.xunhu.xunchat.Presenter.Interfaces.LikePostAction;
import com.example.xunhu.xunchat.Presenter.Interfaces.LikePostActionStatus;
import com.example.xunhu.xunchat.View.Interfaces.LikePostView;

/**
 * Created by xunhu on 8/20/2017.
 */

public class LikePostPresenter implements LikePostAction,LikePostActionStatus {
    LikePostView likePostView;
    LikePostModel likePostModel;
    public LikePostPresenter(LikePostView likePostView){
        this.likePostView=likePostView;
        likePostModel = new LikePostModel(this);
    }
    @Override
    public void likePost(int postID, int userID) {
        likePostModel.likePost(postID,userID);
    }

    @Override
    public void likedSuccess(String msg, int postID) {
        likePostView.likeSuccess(msg,postID);
    }

    @Override
    public void likedFail(String msg) {
        likePostView.likedFail(msg);
    }
}
