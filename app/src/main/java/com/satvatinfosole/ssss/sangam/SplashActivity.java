package com.satvatinfosole.ssss.sangam;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.satvatinfosole.ssss.sangam.application.RealmController;
import com.satvatinfosole.ssss.sangam.constants.AppConstants;
import com.satvatinfosole.ssss.sangam.dashboard.DashBoardActivity;
import com.satvatinfosole.ssss.sangam.events.EventMainActivity;
import com.satvatinfosole.ssss.sangam.model.responseModel.EventResponseModel;
import com.satvatinfosole.ssss.sangam.presenter.EventPresenter;
import com.satvatinfosole.ssss.sangam.presenter.RegistreationPresenter;
import com.satvatinfosole.ssss.sangam.sharedpreference.PreferenceData;
import com.satvatinfosole.ssss.sangam.signup.PrimaryDetailsActivity;
import com.satvatinfosole.ssss.sangam.utility.CheckNetwork;
import com.satvatinfosole.ssss.sangam.view.EventViews;
import com.satvatinfosole.ssss.sangam.view.RegistrationViews;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;

import io.realm.Realm;

import static android.Manifest.permission.ACCESS_NETWORK_STATE;
import static android.Manifest.permission.ACCESS_WIFI_STATE;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class SplashActivity extends AppCompatActivity implements EventViews {

    public static final int RequestPermissionCode = 1;

    ProgressDialog pDialog;
    CheckNetwork checkNetwork;
    PreferenceData preferenceData;
    EventPresenter eventPresenter;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm = RealmController.with(this).getRealm(); // opens "myrealm.realm"

        setContentView(R.layout.activity_splash);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        Log.e("Display matrixs:-", "width\t" + width + " \n height\t" + height);

        Calendar c = Calendar.getInstance();

        AppConstants.TODAY_DATE = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
        AppConstants.TODAY_MONTH = String.valueOf(c.get(Calendar.MONTH) + 1);
        AppConstants.TODAY_YEAR = String.valueOf(c.get(Calendar.YEAR));

        preferenceData = new PreferenceData(this);
        checkNetwork = new CheckNetwork(this);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Please Wait ...");

        if (checkRunTimePermission()) {
            if (checkNetwork.isNetworkAvailable()) {
                eventPresenter = new EventPresenter(this, this, realm);
                eventPresenter.getEventList();
            }
            if (preferenceData.getLogin()) {
                startActivity(new Intent(getApplicationContext(), DashBoardActivity.class));
            } else {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }

        } else {
            requestPermission();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        finish();
        System.exit(0);
    }

    private boolean checkRunTimePermission() {
        int CameraPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);
        int WriteExternalStoragePermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int InternetPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), INTERNET);
        int AccessNetworkStatePermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_NETWORK_STATE);
        int AccessWifiStatePermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_WIFI_STATE);
        int ReadExternalStoragePermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);


        return CameraPermissionResult == PackageManager.PERMISSION_GRANTED &&
                WriteExternalStoragePermissionResult == PackageManager.PERMISSION_GRANTED &&
                InternetPermissionResult == PackageManager.PERMISSION_GRANTED &&
                AccessNetworkStatePermissionResult == PackageManager.PERMISSION_GRANTED &&
                AccessWifiStatePermissionResult == PackageManager.PERMISSION_GRANTED &&
                ReadExternalStoragePermissionResult == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(SplashActivity.this, new String[]
                {
                        CAMERA,
                        WRITE_EXTERNAL_STORAGE,
                        INTERNET,
                        ACCESS_NETWORK_STATE,
                        ACCESS_WIFI_STATE,
                        READ_EXTERNAL_STORAGE
                }, RequestPermissionCode);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {

            case RequestPermissionCode:

                if (grantResults.length > 0) {

                    boolean CameraPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean WriteExternalStoragePermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean InternetPermission = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                    boolean AccessNetworkStatePermission = grantResults[3] == PackageManager.PERMISSION_GRANTED;
                    boolean AccessWifiStatePermission = grantResults[4] == PackageManager.PERMISSION_GRANTED;
                    boolean ReadExternalStoragePermissionResult = grantResults[5] == PackageManager.PERMISSION_GRANTED;

                    if (CameraPermission &&
                            WriteExternalStoragePermission &&
                            InternetPermission &&
                            AccessNetworkStatePermission &&
                            AccessWifiStatePermission &&
                            ReadExternalStoragePermissionResult) {

                        Toast.makeText(SplashActivity.this, "Permission Granted", Toast.LENGTH_LONG).show();
//                        initAnimation();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();


                    } else {
                        Toast.makeText(SplashActivity.this, "Permission Denied", Toast.LENGTH_LONG).show();

                    }
                }

                break;
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
    public void showEventSuccess(String responseSuccess) {

    }

    @Override
    public void showEventError(String responseError) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }
}
