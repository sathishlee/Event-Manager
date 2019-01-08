package com.satvatinfosole.ssss.sangam.view;

/**
 * Created by SATHISH on 11/15/2018.
 */
public interface FeedbackViews {
    void showProgress();
    void hideProgress();

    void postFeedbackSuccess(String response);
    void postFeedbackError(String error);
}
