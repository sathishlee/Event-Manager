package com.satvatinfosole.ssss.sangam.userProfile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.satvatinfosole.ssss.sangam.R;
import com.satvatinfosole.ssss.sangam.constants.AppConstants;
import com.satvatinfosole.ssss.sangam.sharedpreference.PreferenceData;

public class ProfileDetailsActivity extends AppCompatActivity {
    TextView txtName, txtEmail, txtPhone, txtAddress, txt_profile_father_name, txt_profile_dob;

    PreferenceData preferenceData;
LinearLayout ll_edit;
ImageView img_back_press;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_details);
        initUI();
        setvalueUI();
        onClickListner();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
        finish();
    }

    private void onClickListner() {
        ll_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppConstants.EDIT_ACTIVITY="1";
                startActivity(new Intent(getApplicationContext(),EditProfileActivity.class));
            }
        });
        img_back_press.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                finish();
            }
        });
    }

    private void setvalueUI() {
        txtName.setText(preferenceData.getDevoteeName());
        txtEmail.setText(preferenceData.getDevoteEmail());
        txtAddress.setText(preferenceData.getDevoteAddress()
                + ",\n"
                + preferenceData.getDevoteCity()+ ",\t"
                + preferenceData.getDevoteState()
                + ",\n" + preferenceData.getDevoteCountry()+ ",\t-"
                + preferenceData.getDevotePincode().codePointAt(4));
        txtPhone.setText(preferenceData.getDevoteContact());
        txt_profile_father_name.setText(preferenceData.getDevoteFatherName());
        txt_profile_dob.setText(preferenceData.getDevoteDob());
    }

    private void initUI() {
        preferenceData = new PreferenceData(this);
        txtName = (TextView) findViewById(R.id.txt_profile_user_name);
        txtEmail = (TextView) findViewById(R.id.txt_profile_email);
        txtPhone = (TextView) findViewById(R.id.txt_profile_phone);
        txtAddress = (TextView) findViewById(R.id.txt_profile_address);
        txt_profile_father_name = (TextView) findViewById(R.id.txt_profile_father_name);
        txt_profile_dob = (TextView) findViewById(R.id.txt_profile_dob);
        ll_edit =(LinearLayout) findViewById(R.id.ll_edit);
        img_back_press=(ImageView)findViewById(R.id.img_back_press);
    }
}
