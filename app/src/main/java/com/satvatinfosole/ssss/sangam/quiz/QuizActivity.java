package com.satvatinfosole.ssss.sangam.quiz;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.satvatinfosole.ssss.sangam.DailogInterface;
import com.satvatinfosole.ssss.sangam.R;
import com.satvatinfosole.ssss.sangam.adapter.QuizAdapter;
import com.satvatinfosole.ssss.sangam.dashboard.DashBoardActivity;
import com.satvatinfosole.ssss.sangam.model.requestModel.AnswerQuizRequestModel;
import com.satvatinfosole.ssss.sangam.model.responseModel.QuizGetQuestionResponseModel;
import com.satvatinfosole.ssss.sangam.presenter.QuizPresenter;
import com.satvatinfosole.ssss.sangam.sharedpreference.PreferenceData;
import com.satvatinfosole.ssss.sangam.signup.UserLoginDetailsActivity;
import com.satvatinfosole.ssss.sangam.utility.CheckNetwork;
import com.satvatinfosole.ssss.sangam.utility.CustomDialogBox;
import com.satvatinfosole.ssss.sangam.view.QuizView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class QuizActivity extends AppCompatActivity implements QuizView, View.OnClickListener, DailogInterface {

    public ImageView img_back_press;
    public TextView txt_quiz_title, txt_quiz_duration;
    RecyclerView recv_quiz_list;
    String strQuestionBankId = "";
    QuizAdapter quizAdapter;

    //Api call
    ProgressDialog pDialog;
    CheckNetwork checkNetwork;
    PreferenceData preferenceData;
    QuizPresenter quizPresenter;

    JSONArray jObj_quiz_array = null;
    //    ArrayList<String> questionList;
    ArrayList<QuizGetQuestionResponseModel.Quiz_Questions_List> questionList;
    QuizGetQuestionResponseModel.Quiz_Questions_List quizGetQuestionResponseModel;
    Button but_submit;

    CustomDialogBox customDialogBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        intiUi();
        onClickListner();
    }

    private void onClickListner() {
        but_submit.setOnClickListener(this);
        img_back_press.setOnClickListener(this);
    }

    private void intiUi() {
        checkNetwork = new CheckNetwork(this);
        preferenceData = new PreferenceData(this);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Please Wait ...");

        questionList = new ArrayList<>();

        quizPresenter = new QuizPresenter(QuizActivity.this, QuizActivity.this);

        if (checkNetwork.isNetworkAvailable()) {
            quizPresenter.getQuizQuestions(preferenceData.getDevoteeId());
        } else {
            customDialogBox = new CustomDialogBox(QuizActivity.this, this,
                    "You are in Offline", "please check your internat connection",
                    false, "OK", "CANCEL");
            customDialogBox.show();
        }


        img_back_press = (ImageView) findViewById(R.id.img_back_press);
        txt_quiz_title = (TextView) findViewById(R.id.txt_quiz_title);
        txt_quiz_duration = (TextView) findViewById(R.id.txt_quiz_duration);
        but_submit = (Button) findViewById(R.id.but_submit);
        recv_quiz_list = (RecyclerView) findViewById(R.id.recv_quiz_list);
        quizAdapter = new QuizAdapter(questionList, QuizActivity.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(QuizActivity.this);
        recv_quiz_list.setLayoutManager(mLayoutManager);
        recv_quiz_list.setItemAnimator(new DefaultItemAnimator());
        recv_quiz_list.setAdapter(quizAdapter);
    }


    @Override
    public void showProgress() {
             pDialog.show();
    }

    @Override
    public void hideProgress() {
        pDialog.hide();
    }

    @Override
    public void getQuestionSuccess(String response) {

        try {
            JSONObject mJsnobject = new JSONObject(response);
            String status = mJsnobject.getString("status");
            String message = mJsnobject.getString("message");
            if (status.equalsIgnoreCase("1")) {
                JSONArray jsonArray = mJsnobject.getJSONArray("Quiz_Questions_List");
                Log.e("Question list size ->", jsonArray.length() + "");

                JSONObject jsonObject = null;
                if (jsonArray.length() != 0) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject = jsonArray.getJSONObject(i);
                        Log.e("Question ->", i + jsonObject.toString());
                        quizGetQuestionResponseModel = new QuizGetQuestionResponseModel.Quiz_Questions_List();
                        quizGetQuestionResponseModel.setQid(jsonObject.getString("qid"));
                        quizGetQuestionResponseModel.setQtitle(jsonObject.getString("qtitle"));
                        quizGetQuestionResponseModel.setQstart_date(jsonObject.getString("qstart_date"));
                        quizGetQuestionResponseModel.setQend_date(jsonObject.getString("qend_date"));
                        quizGetQuestionResponseModel.setQstatus(jsonObject.getString("qstatus"));
                        quizGetQuestionResponseModel.setCh_id(jsonObject.getString("ch_id"));
                        quizGetQuestionResponseModel.setCh_question(jsonObject.getString("ch_question"));
//                        quizGetQuestionResponseModel.setEvent_image(jsonObject.getString("ch_answer"));
                        quizGetQuestionResponseModel.setCh_qid(jsonObject.getString("ch_qid"));

                        questionList.add(quizGetQuestionResponseModel);
//                        questionList.add(jsonObject.getString("ch_question"));


                    }
                    setValueToUI(questionList);
                    quizAdapter.notifyDataSetChanged();

                }
            } else {
                customDialogBox = new CustomDialogBox(QuizActivity.this, this, "Server Response", message, false, "OK", "CANCEL");
                customDialogBox.show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setValueToUI(ArrayList<QuizGetQuestionResponseModel.Quiz_Questions_List> quizGetQuestionResponseModel) {
        txt_quiz_title.setText(quizGetQuestionResponseModel.get(0).getQtitle());
        txt_quiz_duration.setText(quizGetQuestionResponseModel.get(0).getQstart_date() + "-" + quizGetQuestionResponseModel.get(0).getQend_date());
        strQuestionBankId = quizGetQuestionResponseModel.get(0).getCh_qid();
    }

    @Override
    public void getQuestionError(String error) {
        customDialogBox = new CustomDialogBox(QuizActivity.this, this, "Server Response", error, false, "OK", "CANCEL");
        customDialogBox.show();
    }

    @Override
    public void postQuestionSuccess(String response) {


        try {
            JSONObject mJsnobject = new JSONObject(response);
            String status = mJsnobject.getString("status");
            String message = mJsnobject.getString("message");
            if (status.equalsIgnoreCase("1")) {
                customDialogBox = new CustomDialogBox(QuizActivity.this, this, "Server Response", message, false, "OK", "CANCEL");
                customDialogBox.show();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void postQuestionError(String error) {
        customDialogBox = new CustomDialogBox(QuizActivity.this, this,
                "Server Response", error, false, "OK",
                "CANCEL");
        customDialogBox.show();
    }

    @Override
    public void getScoreSuccess(String response) {

    }

    @Override
    public void getScoreError(String response) {

    }

    @Override
    public void getPreviousQuestionSuccess(String response) {

    }

    @Override
    public void getPreviousQuestionError(String error) {

    }

    @Override
    public void getTopRunnerSuccess(String response) {

    }

    @Override
    public void getTopRunnerError(String error) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.but_submit:
                upDateAnswer();
                break;
            case R.id.img_back_press:
                goBackActivity();
                break;
        }
    }

    private void goBackActivity() {
        finish();
    }

    private void upDateAnswer() {
        StringBuilder strQuestionid, strAnswerid;
        Log.e(QuizAdapter.class.getSimpleName(), String.valueOf(quizAdapter.answerIdList.size()));
        strQuestionid = new StringBuilder();
        strAnswerid = new StringBuilder();
        for (int i = 0; i < questionList.size(); i++) {
            if (i == questionList.size() - 1) {
                if (quizAdapter.questionIdList.get(i).equalsIgnoreCase("null")) {
                    strQuestionid.append(0);
                } else {
                    strQuestionid.append(quizAdapter.questionIdList.get(i));
                }

                if (quizAdapter.answerIdList.get(i).equalsIgnoreCase("null")) {
                    strAnswerid.append(0);
                } else {
                    strAnswerid.append(quizAdapter.answerIdList.get(i));
                }

            } else {
                if (quizAdapter.questionIdList.get(i).equalsIgnoreCase("null")) {
                    strQuestionid.append(0).append("|");
                } else {
                    strQuestionid.append(quizAdapter.questionIdList.get(i)).append("|");
                }

                if (quizAdapter.answerIdList.get(i).equalsIgnoreCase("null")) {
                    strAnswerid.append(0).append("|");
                } else {
                    strAnswerid.append(quizAdapter.answerIdList.get(i)).append("|");
                }
            }
        }

        AnswerQuizRequestModel answerQuizRequestModel = new AnswerQuizRequestModel();
        answerQuizRequestModel.setQuserid(preferenceData.getDevoteeId());
        answerQuizRequestModel.setQusername(preferenceData.getDevoteeName() + " " + preferenceData.getDevoteeLName());
        answerQuizRequestModel.setQid(strQuestionBankId);
        answerQuizRequestModel.setQuestionid(strQuestionid.toString());
        answerQuizRequestModel.setAnswerid(strAnswerid.toString());
        if (checkNetwork.isNetworkAvailable()) {
            quizPresenter.postQuizAnswers(answerQuizRequestModel);
        } else {
            customDialogBox = new CustomDialogBox(QuizActivity.this, this, "You are in Offline", "please check your internat connection", false, "OK", "CANCEL");
            customDialogBox.show();
        }
    }

    @Override
    public void okonclick() {
        customDialogBox.dismiss();
        startActivity(new Intent(getApplicationContext(), DashBoardActivity.class));
        finish();
    }

    @Override
    public void cancelonclick() {
        customDialogBox.dismiss();
        startActivity(new Intent(getApplicationContext(), DashBoardActivity.class));
        finish();
    }
}
