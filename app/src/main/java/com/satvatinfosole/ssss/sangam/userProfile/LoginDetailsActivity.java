package com.satvatinfosole.ssss.sangam.userProfile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.satvatinfosole.ssss.sangam.R;
import com.satvatinfosole.ssss.sangam.constants.AppConstants;

public class LoginDetailsActivity extends AppCompatActivity {
ImageView img_back_press;
LinearLayout ll_edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_details);
        img_back_press =(ImageView) findViewById(R.id.img_back_press);
        ll_edit =(LinearLayout) findViewById(R.id.ll_edit);
        ll_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppConstants.EDIT_ACTIVITY="3";
                startActivity(new Intent(getApplicationContext(),EditProfileActivity.class));
            }
        });



    }
}
