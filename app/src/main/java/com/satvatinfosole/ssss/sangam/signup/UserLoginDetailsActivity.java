package com.satvatinfosole.ssss.sangam.signup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.satvatinfosole.ssss.sangam.DailogInterface;
import com.satvatinfosole.ssss.sangam.R;
import com.satvatinfosole.ssss.sangam.RequestActivity;
import com.satvatinfosole.ssss.sangam.SplashActivity;
import com.satvatinfosole.ssss.sangam.constants.AppConstants;
import com.satvatinfosole.ssss.sangam.dashboard.DashBoardActivity;
import com.satvatinfosole.ssss.sangam.presenter.RegistreationPresenter;
import com.satvatinfosole.ssss.sangam.sharedpreference.PreferenceData;
import com.satvatinfosole.ssss.sangam.utility.CheckNetwork;
import com.satvatinfosole.ssss.sangam.utility.CustomDialogBox;
import com.satvatinfosole.ssss.sangam.view.RegistrationViews;

import org.json.JSONObject;

public class UserLoginDetailsActivity extends AppCompatActivity implements View.OnClickListener, RegistrationViews, DailogInterface {
    String TAG = UserLoginDetailsActivity.class.getSimpleName();
    Spinner spSecletknowAboutUs;
    EditText edtAboutYou, edtEmail, edtUserId, edtPassword, edtReTypePassword;
    Button btnFinesh;
    String strSecletknowAboutUs, strAboutYou, strEmail, strUserId, strPassword, strReTypePassword;
    ArrayAdapter<String> arrayAdapter;

    ProgressDialog pDialog;
    CheckNetwork checkNetwork;
    PreferenceData preferenceData;
    RegistreationPresenter registreationPresenter;

    CustomDialogBox customDialogBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login_details);
        initUI();
        onClickListner();
    }

    private void onClickListner() {
        btnFinesh.setOnClickListener(this);
    }

    private void initUI() {

        checkNetwork = new CheckNetwork(this);
        preferenceData = new PreferenceData(this);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Please Wait ...");

        registreationPresenter = new RegistreationPresenter(this, this);

        spSecletknowAboutUs = (Spinner) findViewById(R.id.sp_choose_one);
        edtEmail = (EditText) findViewById(R.id.input_email);
        edtEmail.setText(preferenceData.getRegDevoteeEmail());
        edtAboutYou = (EditText) findViewById(R.id.input_about_you);
        edtUserId = (EditText) findViewById(R.id.input_user_id);
        edtPassword = (EditText) findViewById(R.id.input_password);
        edtReTypePassword = (EditText) findViewById(R.id.input_re_password);
        btnFinesh = (Button) findViewById(R.id.btn_finish);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.arr_know_about_sangam));
        spSecletknowAboutUs.setAdapter(arrayAdapter);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_finish:

                if (!validateFields()) {
                    Toast.makeText(getApplicationContext(), "validatestatus - " + !validateFields(), Toast.LENGTH_SHORT).show();
                    customDialogBox = new CustomDialogBox(UserLoginDetailsActivity.this, this, "Try again", "please try again later", false, "OK", "CANCEL");
                    customDialogBox.show();
                } else {
                    AppConstants.KNOWN_ABOUT_US = strSecletknowAboutUs;
                    AppConstants.ABOUT_YOU = strAboutYou;
                    AppConstants.EMAIL = strEmail;
                    AppConstants.USER_ID = strUserId;
                    AppConstants.PASSWORD = strReTypePassword;
                    if (checkNetwork.isNetworkAvailable()) {
                        registreationPresenter.registration(AppConstants.FIRST_NAME, AppConstants.LAST_NAME,
                                AppConstants.FATHER_NAME, AppConstants.GENDER, AppConstants.SPOUSE_NAME, AppConstants.DOB,
                                AppConstants.ADDRESS_LINE1 + AppConstants.ADDRESS_LINE2, AppConstants.CITY, AppConstants.COUNTRY,
                                AppConstants.STATE, AppConstants.PINCODE, AppConstants.MOBILE_NUMBER,
                                AppConstants.LANDLINE_NUMBER, AppConstants.KNOWN_ABOUT_US, AppConstants.ABOUT_YOU,
                                AppConstants.USER_ID, AppConstants.PASSWORD,
                                preferenceData.getRegDevoteeEmail(), preferenceData.getRegDevoteeActivationKey()
//                                preferenceData.getRequestMailID().replace("@", "%40"), preferenceData.getActivationKey()
                        );
                    } else {
//                        Toast.makeText(getApplicationContext(),"no internet connection",Toast.LENGTH_SHORT).show();
                        customDialogBox = new CustomDialogBox(UserLoginDetailsActivity.this, this, "You are in Offline", "please check your internat connection", false, "OK", "CANCEL");
                        customDialogBox.show();
                    }
                }

        }

    }

    private boolean validateFields() {
        boolean valid = true;

        strSecletknowAboutUs = spSecletknowAboutUs.getSelectedItem() + "";
        strAboutYou = edtAboutYou.getText().toString();
        strEmail = edtEmail.getText().toString();
        strUserId = edtUserId.getText().toString();
        strPassword = edtPassword.getText().toString();
        strReTypePassword = edtReTypePassword.getText().toString();


        if (strSecletknowAboutUs.equalsIgnoreCase("")) {
            Toast.makeText(getApplicationContext(), "please select Know About Us", Toast.LENGTH_LONG).show();
            valid = false;
        } else if (strAboutYou.equalsIgnoreCase("")) {
            edtAboutYou.setError("please enter about you");
            valid = false;

        } else if (strEmail.equalsIgnoreCase("") || !android.util.Patterns.EMAIL_ADDRESS.matcher(strEmail).matches()) {
            edtEmail.setError("please enter a valid email addressl");
            valid = false;

        } else if (strUserId.equalsIgnoreCase("")) {
            edtUserId.setError("please enter user Id");
            valid = false;

        } else if (strPassword.isEmpty() || strPassword.length() < 6 || strPassword.length() > 10) {
            edtPassword.setError("please enter password");
            valid = false;

        } else if (strReTypePassword.equalsIgnoreCase("")) {
            edtReTypePassword.setError("please enter password");
            valid = false;

        } else if (strPassword.equalsIgnoreCase(strReTypePassword)) {
            valid = true;
//            Toast.makeText(getApplicationContext(), "Update Successfully", Toast.LENGTH_LONG).show();
        } else {
            valid = false;
//            Toast.makeText(getApplicationContext(), "Password miss match", Toast.LENGTH_LONG).show();
            customDialogBox = new CustomDialogBox(UserLoginDetailsActivity.this, this, "Password Incorrect", "please enter correct password in retype password.", false, "OK", "CANCEL");
            customDialogBox.show();
        }
        return valid;
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
        JSONObject jObj = null;
        try {
            jObj = new JSONObject(responseSuccess);
            String status = jObj.getString("status");
            String message = jObj.getString("message");
            if (status.equalsIgnoreCase("1")) {
                preferenceData.setRegisterationStatus(true);
                customDialogBox = new CustomDialogBox(UserLoginDetailsActivity.this, this, "Server Response", message, true, "OK", "CANCEL");
                customDialogBox.show();
            } else {
                customDialogBox = new CustomDialogBox(UserLoginDetailsActivity.this, this, "Server Response", message, false, "OK", "CANCEL");
                customDialogBox.show();
                preferenceData.setRegisterationStatus(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void shoRegistrationError(String responseError) {
        preferenceData.setRegisterationStatus(false);
        customDialogBox = new CustomDialogBox(UserLoginDetailsActivity.this, this, "Please Try Again", "Unable to reach the server,\n Please try again", false, "OK", "CANCEL");
        customDialogBox.show();

    }

    @Override
    public void getRequestEmailSuccess(String responseSuccess) {

    }

    @Override
    public void getRequestEmailError(String responseSuccess) {

    }

    @Override
    public void getRequestStatusSuccess(String responseSuccess) {

    }

    @Override
    public void getRequestStatusError(String responseSuccess) {

    }

    @Override
    public void okonclick() {
        customDialogBox.dismiss();
        startActivity(new Intent(getApplicationContext(), SplashActivity.class));

    }

    @Override
    public void cancelonclick() {
        customDialogBox.dismiss();

    }
}
