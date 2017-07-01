package com.example.xunhu.xunchat.Model.Entities;

/**
 * Created by xunhu on 6/30/2017.
 */

public class Request {
    String sender;
    String senderNickname;
    String senderAge;
    String senderGender;
    String senderRegion;
    String senderWhatsup;
    String extras;
    String isAgreed;
    String senderUrl;
    String isRead;

    public Request(String sender,String extras,String isAgreed,
                   String senderUrl,String isRead,String senderNickname,
                   String senderAge,String senderGender,
                   String senderRegion,String senderWhatsup){
        this.sender=sender;
        this.extras=extras;
        this.isAgreed=isAgreed;
        this.senderUrl=senderUrl;
        this.isRead=isRead;
        this.senderNickname=senderNickname;
        this.senderAge=senderAge;
        this.senderGender=senderGender;
        this.senderRegion=senderRegion;
        this.senderWhatsup=senderWhatsup;
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

    public String getSenderAge() {
        return senderAge;
    }

    public String getSenderGender() {
        return senderGender;
    }

    public String getSenderNickname() {
        return senderNickname;
    }

    public String getSenderRegion() {
        return senderRegion;
    }

    public String getSenderWhatsup() {
        return senderWhatsup;
    }

    public void setSenderAge(String senderAge) {
        this.senderAge = senderAge;
    }

    public void setSenderGender(String senderGender) {
        this.senderGender = senderGender;
    }

    public void setSenderNickname(String senderNickname) {
        this.senderNickname = senderNickname;
    }

    public void setSenderRegion(String senderRegion) {
        this.senderRegion = senderRegion;
    }

    public void setSenderWhatsup(String senderWhatsup) {
        this.senderWhatsup = senderWhatsup;
    }
}
