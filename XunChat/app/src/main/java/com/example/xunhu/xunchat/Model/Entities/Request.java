package com.example.xunhu.xunchat.Model.Entities;

/**
 * Created by xunhu on 6/30/2017.
 */

public class Request {
    int senderID;
    String senderName;
    String senderNickname;
    String extras;
    String isAgreed;
    String senderUrl;
    String isRead;

    public Request(int senderID,String senderName,String senderNickname,String extras,
                   String isAgreed,String senderUrl,String isRead){
        this.senderID=senderID;
        this.senderName=senderName;
        this.senderNickname = senderNickname;
        this.extras=extras;
        this.isAgreed=isAgreed;
        this.senderUrl=senderUrl;
        this.isRead=isRead;
    }

    public String getSenderNickname() {
        return senderNickname;
    }

    public void setSenderNickname(String senderNickname) {
        this.senderNickname = senderNickname;
    }

    public String getExtras() {
        return extras;
    }
    public String getIsAgreed() {
        return isAgreed;
    }
    public void setExtras(String extras) {
        this.extras = extras;
    }
    public void setIsAgreed(String isAgreed) {
        this.isAgreed = isAgreed;
    }
    public String getSenderUrl() {
        return senderUrl;
    }
    public void setSenderUrl(String senderUrl) {
        this.senderUrl = senderUrl;
    }
    public String getIsRead() {
        return isRead;
    }
    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }
    public String getSenderName() {
        return senderName;
    }
    public int getSenderID() {
        return senderID;
    }
    public void setSenderID(int senderID) {
        this.senderID = senderID;
    }
    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }
}
