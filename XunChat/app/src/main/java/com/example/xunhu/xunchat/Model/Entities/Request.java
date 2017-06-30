package com.example.xunhu.xunchat.Model.Entities;

/**
 * Created by xunhu on 6/30/2017.
 */

public class Request {
    String sender;
    String extras;
    String isAgreed;
    String senderUrl;
    String isRead;

    public Request(String sender,String extras,String isAgreed,String senderUrl,String isRead){
        this.sender=sender;
        this.extras=extras;
        this.isAgreed=isAgreed;
        this.senderUrl=senderUrl;
        this.isRead=isRead;
    }

    public String getExtras() {
        return extras;
    }
    public String getIsAgreed() {
        return isAgreed;
    }
    public String getSender() {
        return sender;
    }
    public void setExtras(String extras) {
        this.extras = extras;
    }
    public void setIsAgreed(String isAgreed) {
        this.isAgreed = isAgreed;
    }
    public void setSender(String sender) {
        this.sender = sender;
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
}
