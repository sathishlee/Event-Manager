package com.satvatinfosole.ssss.sangam.quiz;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.satvatinfosole.ssss.sangam.DailogInterface;
import com.satvatinfosole.ssss.sangam.R;
import com.satvatinfosole.ssss.sangam.adapter.QuizAdapter;
import com.satvatinfosole.ssss.sangam.adapter.QuizRrreviousMonthAdapter;
import com.satvatinfosole.ssss.sangam.model.responseModel.QuizGetQuestionResponseModel;
import com.satvatinfosole.ssss.sangam.model.responseModel.PreviousQuizResponseModel;
import com.satvatinfosole.ssss.sangam.presenter.QuizPresenter;
import com.satvatinfosole.ssss.sangam.sharedpreference.PreferenceData;
import com.satvatinfosole.ssss.sangam.utility.CheckNetwork;
import com.satvatinfosole.ssss.sangam.utility.CustomDialogBox;
import com.satvatinfosole.ssss.sangam.view.QuizView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class QuizReportViewActivity extends AppCompatActivity implements QuizView, DailogInterface {
    public TextView txtUserName, txtPreviousScore, txtCumScore, txtLastQuizTitle;
    public RecyclerView recvLastQuizList;

    ProgressDialog pDialog;
    CheckNetwork checkNetwork;
    PreferenceData preferenceData;
    QuizPresenter quizPresenter;
    CustomDialogBox customDialogBox;

    ArrayList<PreviousQuizResponseModel.Previous_questions> arr_quizGetQuestionResponseModel;
    PreviousQuizResponseModel.Previous_questions quizGetQuestionResponseModel;
    QuizRrreviousMonthAdapter quizRrreviousMonthAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_report_view);
        initUI();

    }


    private void initUI() {

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Please Wait ...");
        pDialog.show();
        for (int i = 0; i <= 10000; i++) {
            pDialog.dismiss();

        }
        checkNetwork = new CheckNetwork(this);
        preferenceData = new PreferenceData(this);

        arr_quizGetQuestionResponseModel = new ArrayList<>();
        quizPresenter = new QuizPresenter(QuizReportViewActivity.this, QuizReportViewActivity.this);
        if (checkNetwork.isNetworkAvailable()) {
            quizPresenter.getScore(preferenceData.getDevoteeId());
        } else {
            Toast.makeText(getApplicationContext(), "on internet connection", Toast.LENGTH_SHORT).show();
        }

        txtUserName = (TextView) findViewById(R.id.txt_user_name);
        txtPreviousScore = (TextView) findViewById(R.id.txt_previous_score);
        txtCumScore = (TextView) findViewById(R.id.txt_cum_score);
        txtLastQuizTitle = (TextView) findViewById(R.id.txt_last_quiz_title);
        recvLastQuizList = (RecyclerView) findViewById(R.id.recv_last_quiz_list);

        quizRrreviousMonthAdapter = new QuizRrreviousMonthAdapter(arr_quizGetQuestionResponseModel, this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recvLastQuizList.setLayoutManager(mLayoutManager);
        recvLastQuizList.setItemAnimator(new DefaultItemAnimator());
        recvLastQuizList.setAdapter(quizRrreviousMonthAdapter);
    }

    @Override
    public void showProgress() {
        pDialog.show();
    }

    @Override
    public void hideProgress() {
        pDialog.dismiss();
    }

    @Override
    public void getQuestionSuccess(String response) {

    }

    @Override
    public void getQuestionError(String error) {

    }

    @Override
    public void postQuestionSuccess(String response) {

    }

    @Override
    public void postQuestionError(String error) {

    }

    @Override
    public void getScoreSuccess(String response) {

        Log.e(QuizReportViewActivity.class.getSimpleName(),"response"+response);

        try {
            JSONObject mJsnobject = new JSONObject(response);
            String status = mJsnobject.getString("status");
            String message = mJsnobject.getString("message");

            if (status.equalsIgnoreCase("1")) {
                JSONObject jsonObject = mJsnobject.getJSONObject("your_score");
                Log.e(QuizReportViewActivity.class.getSimpleName(),"cumulative Score"+jsonObject.getString("cumulative_score"));

                String strCumulativeScore = jsonObject.getString("cumulative_score");
                String strPreviousScore = jsonObject.getString("previous_score");

                txtPreviousScore.setText(strPreviousScore);
                txtCumScore.setText(strCumulativeScore);
            } else {
                customDialogBox = new CustomDialogBox(QuizReportViewActivity.this, this, "Server Response", message, false, "OK", "CANCEL");
                customDialogBox.show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (checkNetwork.isNetworkAvailable()) {
            quizPresenter.getpreviousQuestions();
        }

    }

    @Override
    public void getScoreError(String response) {
        customDialogBox = new CustomDialogBox(this, this,
                "Server Response", response, false, "OK",
                "CANCEL");
        customDialogBox.show();
    }

    @Override
    public void getPreviousQuestionSuccess(String response) {
        try {
            JSONObject mJsnobject = new JSONObject(response);
            String status = mJsnobject.getString("status");
            String message = mJsnobject.getString("message");
            if (status.equalsIgnoreCase("1")) {

                JSONArray jsonArray = mJsnobject.getJSONArray("previous_questions");
                Log.e("Question list size ->", jsonArray.length() + "");

                JSONObject jsonObject = null;
                if (jsonArray.length() != 0) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject = jsonArray.getJSONObject(i);
                        Log.e("Question ->", i + jsonObject.toString());


                        quizGetQuestionResponseModel = new PreviousQuizResponseModel.Previous_questions();
                        quizGetQuestionResponseModel.setCh_sno(String.valueOf(i+1));
                        quizGetQuestionResponseModel.setCh_question(jsonObject.getString("ch_question"));
                        quizGetQuestionResponseModel.setCh_answer(jsonObject.getString("ch_answer"));


                        arr_quizGetQuestionResponseModel.add(quizGetQuestionResponseModel);
//                        questionList.add(jsonObject.getString("ch_question"));


                    }

                    quizRrreviousMonthAdapter.notifyDataSetChanged();

                }
            } else {
                customDialogBox = new CustomDialogBox(QuizReportViewActivity.this, this, "Server Response", message, false, "OK", "CANCEL");
                customDialogBox.show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    public void getPreviousQuestionError(String error) {
        customDialogBox = new CustomDialogBox(this, this,
                "Server Response", error, false, "OK",
                "CANCEL");
        customDialogBox.show();
    }

    @Override
    public void getTopRunnerSuccess(String response) {

    }

    @Override
    public void getTopRunnerError(String error) {

    }

    @Override
    public void okonclick() {

    }

    @Override
    public void cancelonclick() {

    }
}
