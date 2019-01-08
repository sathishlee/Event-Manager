package com.satvatinfosole.ssss.sangam.signup;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.satvatinfosole.ssss.sangam.DailogInterface;
import com.satvatinfosole.ssss.sangam.R;
import com.satvatinfosole.ssss.sangam.RequestActivity;
import com.satvatinfosole.ssss.sangam.constants.AppConstants;
import com.satvatinfosole.ssss.sangam.presenter.LogInPresenter;
import com.satvatinfosole.ssss.sangam.sharedpreference.PreferenceData;
import com.satvatinfosole.ssss.sangam.utility.CheckNetwork;
import com.satvatinfosole.ssss.sangam.utility.CustomDialogBox;
import com.satvatinfosole.ssss.sangam.view.LoginViews;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class PrimaryDetailsActivity extends AppCompatActivity implements LoginViews, DailogInterface {
    EditText edtFirstName, edtLastname, edtFatherName, edtSpouseName, edtDOB;
    RadioGroup rgGender;
    RadioButton rbSelected;
    String strFirstName, strLastname, strFatherName, strSpouseName, strDOB, strGender;
    Button btnNext;


    ProgressDialog pDialog;
    CheckNetwork checkNetwork;
    PreferenceData preferenceData;
    LogInPresenter logInPresenter;

    TextView txtGreeting;

    CustomDialogBox customDialogBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primary_details);

        checkNetwork = new CheckNetwork(this);
        preferenceData = new PreferenceData(this);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Please Wait ...");

        logInPresenter = new LogInPresenter(PrimaryDetailsActivity.this, PrimaryDetailsActivity.this);

        Intent intent = getIntent();
        Uri data = intent.getData();

        String[] strUri = data.toString().split("email=");
        String strEmail = "";
        String strKey = "";
        for (int i = 0; i < strUri.length; i++) {
            Log.e(PrimaryDetailsActivity.class.getSimpleName(), "position of" + i + " ->" + strUri[i]);

            if (i == 1) {
                String[] strMailKey = strUri[i].split("&key=");
                strEmail = strMailKey[0].replace("%40", "@");
                strKey = strMailKey[1];
                Log.e(PrimaryDetailsActivity.class.getSimpleName(), "Email " + i + " ->" + strMailKey[0].replace("%40", "@"));
                Log.e(PrimaryDetailsActivity.class.getSimpleName(), "Key " + i + " ->" + strMailKey[1]);
                preferenceData.setRegisterCredintialsfromWebUrlResponse(strMailKey[0].replace("%40", "@"), strMailKey[1]);
                customDialogBox = new CustomDialogBox(PrimaryDetailsActivity.this, this, "Welcome ", "HI, " + strMailKey[0].replace("%40", "@")   + "\n" + "please start your registration process", false, "GO", "CANCEL");
                customDialogBox.show();
//                AppConstants.REG_DEVOTEE_EMAIL = strMailKey[0].replace("%40","@");
//                AppConstants.REG_DEVOTEE_ACTIVATION_KEY = strMailKey[1];
            }
        }

        if (strKey.equalsIgnoreCase("null")){
            logInPresenter.getEmail(strKey);
        }


       /* if (!strEmail.equalsIgnoreCase("null")) {
            Log.e("str email", strEmail);
            if (!strKey.equalsIgnoreCase("null")) {
                Log.e("str key", strKey);
                preferenceData.setRegisterCredintialsfromWebUrlResponse(strEmail, strKey);
            } else {
                logInPresenter.getKey(strEmail);
            }
        } else {
            if (!strKey.equalsIgnoreCase("null")) {
                logInPresenter.getEmail(strKey);
            } else {
                Toast.makeText(getApplicationContext(), "Please try again.", Toast.LENGTH_SHORT).show();
            }
        }*/

        /*http://ssss-sangam.com/root/registration.php?email=sathish.s%40satvatinfosol.com&key=92af0c229036abf6cae91e3ac453b3e1*/
        initUI();
        onClickListner();
    }

    private void onClickListner() {
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateFields()) {
                    Toast.makeText(getApplicationContext(), "validation error", Toast.LENGTH_SHORT).show();
                } else {
                    AppConstants.FIRST_NAME = strFirstName;
                    AppConstants.LAST_NAME = strLastname;
                    AppConstants.FATHER_NAME = strFatherName;
                    AppConstants.DOB = strDOB;
                    AppConstants.GENDER = strGender;
                    AppConstants.SPOUSE_NAME = strSpouseName;
                    startActivity(new Intent(getApplicationContext(), ContactDetailsActivity.class));

                }
            }
        });

        edtDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getDob(edtDOB);
            }
        });
    }

    private void getDob(final EditText edtDOB) {
        final SimpleDateFormat[] dateFormatter = new SimpleDateFormat[1];

        Calendar newCalendar = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(PrimaryDetailsActivity.this, R.style.DatePickerDialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
//                edtDob.setText(dayOfMonth + "-" + monthOfYear + "-" + year);
                dateFormatter[0] = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
                edtDOB.setText(dateFormatter[0].format(newDate.getTime()));
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        InputMethodManager imm =
                (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edtDOB.getWindowToken(), 0);

        datePickerDialog.show();
    }

    private boolean validateFields() {
        boolean valid = true;

        strFirstName = edtFirstName.getText().toString();
        strLastname = edtLastname.getText().toString();
        strFatherName = edtFatherName.getText().toString();
        strSpouseName = edtSpouseName.getText().toString();
        int selectedId = rgGender.getCheckedRadioButtonId();
        rbSelected = (RadioButton) findViewById(selectedId);
        strGender = String.valueOf(rbSelected.getText());
        strDOB = edtDOB.getText().toString();
        if (strFirstName.equalsIgnoreCase("")) {
            edtFirstName.setError("Enter First Name");
            valid = false;

        } else if (strLastname.equalsIgnoreCase("")) {
            edtLastname.setError("Enter Last Name");
            valid = false;

        } else if (strFatherName.equalsIgnoreCase("")) {
            edtFatherName.setError("Enter Father Name");
            valid = false;

        } /*else if (strSpouseName.equalsIgnoreCase("")) {
            edtSpouseName.setError("Enter Spouse Name");
            valid = false;

        }*/ else if (strDOB.equalsIgnoreCase("")) {
            edtDOB.setError("Enter Dob");
            valid = false;
        } else {
        }
        return valid;
    }

    private void initUI() {
        edtFirstName = (EditText) findViewById(R.id.input_first_name);
        edtLastname = (EditText) findViewById(R.id.input_last_name);
        edtFatherName = (EditText) findViewById(R.id.input_Father_name);
        edtSpouseName = (EditText) findViewById(R.id.input_spouse_name);
        edtDOB = (EditText) findViewById(R.id.input_dob);
        rgGender = (RadioGroup) findViewById(R.id.rg_gender);
        btnNext = (Button) findViewById(R.id.btn_next);
        txtGreeting = (TextView) findViewById(R.id.txt_greeting);
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
        JSONObject jObj = null;
        JSONObject jobj_details = null;
        try {
            jObj = new JSONObject(responseSuccess);
            String status = jObj.getString("status");
            String message = jObj.getString("message");
            if (status.equalsIgnoreCase("1")) {
                String details = jObj.getString("Email_Key_Details");
                jobj_details = new JSONObject(details);
                txtGreeting.setText("Welcome " + jobj_details.getString("email") + "\n" + "please start your registration process");
                preferenceData.setRegisterCredintialsfromWebUrlResponse(jobj_details.getString("email"), jobj_details.getString("activation"));
                customDialogBox = new CustomDialogBox(PrimaryDetailsActivity.this, this, "Welcome ", "HI, " + jobj_details.getString("email") + "\n" + "please start your registration process", false, "GO", "CANCEL");
                customDialogBox.show();
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

    @Override
    public void okonclick() {
        customDialogBox.dismiss();
    }

    @Override
    public void cancelonclick() {
        customDialogBox.dismiss();

    }
}
