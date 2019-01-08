package com.satvatinfosole.ssss.sangam.events;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.satvatinfosole.ssss.sangam.R;
import com.satvatinfosole.ssss.sangam.application.RealmController;
import com.satvatinfosole.ssss.sangam.constants.ApiConstants;
import com.satvatinfosole.ssss.sangam.constants.AppConstants;
import com.satvatinfosole.ssss.sangam.model.responseModel.EventResponseModel;
import com.satvatinfosole.ssss.sangam.presenter.EventPresenter;
import com.satvatinfosole.ssss.sangam.sharedpreference.PreferenceData;
import com.satvatinfosole.ssss.sangam.utility.CheckNetwork;
import com.satvatinfosole.ssss.sangam.view.EventViews;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import io.realm.Realm;

public class ShowEventActivity extends AppCompatActivity implements View.OnClickListener, EventViews {

    TextView txt_event_name, txt_month, txt_date, txt_event_type, txt_program_discription, txt_contact_person, txt_venue_address, txt_event_status;
    ImageView img_back_press, img_placeholder, img_call, img_share, img_event_image;

    String strEventNmae, strDate, strMonth, strEventType, strProgram, strVenue, strEventImage;
    String strShareContent = null;
    String strEventTypeShareContent = null;

    StringBuilder strAddress = new StringBuilder();
    StringBuilder strcontactPerson = new StringBuilder();
    String strcontactNumber = "";
    String strcontactEmail = "";
    String strcontactPlace = "";
    String event_status = null;
    ProgressDialog pDialog;
    CheckNetwork checkNetwork;
    PreferenceData preferenceData;
    EventPresenter eventPresenter;


    private static final int MAKE_CALL_PERMISSION_REQUEST_CODE = 1;

    Realm realm;


    private String months[] = new String[]{"January", "February", "March", "April", "May", "June", "July", "Augest", "September", "October", "November", "December"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm = RealmController.with(this).getRealm(); // opens "myrealm.realm"
        setContentView(R.layout.activity_show_event);
        Bundle extras = getIntent().getExtras();

        strEventNmae = extras.getString("event_name");
        strDate = extras.getString("event_date");
        strMonth = extras.getString("event_month");
        strProgram = extras.getString("event_program");
        strEventType = extras.getString("event_type");
        strVenue = extras.getString("event_venue");
        strEventImage = extras.getString("event_image");

        initUI();
        onClickLintner();
        setValue();
    }

    private void setValue() {
        txt_event_name.setText(strEventNmae);
        if (!strMonth.equalsIgnoreCase("null")) {
            txt_month.setText(strMonth);
        } else {
            txt_month.setText("");

        }
        txt_date.setText(strDate);
        txt_program_discription.setText(Html.fromHtml(String.valueOf(strProgram)));
        Log.e("Event Image url", ApiConstants.EVENT_PHOTO_URL + strEventImage);
        Picasso.with(this)
                .load(ApiConstants.EVENT_PHOTO_URL + strEventImage)
                .placeholder(R.drawable.ic_launcher)   // optional
                .error(R.drawable.ic_launcher)      // optional
                .into(img_event_image);
        if (strEventType.equalsIgnoreCase("1")) {
            txt_event_type.setText("Annual Event");
            strEventTypeShareContent = "Annual Event";
        } else if (strEventType.equalsIgnoreCase("2")) {
            txt_event_type.setText("Hastham");
            strEventTypeShareContent = "Hastham";
        } else if (strEventType.equalsIgnoreCase("3")) {
            txt_event_type.setText("Poosam");
            strEventTypeShareContent = "Poosam";
        } else if (strEventType.equalsIgnoreCase("4")) {
            txt_event_type.setText("Swamigal Visit");
            strEventTypeShareContent = "Swamigal Visit";
        } else {
            strEventTypeShareContent = "No Event";

        }
    }

    private void onClickLintner() {
        img_placeholder.setOnClickListener(this);
        img_call.setOnClickListener(this);
        img_share.setOnClickListener(this);
        img_back_press.setOnClickListener(this);
        img_event_image.setOnClickListener(this);
    }

    private void initUI() {

        checkNetwork = new CheckNetwork(this);
        preferenceData = new PreferenceData(this);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Please Wait ...");


        eventPresenter = new EventPresenter(ShowEventActivity.this, ShowEventActivity.this, realm);
        if (checkNetwork.isNetworkAvailable()) {
            eventPresenter.getEventList(strVenue);
        }

        txt_event_name = (TextView) findViewById(R.id.txt_event_name);
        txt_month = (TextView) findViewById(R.id.txt_month);
        txt_date = (TextView) findViewById(R.id.txt_date);
        txt_event_type = (TextView) findViewById(R.id.txt_event_type);
        txt_program_discription = (TextView) findViewById(R.id.txt_program_discription);
        txt_venue_address = (TextView) findViewById(R.id.txt_venue_address);
        txt_contact_person = (TextView) findViewById(R.id.txt_contact_person);
        txt_venue_address = (TextView) findViewById(R.id.txt_venue_address);
        txt_event_status = (TextView) findViewById(R.id.txt_event_status);
        img_placeholder = (ImageView) findViewById(R.id.img_placeholder);
        img_back_press = (ImageView) findViewById(R.id.img_back_press);
        img_call = (ImageView) findViewById(R.id.img_call);
        img_share = (ImageView) findViewById(R.id.img_share);
        img_event_image = (ImageView) findViewById(R.id.img_event_image);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_placeholder:
                goGooleeMap();
                break;
            case R.id.img_call:
                goCall();
                break;
            case R.id.img_share:
                goShare();
                break;
            case R.id.img_event_image:
                fullViewImage();
                break;
            case R.id.img_back_press:
                goBackActivity();
                break;
        }
    }

    private void fullViewImage() {
        AppConstants.FULL_IMAGE_URI=ApiConstants.EVENT_PHOTO_URL + strEventImage;
        startActivity(new Intent(getApplicationContext(),EventImageViewActivity.class));

    }

    private void goBackActivity() {
        finish();
    }

    private void goShare() {
        strShareContent = "HI,\n" + "\nEvent Name : " + strEventNmae + "\nProgram : " + strProgram + "\nLocation : " + strAddress + "\nContacct Person :" + strcontactPerson;
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, strShareContent);
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, "REG:" + strEventTypeShareContent + "-" + strEventNmae + "@" + strcontactPlace);
//        sendIntent.putExtra(Intent.EXTRA_EMAIL, new String[] { strcontactEmail});
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    private void goCall() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            requestCallPermission();
        } else {
            if (strcontactNumber.equalsIgnoreCase("")) {
                if (strcontactNumber.length() == 10) {
                    startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:+91" + strcontactNumber)));
                } else {
                    if (strcontactNumber.length() == 11) {
                        startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:+91" + strcontactNumber)));
                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid contact number", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                Toast.makeText(getApplicationContext(), "Contact number not available", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void goGooleeMap() {
        String uri = "http://maps.google.co.in/maps?q=" + strAddress;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(intent);
    }

    private void requestCallPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CALL_PHONE)) {
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE},
                    MAKE_CALL_PERMISSION_REQUEST_CODE);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MAKE_CALL_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                }
                return;
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
        JSONObject jObj = null;
        JSONObject jObj_event = null;

        try {
            jObj = new JSONObject(responseSuccess);

            String status = jObj.getString("status");
            String message = jObj.getString("message");
            if (status.equalsIgnoreCase("1")) {

                String event_List = jObj.getString("Event_Venue");

                JSONArray jObj_event_array = new JSONArray(event_List);


                for (int i = 0; i < jObj_event_array.length(); i++) {
                    jObj_event = new JSONObject(jObj_event_array.get(i).toString());
                    strAddress.append(jObj_event.getString("ev_address1")).append("\n")
                            .append(jObj_event.getString("ev_address2")).append(",\n")
                            .append(jObj_event.getString("ev_city")).append(",\t")
                            .append(jObj_event.getString("ev_state")).append(",\n")
                            .append(jObj_event.getString("ev_pincode"));
                    strcontactPerson.append(jObj_event.getString("ev_contactperson")).append(",\n")
                            .append(jObj_event.getString("ev_contactno")).append(",\n")
                            .append(jObj_event.getString("ev_emailid"));
                    event_status = jObj_event.getString("ev_status");
                    strcontactNumber = jObj_event.getString("ev_contactno");
                    strcontactEmail = jObj_event.getString("ev_emailid");
                    strcontactPlace = jObj_event.getString("ev_city");

                }


                txt_venue_address.setText(strAddress);
                txt_contact_person.setText(strcontactPerson);
                txt_event_status.setText(event_status);
            } else {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void showEventError(String responseError) {

    }
}
