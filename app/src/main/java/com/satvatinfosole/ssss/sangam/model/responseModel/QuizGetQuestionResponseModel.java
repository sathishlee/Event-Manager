package com.satvatinfosole.ssss.sangam.model.responseModel;

import java.util.List;

/**
 * Created by SATHISH on 11/13/2018.
 */
public class QuizGetQuestionResponseModel {
    public List<Quiz_Questions_List> Quiz_Questions_List;
    public String message;
    public String status;

    public List<QuizGetQuestionResponseModel.Quiz_Questions_List> getQuiz_Questions_List() {
        return Quiz_Questions_List;
    }

    public void setQuiz_Questions_List(List<QuizGetQuestionResponseModel.Quiz_Questions_List> quiz_Questions_List) {
        Quiz_Questions_List = quiz_Questions_List;
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



    public static class Quiz_Questions_List {
        private String ch_qid;
        private String ch_answer;
        private String ch_question;
        private String ch_id;
        private String qstatus;
        private String qend_date;
        private String qstart_date;
        private String qtitle;
        private String qid;

        public String getCh_qid() {
            return ch_qid;
        }

        public void setCh_qid(String ch_qid) {
            this.ch_qid = ch_qid;
        }

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

        public String getCh_id() {
            return ch_id;
        }

        public void setCh_id(String ch_id) {
            this.ch_id = ch_id;
        }

        public String getQstatus() {
            return qstatus;
        }

        public void setQstatus(String qstatus) {
            this.qstatus = qstatus;
        }

        public String getQend_date() {
            return qend_date;
        }

        public void setQend_date(String qend_date) {
            this.qend_date = qend_date;
        }

        public String getQstart_date() {
            return qstart_date;
        }

        public void setQstart_date(String qstart_date) {
            this.qstart_date = qstart_date;
        }

        public String getQtitle() {
            return qtitle;
        }

        public void setQtitle(String qtitle) {
            this.qtitle = qtitle;
        }

        public String getQid() {
            return qid;
        }

        public void setQid(String qid) {
            this.qid = qid;
        }
    }
}
