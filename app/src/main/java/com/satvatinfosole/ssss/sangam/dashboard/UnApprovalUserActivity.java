package com.satvatinfosole.ssss.sangam.dashboard;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.satvatinfosole.ssss.sangam.R;
import com.satvatinfosole.ssss.sangam.sharedpreference.PreferenceData;

import org.w3c.dom.Text;

public class UnApprovalUserActivity extends AppCompatActivity {
    TextView txt_status_discription;
    Button but_submit;
    PreferenceData preferenceData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_un_approval_user);
        initUI();
        onClickListner();
        setvalueToUI();
    }

    private void setvalueToUI() {
        if (preferenceData.getDevoteeStatus().equalsIgnoreCase("1")) {
            txt_status_discription.setText("Your status is pending, we will activated soon.");
            but_submit.setVisibility(View.GONE);
        } else if (preferenceData.getDevoteeStatus().equalsIgnoreCase("2")) {
            txt_status_discription.setText("Your status is Cancel, Please contact to admin support at: admin@ssss-sangan.org");
            but_submit.setVisibility(View.VISIBLE);
        } else if (preferenceData.getDevoteeStatus().equalsIgnoreCase("3")) {
            txt_status_discription.setText("Your status is Query,Please contact to admin support at: admin@ssss-sangan.org");
            but_submit.setVisibility(View.VISIBLE);
        }
    }

    private void onClickListner() {
        but_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "sathish.s@satvatinfosol.com", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "ssss-sangam-unAproval user");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "test mail");
                startActivity(Intent.createChooser(emailIntent, "Send email..."));
            }
        });
    }

    private void initUI() {
        preferenceData = new PreferenceData(this);
        txt_status_discription = (TextView) findViewById(R.id.txt_status_discription);
        but_submit = (Button) findViewById(R.id.but_submit);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.exit(0);
    }
}
