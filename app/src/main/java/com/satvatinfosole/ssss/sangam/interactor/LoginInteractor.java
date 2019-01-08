package com.satvatinfosole.ssss.sangam.interactor;

/**
 * Created by SATHISH on 10/16/2018.
 */
public interface LoginInteractor {
    void doLogIn(String strUserName,String strPassword,String strDeviceToken);
    void getEmail(String strKey);
    void getKey(String strEmail);
}
