package com.example.xunhu.xunchat.Model.Entities;

import android.graphics.Bitmap;

/**
 * Created by xunhu on 7/18/2017.
 */

public class Message {
    String senderURL;
    int messageType;
    int from;
    String messageContent;
    String time;
    int isSuccess=1;
    String caption = "";
    public Message(String senderURL,int messageType,int from, String messageContent,String time){
        this.senderURL=senderURL;
        this.messageType=messageType;
        this.from=from;
        this.messageContent=messageContent;
        this.time =time;
    }
    public int getIsSentSuccess(){
        return this.isSuccess;
    }
    public void setIsSentSuccess(int isSuccess){
        this.isSuccess=isSuccess;
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

    public String getSenderURL() {
        return senderURL;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setSenderURL(String senderURL) {
        this.senderURL = senderURL;
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

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getCaption() {
        return caption;
    }
}
