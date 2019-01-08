package com.satvatinfosole.ssss.sangam.model.responseModel;

import java.util.List;

/**
 * Created by SATHISH on 12/21/2018.
 */
public class PreviousQuizResponseModel {

    public List<Previous_questions> getPrevious_questions() {
        return previous_questions;
    }

    public void setPrevious_questions(List<Previous_questions> previous_questions) {
        this.previous_questions = previous_questions;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private List<Previous_questions> previous_questions;
    private String message;
    private String status;

    public static class Previous_questions {
        public String getCh_sno() {
            return ch_sno;
        }

        public void setCh_sno(String ch_sno) {
            this.ch_sno = ch_sno;
        }

        private String ch_sno;
        private String ch_answer;
        private String ch_question;

        public String getCh_answer() {
            return ch_answer;
        }

        public void setCh_answer(String ch_answer) {
            this.ch_answer = ch_answer;
        }

        public String getCh_question() {
            return ch_question;
        }

        public void setCh_question(String ch_question) {
            this.ch_question = ch_question;
        }
    }
}
