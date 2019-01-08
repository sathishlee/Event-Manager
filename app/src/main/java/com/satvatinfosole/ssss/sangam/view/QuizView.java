package com.satvatinfosole.ssss.sangam.view;

/**
 * Created by SATHISH on 11/13/2018.
 */
public interface QuizView {
    void showProgress();
    void hideProgress();
    void getQuestionSuccess(String response);
    void getQuestionError(String error);
    void postQuestionSuccess(String response);
    void postQuestionError(String error);

     void getScoreSuccess(String response);
     void getScoreError(String response);

    void getPreviousQuestionSuccess(String response);
    void getPreviousQuestionError(String error);

    void getTopRunnerSuccess(String response);
    void getTopRunnerError(String error);
}
