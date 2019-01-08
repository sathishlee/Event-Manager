package com.satvatinfosole.ssss.sangam.feedback;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.satvatinfosole.ssss.sangam.DailogInterface;
import com.satvatinfosole.ssss.sangam.R;
import com.satvatinfosole.ssss.sangam.dashboard.DashBoardActivity;
import com.satvatinfosole.ssss.sangam.presenter.FeedbackPresenter;
import com.satvatinfosole.ssss.sangam.quiz.QuizActivity;
import com.satvatinfosole.ssss.sangam.sharedpreference.PreferenceData;
import com.satvatinfosole.ssss.sangam.utility.CheckNetwork;
import com.satvatinfosole.ssss.sangam.utility.CustomDialogBox;
import com.satvatinfosole.ssss.sangam.view.FeedbackViews;

import org.json.JSONObject;

public class FeedbackActivity extends AppCompatActivity implements View.OnClickListener, FeedbackViews, DailogInterface {
    public EditText edt_feedback;
    public Button but_submit;
    public ImageView img_back_press;
    String strFeedback;
    ProgressDialog pDialog;
    CheckNetwork checkNetwork;
    PreferenceData preferenceData;
    FeedbackPresenter feedbackPresenter;
    CustomDialogBox customDialogBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        intiUI();
        onClickListner();
    }
    private void onClickListner() {
        but_submit.setOnClickListener(this);
        img_back_press.setOnClickListener(this);
    }

    private void intiUI() {

        checkNetwork = new CheckNetwork(this);
        preferenceData = new PreferenceData(this);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Please Wait ...");

        feedbackPresenter = new FeedbackPresenter(FeedbackActivity.this, FeedbackActivity.this);
        edt_feedback = (EditText) findViewById(R.id.edt_feedback);
        but_submit = (Button) findViewById(R.id.but_submit);
        img_back_press = (ImageView) findViewById(R.id.img_back_press);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back_press:
                goBackActivity();
                break;
            case R.id.but_submit:
                submitFeedBack();
                break;
        }
    }

    private void submitFeedBack() {
        strFeedback = edt_feedback.getText().toString();
        if (strFeedback.equalsIgnoreCase("null")) {
            customDialogBox = new CustomDialogBox(FeedbackActivity.this, this, "", "Please Enter Your Feedback. ", false, "OK", "CANCEL");
            customDialogBox.show();
        } else {
            if (checkNetwork.isNetworkAvailable()) {
                feedbackPresenter.postFeedback(preferenceData.getDevoteeId(), strFeedback);
            }
            else {
                customDialogBox = new CustomDialogBox(FeedbackActivity.this, this, "You are in Offline", "please check your internat connection", false, "OK", "CANCEL");
                customDialogBox.show();
            }
        }
    }

    private void goBackActivity() {
        finish();
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
    public void postFeedbackSuccess(String response) {

            try {
                JSONObject mJsnobject = new JSONObject(response);
                String status = mJsnobject.getString("status");
                String message = mJsnobject.getString("message");
                if (status.equalsIgnoreCase("1")) {
                    customDialogBox = new CustomDialogBox(FeedbackActivity.this, this, "Server Response", message, false, "OK", "CANCEL");
                    customDialogBox.show();

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    @Override
    public void postFeedbackError(String error) {
        customDialogBox = new CustomDialogBox(FeedbackActivity.this, this, "Server Response", error, false, "OK", "CANCEL");
        customDialogBox.show();
    }

    @Override
    public void okonclick() {
        customDialogBox.dismiss();
        startActivity(new Intent(getApplicationContext(), DashBoardActivity.class));
    }

    @Override
    public void cancelonclick() {
        customDialogBox.dismiss();
        startActivity(new Intent(getApplicationContext(), DashBoardActivity.class));

    }
}
