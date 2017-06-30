package com.example.xunhu.xunchat.Presenter;

import com.example.xunhu.xunchat.Model.UserCookieValidateModel;
import com.example.xunhu.xunchat.Presenter.Interfaces.CookieValidateActionStatus;
import com.example.xunhu.xunchat.Presenter.Interfaces.CookieValidatePresenterAction;
import com.example.xunhu.xunchat.View.Interfaces.ValidateCookiesView;

/**
 * Created by xunhu on 6/12/2017.
 */

public class MyCookieValidationPresenter  implements CookieValidateActionStatus, CookieValidatePresenterAction{
    ValidateCookiesView validateCookiesView;
    UserCookieValidateModel userCookieValidateModel;

    public MyCookieValidationPresenter(ValidateCookiesView validateCookiesView){
        this.validateCookiesView = validateCookiesView;
        this.userCookieValidateModel = new UserCookieValidateModel(this);
    }
    public ValidateCookiesView getValidateCookiesView(){
        return this.validateCookiesView;
    }
    @Override
    public void validateSuccess(String msg) {
        getValidateCookiesView().switchToAccountLayout(msg);
    }

    @Override
    public void validateFail() {
        getValidateCookiesView().validateFail();
    }

    @Override
    public void validateTimeout() {
        getValidateCookiesView().validateTimeout();
    }

    @Override
    public void validateAttempt(String username, String password) {
        userCookieValidateModel.cookieCheck(username,password);
    }
}
