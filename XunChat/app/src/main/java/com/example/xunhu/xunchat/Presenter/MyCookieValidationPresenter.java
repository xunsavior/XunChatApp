package com.example.xunhu.xunchat.Presenter;

import android.content.Context;

import com.example.xunhu.xunchat.Model.UserCookieValidateModel;
import com.example.xunhu.xunchat.Presenter.Interfaces.CookieValidateActionStatus;
import com.example.xunhu.xunchat.Presenter.Interfaces.CookieValidatePresenterAction;
import com.example.xunhu.xunchat.View.Interfaces.ValidateCookiesView;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

/**
 * Created by xunhu on 6/12/2017.
 */
@EBean
public class MyCookieValidationPresenter  implements CookieValidateActionStatus, CookieValidatePresenterAction{
    ValidateCookiesView validateCookiesView;
    @Bean UserCookieValidateModel userCookieValidateModel;

    public MyCookieValidationPresenter(Context context){
        this.userCookieValidateModel = new UserCookieValidateModel(context);
        userCookieValidateModel.setCookieValidateActionStatus(this);
    }
    public void setValidateCookiesView(ValidateCookiesView validateCookiesView) {
        this.validateCookiesView = validateCookiesView;
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
