package com.example.xunhu.xunchat.Model.Interfaces;

/**
 * Created by xunhu on 8/14/2017.
 */

public interface CURDPostOptions {
    void attemptPost(int userID, int postType,String postContent,String timestamp,String location);
}
