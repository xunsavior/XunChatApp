package com.example.xunhu.xunchat.Model.Entities;

import java.io.Serializable;

/**
 * Created by xunhu on 7/20/2017.
 */

public class LatestMessage implements Serializable {
    int friendID;
    String friendUsername;
    String friendNickname;
    String friendURL;
    String latestMessage;
    String timestamp;
    int messageType;
    int unread;

    public LatestMessage(int friendID,String friendUsername,String friendNickname,String friendURL,
                         String latestMessage,String timestamp, int messageType,int unread){
        this.friendID=friendID;
        this.friendUsername=friendUsername;
        this.friendNickname=friendNickname;
        this.friendURL=friendURL;
        this.latestMessage=latestMessage;
        this.timestamp=timestamp;
        this.messageType=messageType;
        this.unread=unread;
    }

    public int getUnread() {
        return unread;
    }

    public void setUnread(int unread) {
        this.unread = unread;
    }
    public String getFriendURL() {
        return friendURL;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public int getMessageType() {
        return messageType;
    }

    public int getFriendID() {
        return friendID;
    }

    public String getFriendNickname() {
        return friendNickname;
    }

    public String getFriendUsername() {
        return friendUsername;
    }

    public String getLatestMessage() {
        return latestMessage;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setFriendURL(String friendURL) {
        this.friendURL = friendURL;
    }

    public void setFriendID(int friendID) {
        this.friendID = friendID;
    }

    public void setFriendNickname(String friendNickname) {
        this.friendNickname = friendNickname;
    }

    public void setFriendUsername(String friendUsername) {
        this.friendUsername = friendUsername;
    }

    public void setLatestMessage(String latestMessage) {
        this.latestMessage = latestMessage;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
