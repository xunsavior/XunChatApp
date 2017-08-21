package com.example.xunhu.xunchat.Model.Interfaces;

/**
 * Created by xunhu on 8/20/2017.
 */

public interface CRUDLikePostOptions {
    void likePost(int postID, int userID);
    void cancelDislikedPost(int postID, int userID);
}
