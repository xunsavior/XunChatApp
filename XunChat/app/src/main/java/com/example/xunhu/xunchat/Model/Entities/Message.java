package com.example.xunhu.xunchat.Model.Entities;

/**
 * Created by xunhu on 7/18/2017.
 */

public class Message {
    String senderURL;
    int messageType;
    int from;
    String messageContent;
    String time;
    int isError=1;

    public Message(String senderURL,int messageType,int from, String messageContent,String time){
        this.senderURL=senderURL;
        this.messageType=messageType;
        this.from=from;
        this.messageContent=messageContent;
        this.time =time;
    }
    public int getIsSentSuccess(){
        return this.isError;
    }
    public void setIsSentSuccess(int isError){
        this.isError=isError;
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
}
