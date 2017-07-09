package com.example.xunhu.xunchat.Model.Entities;

/**
 * Created by xunhu on 7/9/2017.
 */

public class Friend {
    int ID;
    String username;
    String nickname;
    String url;
    boolean firstOrNot;

    public Friend(int ID, String username,String nickname,String url,boolean firstOrNot){
        this.ID=ID;
        this.username=username;
        this.nickname=nickname;
        this.url=url;
        this.firstOrNot=firstOrNot;
    }
    public boolean getFirstOrNot(){
        return firstOrNot;
    }
    public void setFirstOrNot(boolean firstOrNot) {
        this.firstOrNot = firstOrNot;
    }

    public String getUsername() {
        return username;
    }

    public String getUrl() {
        return url;
    }

    public String getNickname() {
        return nickname;
    }

    public int getID() {
        return ID;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

}
