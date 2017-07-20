package com.example.xunhu.xunchat.View.Interfaces;

/**
 * Created by xunhu on 7/20/2017.
 */

public interface SendChatView {
    void sendingMessageFail(long timestamp,String msg);
    void sendingMessageSuccessful(long timestamp);
}
