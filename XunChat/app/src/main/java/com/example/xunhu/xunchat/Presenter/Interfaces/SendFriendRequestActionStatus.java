package com.example.xunhu.xunchat.Presenter.Interfaces;

/**
 * Created by xunhu on 6/23/2017.
 */

public interface SendFriendRequestActionStatus {
    void onSentSuccess(String msg);
    void onSentFail(String msg);
}
