package com.example.xunhu.xunchat.Presenter.Interfaces;

/**
 * Created by xunhu on 7/20/2017.
 */

public interface SendMessageActionStatus {
    void sendMessageFail(long timestamp,String msg);
    void sendMessageSuccessful(long msg);
}
