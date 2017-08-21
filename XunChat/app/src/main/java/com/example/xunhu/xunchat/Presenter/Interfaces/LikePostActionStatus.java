package com.example.xunhu.xunchat.Presenter.Interfaces;

/**
 * Created by xunhu on 8/20/2017.
 */

public interface LikePostActionStatus {
    void likedSuccess(String msg, int postID,int type);
    void likedFail(String msg);
}
