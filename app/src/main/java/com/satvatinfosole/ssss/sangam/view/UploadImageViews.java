package com.satvatinfosole.ssss.sangam.view;

/**
 * Created by SATHISH on 11/17/2018.
 */
public interface UploadImageViews {
    void showProgress();
    void hideProgress();
    void  imageResponseSuccess(String response);
    void imageResponseError(String error);
}
