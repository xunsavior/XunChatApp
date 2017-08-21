package com.example.xunhu.xunchat.Model.Entities;

/**
 * Created by xunhu on 8/21/2017.
 */

public class WhoLikePost {
    int userID;
    String username;
    String nickname;
    String url;
    String timestamp;

    public WhoLikePost(int userID,String username,String nickname,String url,String timestamp){
        this.userID=userID;
        this.username=username;
        this.nickname=nickname;
        this.url=url;
        this.timestamp=timestamp;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getNickname() {
        return nickname;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public int getUserID() {
        return userID;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
