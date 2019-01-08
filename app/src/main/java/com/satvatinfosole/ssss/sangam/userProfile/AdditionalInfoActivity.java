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

public class AdditionalInfoActivity extends AppCompatActivity {
    TextView txt_add_info, txt_how_do_u_know;
    PreferenceData preferenceData;
    LinearLayout ll_edit;
ImageView img_back_press;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additional_info);
        initUI();
        onClickListner();
    }

    private void onClickListner() {
        ll_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppConstants.EDIT_ACTIVITY="2";
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

    private void initUI() {
        preferenceData = new PreferenceData(this);

        txt_add_info = (TextView) findViewById(R.id.txt_add_info);
        txt_how_do_u_know = (TextView) findViewById(R.id.txt_how_do_u_know);
        ll_edit = (LinearLayout) findViewById(R.id.ll_edit);
        img_back_press=(ImageView)findViewById(R.id.img_back_press);
        txt_add_info.setText(preferenceData.getDevoteAdd_Info());
        txt_how_do_u_know.setText(preferenceData.getDevoteHowDoYouKnow());
        }
}
