package com.satvatinfosole.ssss.sangam.signup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.satvatinfosole.ssss.sangam.constants.AppConstants;
import com.satvatinfosole.ssss.sangam.R;

public class ContactDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    String TAG = ContactDetailsActivity.class.getSimpleName();

    EditText edtAddressLine1, edtAddressLine2, edtCity,edtState,edtCountry, edtPincode, edtMobileNumber, edtLandlineNumber;
    Spinner spState, spCountry;
    String strAddressLine1, strAddressLine2, strCity, strPincode, strMobileNumber, strLandlineNumber, strState, strCountry;
    Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);
        initUi();
        onClickListner();
    }

    private void onClickListner() {
        btnNext.setOnClickListener(this);
    }

    private void initUi() {
        edtAddressLine1 = (EditText) findViewById(R.id.input_address_line_one);
        edtAddressLine2 = (EditText) findViewById(R.id.input_address_line_two);
        edtCity = (EditText) findViewById(R.id.input_city);
        edtState = (EditText) findViewById(R.id.input_state);
        edtCountry = (EditText) findViewById(R.id.input_country);
        edtPincode = (EditText) findViewById(R.id.input_pincode);
        edtMobileNumber = (EditText) findViewById(R.id.input_mobile_no);
        edtLandlineNumber = (EditText) findViewById(R.id.input_landline_no);
        spState = (Spinner) findViewById(R.id.sp_state);
        spCountry = (Spinner) findViewById(R.id.sp_country);
        btnNext = (Button) findViewById(R.id.btn_contact_next);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_contact_next:
                if (!validateFields()) {

                } else {
                    AppConstants.ADDRESS_LINE1 = strAddressLine1;
                    AppConstants.ADDRESS_LINE2 = strAddressLine2;
                    AppConstants.CITY = strCity;
//                    AppConstants.STATE = strState;
//                    AppConstants.COUNTRY = strCountry;
//
                   AppConstants.STATE = "TN";
                    AppConstants.COUNTRY = "IN";
                    AppConstants.PINCODE = strPincode;
                    AppConstants.MOBILE_NUMBER = strMobileNumber;
                    AppConstants.LANDLINE_NUMBER = strLandlineNumber;

                    startActivity(new Intent(getApplicationContext(), UserLoginDetailsActivity.class));
                }
                break;
        }
    }

    private boolean validateFields() {

        boolean valid = true;
        strAddressLine1 = edtAddressLine1.getText().toString();
        strAddressLine2 = edtAddressLine2.getText().toString();
        strCity = edtCity.getText().toString();
      /*  strState = String.valueOf(spState.getSelectedItem());
        strCountry = String.valueOf(spCountry.getSelectedItem());*/

        strState = edtState.getText().toString();
        strCountry = edtCountry.getText().toString();

        strPincode = edtPincode.getText().toString();
        strMobileNumber = edtMobileNumber.getText().toString();
        strLandlineNumber = edtLandlineNumber.getText().toString();

        if (strAddressLine1.equalsIgnoreCase("")) {
            edtAddressLine1.setError("plese enter first name");
            valid = false;
        }  else if (strAddressLine2.equalsIgnoreCase("")) {
            edtAddressLine2.setError("plese enter last name");
            valid = false;
        } else if (strCity.equalsIgnoreCase("")) {
            edtCity.setError("plese enter last name");
            valid = false;
        }
        else if (strState.equalsIgnoreCase("")) {
            Toast.makeText(getApplicationContext(), "please select state", Toast.LENGTH_LONG).show();
            valid = false;
        } else if (strCountry.equalsIgnoreCase("")) {
            Toast.makeText(getApplicationContext(), "please select country", Toast.LENGTH_LONG).show();
            valid = false;
        } else if (strPincode.equalsIgnoreCase("")) {
            edtPincode.setError("please enter pincode");
            valid = false;
        } else if (strMobileNumber.equalsIgnoreCase("")) {
            edtMobileNumber.setError("please enter mobile number");
            valid = false;
        } else if (strMobileNumber.length() != 10) {
            edtMobileNumber.setError("please enter valid mobile number");
            valid = false;
        }/* else if (strLandlineNumber.equalsIgnoreCase("")) {
            edtLandlineNumber.setError("please enter LandLine number");
            valid = false;
        }*/ else {
//            startActivity(new Intent(getApplicationContext(), SignupStepThreeActivity.class));
        }
        return valid;

    }
}
