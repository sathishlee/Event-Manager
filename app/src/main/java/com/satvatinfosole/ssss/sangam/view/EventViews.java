package com.satvatinfosole.ssss.sangam.view;

/**
 * Created by SATHISH on 10/24/2018.
 */
public interface EventViews {
    void showProgress();
    void hideProgress();
    void showEventSuccess(String responseSuccess);
    void showEventError(String responseError);
}
