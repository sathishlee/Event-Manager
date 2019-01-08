package com.satvatinfosole.ssss.sangam;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.satvatinfosole.ssss.sangam.application.RealmController;
import com.satvatinfosole.ssss.sangam.events.EventMainActivity;
import com.satvatinfosole.ssss.sangam.sharedpreference.PreferenceData;
import com.satvatinfosole.ssss.sangam.utility.CheckNetwork;

import io.realm.Realm;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, DailogInterface {

    LinearLayout ll_LogIn, ll_EventCalendar;
    ProgressDialog pDialog;
    CheckNetwork checkNetwork;
    PreferenceData preferenceData;

    Realm realm;

    boolean doubleBackToExitPressedOnce = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        onClickListner();

    }

    private void onClickListner() {
        checkNetwork = new CheckNetwork(this);
        preferenceData = new PreferenceData(this);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Please Wait ...");

        ll_LogIn.setOnClickListener(this);
        ll_EventCalendar.setOnClickListener(this);
    }

    private void initUI() {
        ll_LogIn = (LinearLayout) findViewById(R.id.ll_log_in_view);
        ll_EventCalendar = (LinearLayout) findViewById(R.id.ll_event_view);

        realm = RealmController.with(this).getRealm(); // opens "myrealm.realm"
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_log_in_view:
                goLoginActivity();
                break;
            case R.id.ll_event_view:
                goEventCalendarActivity();
                break;
        }
    }

    private void goEventCalendarActivity() {
        startActivity(new Intent(getApplicationContext(), EventMainActivity.class));
    }

    private void goLoginActivity() {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }

    @Override
    public void okonclick() {

    }

    @Override
    public void cancelonclick() {

    }

    @Override
    public void onBackPressed() {

        if (doubleBackToExitPressedOnce) {

            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MainActivity.this);
            builder.setTitle(R.string.app_name);
            builder.setIcon(R.mipmap.ic_launcher);
            builder.setMessage("Are you Sure do you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
//                            finish();
                            Intent intent = new Intent(Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
                            startActivity(intent);
                            finish();
                            System.exit(0);

                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            android.app.AlertDialog alert = builder.create();
            alert.show();

        } else {
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
        }


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;

            }
        }, 2000);

        super.onBackPressed();
    }
}
