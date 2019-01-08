package com.satvatinfosole.ssss.sangam.interactor;

import com.satvatinfosole.ssss.sangam.model.requestModel.AnswerQuizRequestModel;

/**
 * Created by SATHISH on 11/13/2018.
 */
public interface QuizInteractor {
    void getQuizQuestions(String StringDevoteeid);
    void postQuizAnswers(AnswerQuizRequestModel answerQuizRequestModel);

    void getScore(String StringDevoteeid);
    void getpreviousQuestions();
    void getTopRunners();
}
