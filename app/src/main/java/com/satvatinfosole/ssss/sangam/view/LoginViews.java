package com.satvatinfosole.ssss.sangam.view;

/**
 * Created by Suthishan on 20/1/2018.
 */

public interface LoginViews {
    void showProgress();
    void hideProgress();
    void showLoginSuccess(String responseSuccess);
    void showLoginError(String responseError);

}
