package com.satvatinfosole.ssss.sangam;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.satvatinfosole.ssss.sangam.presenter.RegistreationPresenter;
import com.satvatinfosole.ssss.sangam.sharedpreference.PreferenceData;
import com.satvatinfosole.ssss.sangam.utility.CheckNetwork;
import com.satvatinfosole.ssss.sangam.utility.CustomDialogBox;
import com.satvatinfosole.ssss.sangam.view.RegistrationViews;

import org.json.JSONObject;

public class RequestActivity extends AppCompatActivity implements View.OnClickListener, RegistrationViews, DailogInterface {
    EditText edt_Emaiid;
    LinearLayout ll_SendRequest, _ll_login_view;
    String strEmailId;

    ProgressDialog pDialog;
    CheckNetwork checkNetwork;
    PreferenceData preferenceData;
    RegistreationPresenter registreationPresenter;

    CustomDialogBox customDialogBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        initUI();
        onClickListner();
    }

    private void onClickListner() {
        ll_SendRequest.setOnClickListener(this);
    }

    private void initUI() {
        checkNetwork = new CheckNetwork(this);
        preferenceData = new PreferenceData(this);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Please Wait ...");

        registreationPresenter = new RegistreationPresenter(this, this);


        edt_Emaiid = (EditText) findViewById(R.id.input_email);
        ll_SendRequest = (LinearLayout) findViewById(R.id.ll_but_send_Email);
        _ll_login_view = (LinearLayout) findViewById(R.id._ll_login_view);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_but_send_Email:
                sendEmail();
                break;
        }
    }

    private void sendEmail() {
        strEmailId = edt_Emaiid.getText().toString();
        if (strEmailId.equalsIgnoreCase("")) {
//            edt_Emaiid.setError("Enter Email Id");
            customDialogBox = new CustomDialogBox(RequestActivity.this, this, "Enter Email", "Please give Email", false, "OK", "CANCEL");
            customDialogBox.show();

        } else if (!isValidEmail(strEmailId)) {
            customDialogBox = new CustomDialogBox(RequestActivity.this, this, "Invalid Email", "Please give valid Email", false, "OK", "CANCEL");
            customDialogBox.show();
        } else {
            postEmail(strEmailId);
        }
    }

    public boolean isValidEmail(String strEmailId) {
        return (!TextUtils.isEmpty(strEmailId) && Patterns.EMAIL_ADDRESS.matcher(strEmailId).matches());

//      return false;

    }

    private void postEmail(String strEmailId) {

        if (checkNetwork.isNetworkAvailable()) {
            registreationPresenter.postEmailAddress(strEmailId);
        } else {
            customDialogBox = new CustomDialogBox(RequestActivity.this, this, "You are in Offline", "please check your internat connection", false, "OK", "CANCEL");
            customDialogBox.show();
//            Toast.makeText(getApplicationContext(), "please check your internat connection", Toast.LENGTH_LONG).show();

        }
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
    public void showRegistrationSuccess(String responseSuccess) {

    }

    @Override
    public void shoRegistrationError(String responseError) {

    }

    @Override
    public void getRequestEmailSuccess(String responseSuccess) {
        JSONObject jObj = null;
        try {
            jObj = new JSONObject(responseSuccess);
            String status = jObj.getString("status");
            String message = jObj.getString("message");
            Log.e(RequestActivity.class.getSimpleName(), "Message");
            if (status.equalsIgnoreCase("1")) {
                customDialogBox = new CustomDialogBox(RequestActivity.this, this, "Server Response", message, true, "OK", "CANCEL");
                customDialogBox.show();
            } else {
                customDialogBox = new CustomDialogBox(RequestActivity.this, this, "Server Response", message, false, "OK", "CANCEL");
                customDialogBox.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getRequestEmailError(String responseSuccess) {
        customDialogBox = new CustomDialogBox(RequestActivity.this, this, "Please Try Again", "Unable to reach the server,\n Please try again", false, "OK", "CANCEL");
        customDialogBox.show();
    }

    @Override
    public void getRequestStatusSuccess(String responseSuccess) {

    }

    @Override
    public void getRequestStatusError(String responseSuccess) {

    }

    @Override
    public void okonclick() {
        Log.e(RequestActivity.class.getSimpleName(), "you click ok button");
        startActivity(new Intent(getApplicationContext(), SplashActivity.class));
    }

    @Override
    public void cancelonclick() {
        Log.e(RequestActivity.class.getSimpleName(), "you click no button");
        customDialogBox.dismiss();
//        finish();
    }
}
