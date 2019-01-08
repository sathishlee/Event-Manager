package com.satvatinfosole.ssss.sangam.view;

/**
 * Created by SATHISH on 10/23/2018.
 */
public interface DashbordViews {
    void showProgress();
    void hideProgress();

    void getResponseDashbordSuccess(String responseSuccess);
    void getResponseDashboardFailiur(String responseError);

    void getResponseFlashNewsSuccess(String responseSuccess);
    void getResponseFlashNewsFailiur(String responseError);
}
