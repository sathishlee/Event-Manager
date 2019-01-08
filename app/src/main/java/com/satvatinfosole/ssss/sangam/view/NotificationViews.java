package com.satvatinfosole.ssss.sangam.view;

/**
 * Created by SATHISH on 11/23/2018.
 */
public interface NotificationViews {
    void showProgress();
    void hideProgress();
    void getNotificationListSuccess(String responseSuccess);
    void getNotificationListError(String responseError);
}
