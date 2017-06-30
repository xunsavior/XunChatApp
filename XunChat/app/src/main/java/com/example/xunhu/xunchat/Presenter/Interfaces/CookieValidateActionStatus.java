package com.example.xunhu.xunchat.Presenter.Interfaces;

/**
 * Created by xunhu on 6/12/2017.
 */

public interface CookieValidateActionStatus {
    void validateSuccess(String msg);
    void validateFail();
    void validateTimeout();
}
