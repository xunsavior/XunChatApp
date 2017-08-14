package com.example.xunhu.xunchat.Presenter.Interfaces;

/**
 * Created by xunhu on 8/14/2017.
 */

public interface PostAction {
    void operatePost(int userID, int postType,String postContent,String timestamp,String location);
}
