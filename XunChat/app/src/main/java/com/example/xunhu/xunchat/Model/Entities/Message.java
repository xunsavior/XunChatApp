package com.example.xunhu.xunchat.Model.Entities;

/**
 * Created by xunhu on 7/18/2017.
 */

public class Message {
    String friendURL;
    int messageType;
    int from;
    String messageContent;
    String time;

    public Message(String friendURL,int messageType,int from, String messageContent,String time){
        this.friendURL=friendURL;
        this.messageType=messageType;
        this.from=from;
        this.messageContent=messageContent;
        this.time =time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getFrom() {
        return from;
    }

    public int getMessageType() {
        return messageType;
    }

    public String getFriendURL() {
        return friendURL;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setFriendURL(String friendURL) {
        this.friendURL = friendURL;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }
}
