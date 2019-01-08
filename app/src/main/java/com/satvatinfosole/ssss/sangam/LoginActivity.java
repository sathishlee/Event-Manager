package com.satvatinfosole.ssss.sangam;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.satvatinfosole.ssss.sangam.constants.AppConstants;
import com.satvatinfosole.ssss.sangam.dashboard.DashBoardActivity;
import com.satvatinfosole.ssss.sangam.dashboard.UnApprovalUserActivity;
import com.satvatinfosole.ssss.sangam.presenter.LogInPresenter;
import com.satvatinfosole.ssss.sangam.sharedpreference.PreferenceData;
import com.satvatinfosole.ssss.sangam.signup.PrimaryDetailsActivity;
import com.satvatinfosole.ssss.sangam.utility.CheckNetwork;
import com.satvatinfosole.ssss.sangam.view.LoginViews;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, LoginViews {
    EditText edt_UserNAme, edt_Password;
    LinearLayout ll_ButtonLogin;
    TextView txt_SignUp;
    String strUserName, strPassword,strDeviceToken;
    CheckBox requestsattus;

    ProgressDialog pDialog;
    CheckNetwork checkNetwork;
    PreferenceData preferenceData;
    LogInPresenter logInPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initUI();
        onClickListner();

    }

    private void onClickListner() {
        ll_ButtonLogin.setOnClickListener(this);
        txt_SignUp.setOnClickListener(this);
    }

    private void initUI() {
        checkNetwork = new CheckNetwork(this);
        preferenceData = new PreferenceData(this);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Please Wait ...");

        logInPresenter = new LogInPresenter(LoginActivity.this, LoginActivity.this);

        edt_UserNAme = (EditText) findViewById(R.id.input_email);
        edt_Password = (EditText) findViewById(R.id.input_password);
        ll_ButtonLogin = (LinearLayout) findViewById(R.id.ll_but_login);
        txt_SignUp = (TextView) findViewById(R.id.txt_signup);
        requestsattus = (CheckBox) findViewById(R.id.requestsattus);
        requestsattus.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_but_login:
                getInputValue();
                break;
            case R.id.txt_signup:
                goSignUp();
        }
    }

    private void goSignUp() {
        if (AppConstants.REQUEST_STATUS) {
            startActivity(new Intent(getApplicationContext(), PrimaryDetailsActivity.class));
            finish();
        } else {
            startActivity(new Intent(getApplicationContext(), RequestActivity.class));
            finish();
        }
    }

    private void getInputValue() {
        strUserName = edt_UserNAme.getText().toString();
        strPassword = edt_Password.getText().toString();
        Log.e(LoginActivity.class.getSimpleName(),"device token "+preferenceData.getFirebaseRefrenceToken());
        strDeviceToken = preferenceData.getFirebaseRefrenceToken();
        if (strUserName.equalsIgnoreCase("")) {
            edt_UserNAme.setError("Enter User Name");
        } else if (strPassword.equalsIgnoreCase("")) {
            edt_Password.setError("Enter Password");
        }
        /*else if (strDeviceToken.equalsIgnoreCase("")) {
            edt_Password.setError("Devide token is null please try again");
        } */
        else {
            doPostToServer(strUserName, strPassword,strDeviceToken);
        }
    }

    private void doPostToServer(String strUserName, String strPassword,String strDeviceToken) {
//        Toast.makeText(getApplicationContext(),"USER NAME"+strUserName,Toast.LENGTH_LONG).show();
        if (checkNetwork.isNetworkAvailable()) {
            logInPresenter.doLogIn(strUserName, strPassword,strDeviceToken);
        } else {
            Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_LONG).show();
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
    public void showLoginSuccess(String responseSuccess) {
//        Toast.makeText(getApplicationContext(), responseSuccess, Toast.LENGTH_LONG).show();
        JSONObject jObj = null;
        JSONObject jobj_Login_Details = null;
        try {
            jObj = new JSONObject(responseSuccess);
            String status = jObj.getString("status");
            String message = jObj.getString("message");
            if (status.equalsIgnoreCase("1")) {

                String login_Details = jObj.getString("Login_Details");
                jobj_Login_Details = new JSONObject(login_Details);

                String devoteeid = jobj_Login_Details.getString("devoteeid");
                String activation = jobj_Login_Details.getString("activation");
                String devoteestatus = jobj_Login_Details.getString("status");
                String devoteeImage = jobj_Login_Details.getString("devotee_image");
                preferenceData.setLoginDetails(devoteeid, activation, devoteestatus,devoteeImage);
                preferenceData.setLogin(true);
                if (preferenceData.getLogin()) {
                    if (preferenceData.getDevoteeStatus().equalsIgnoreCase("1")) {
                        startActivity(new Intent(getApplicationContext(), UnApprovalUserActivity.class));
                    } else if (preferenceData.getDevoteeStatus().equalsIgnoreCase("2")) {
                        startActivity(new Intent(getApplicationContext(), UnApprovalUserActivity.class));
                    } else if (preferenceData.getDevoteeStatus().equalsIgnoreCase("3")) {
                        startActivity(new Intent(getApplicationContext(), UnApprovalUserActivity.class));
                    } else if (preferenceData.getDevoteeStatus().equalsIgnoreCase("4")) {
                        startActivity(new Intent(getApplicationContext(), DashBoardActivity.class));
                    }else if (preferenceData.getDevoteeStatus().equalsIgnoreCase("5")) {
                        startActivity(new Intent(getApplicationContext(), DashBoardActivity.class));
                    }else if (preferenceData.getDevoteeStatus().equalsIgnoreCase("6")) {
                        startActivity(new Intent(getApplicationContext(), DashBoardActivity.class));
                    }else if (preferenceData.getDevoteeStatus().equalsIgnoreCase("7")) {
                        startActivity(new Intent(getApplicationContext(), DashBoardActivity.class));
                    }
                }

            } else {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void showLoginError(String responseError) {
        Toast.makeText(getApplicationContext(), responseError, Toast.LENGTH_LONG).show();

    }
}
