package com.satvatinfosole.ssss.sangam.view;

/**
 * Created by Suthishan on 20/1/2018.
 */

public interface RegistrationViews {
    void showProgress();

    void hideProgress();

    void showRegistrationSuccess(String responseSuccess);

    void shoRegistrationError(String responseError);


    void getRequestEmailSuccess(String responseSuccess);

    void getRequestEmailError(String responseSuccess);

    void getRequestStatusSuccess(String responseSuccess);

    void getRequestStatusError(String responseSuccess);


}
