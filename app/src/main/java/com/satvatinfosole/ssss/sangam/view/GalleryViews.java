package com.satvatinfosole.ssss.sangam.view;

/**
 * Created by SATHISH on 10/30/2018.
 */
public interface GalleryViews {
    void showProgress();
    void hideProgress();
    void showGallerySuccess(String responseSuccess);
    void showGalleryError(String responseError);

    void getthumbnailSuccess(String responseSuccess);
    void getthumbnailError(String responseError);
}
