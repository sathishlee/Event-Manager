package com.satvatinfosole.ssss.sangam.events;

import android.app.ProgressDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.satvatinfosole.ssss.sangam.R;
import com.satvatinfosole.ssss.sangam.adapter.EventListAdapter;
import com.satvatinfosole.ssss.sangam.application.RealmController;
import com.satvatinfosole.ssss.sangam.constants.AppConstants;
import com.satvatinfosole.ssss.sangam.dashboard.DashBoardActivity;
import com.satvatinfosole.ssss.sangam.interfaces.EventFillterDialogInterface;
import com.satvatinfosole.ssss.sangam.model.localDBModel.CalandarEvent;
import com.satvatinfosole.ssss.sangam.model.localDBModel.SangamEvent;
import com.satvatinfosole.ssss.sangam.model.responseModel.EventResponseModel;
import com.satvatinfosole.ssss.sangam.presenter.EventPresenter;
import com.satvatinfosole.ssss.sangam.sharedpreference.PreferenceData;
import com.satvatinfosole.ssss.sangam.utility.CheckNetwork;
import com.satvatinfosole.ssss.sangam.utility.EventFilterDailog;
import com.satvatinfosole.ssss.sangam.view.EventViews;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

public class EventListActivity extends AppCompatActivity implements EventViews, View.OnClickListener, EventFillterDialogInterface {
    private static final String tag = EventListActivity.class.getSimpleName();

    public ImageView img_back_press;
    RecyclerView recv_event_list;
    TextView txt_event_of_month, txt_event_not_available;
    LinearLayout ll_filter_view;

    //Api call
    ProgressDialog pDialog;
    CheckNetwork checkNetwork;
    PreferenceData preferenceData;
    EventPresenter eventPresenter;

    JSONArray jObj_event_array = null;


    //for list

    ImageButton img_grid_view;

    ArrayList<EventResponseModel.Event_List> arr_event_lists;
    EventResponseModel.Event_List model_event_lit;

    EventListAdapter eventListAdapter;

    Realm realm;
    RealmResults<SangamEvent> sangamEventRealmResults = null;


    EventFilterDailog eventFilterDailog;

    /*private String months[] = new String[]{"", "January", "February", "March", "April", "May",
            "June", "July", "Augest", "September", "October", "November", "December"};*/
    private String months_caps[] = new String[]{"", "JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY",
            "JUNE", "JULY", "AUGGEST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm = RealmController.with(this).getRealm(); // opens "myrealm.realm"
        setContentView(R.layout.activity_event_list);
        initUI();
        img_back_press.setOnClickListener(this);
        ll_filter_view.setOnClickListener(this);
        img_grid_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), EventMainActivity.class));
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initUI() {

        checkNetwork = new CheckNetwork(this);
        preferenceData = new PreferenceData(this);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Please Wait ...");

        arr_event_lists = new ArrayList<>();

        eventPresenter = new EventPresenter(EventListActivity.this, EventListActivity.this, realm);


        eventListAdapter = new EventListAdapter(this, arr_event_lists);

        img_back_press = (ImageView) findViewById(R.id.img_back_press);

        recv_event_list = (RecyclerView) findViewById(R.id.recv_event_list);
        img_grid_view = (ImageButton) findViewById(R.id.img_grid_view);

        ll_filter_view = (LinearLayout) findViewById(R.id.ll_filter_view);
        txt_event_of_month = (TextView) findViewById(R.id.txt_event_of_month);
        txt_event_not_available = (TextView) findViewById(R.id.txt_event_not_available);
        txt_event_not_available.setVisibility(View.GONE);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(EventListActivity.this);
        recv_event_list.setLayoutManager(mLayoutManager);
        recv_event_list.setItemAnimator(new DefaultItemAnimator());
        recv_event_list.setAdapter(eventListAdapter);
        txt_event_of_month.setText("Events of " + months_caps[Integer.parseInt(AppConstants.TODAY_MONTH)] + "-" + AppConstants.TODAY_YEAR);
        Log.e(EventListActivity.class.getSimpleName(), "month " + AppConstants.TODAY_MONTH + "\n year" + AppConstants.TODAY_YEAR);
        getEventList(AppConstants.TODAY_MONTH, AppConstants.TODAY_YEAR);
    }


    private void getEventList(String todayMonth, String todayYear) {

        realm.beginTransaction();
        sangamEventRealmResults = realm.where(SangamEvent.class).equalTo("event_sdate_month", todayMonth).equalTo("event_sdate_year", todayYear).findAll();
        Log.e(EventMainActivity.class.getSimpleName(), "Realm LIST SIZE" +
                sangamEventRealmResults.size());
        if (sangamEventRealmResults.size() != 0) {
            txt_event_of_month.setVisibility(View.VISIBLE);
            txt_event_not_available.setVisibility(View.GONE);

            for (int i = 0; i < sangamEventRealmResults.size(); i++) {
                SangamEvent sangamEvent = sangamEventRealmResults.get(i);
                model_event_lit = new EventResponseModel.Event_List();
                model_event_lit.setEvent_id(sangamEvent.getEvent_id());
                model_event_lit.setEvent_sdate(sangamEvent.getEvent_edate().toString());
                model_event_lit.setEvent_edate(sangamEvent.getEvent_edate().toString());
                model_event_lit.setEvent_name(sangamEvent.getEvent_name());
                model_event_lit.setEvent_type(sangamEvent.getEvent_type());
                model_event_lit.setEvent_venue(sangamEvent.getEvent_venue());
                model_event_lit.setEvent_program(sangamEvent.getEvent_program());
                model_event_lit.setEvent_image(sangamEvent.getEvent_image());
                model_event_lit.setEvent_status(sangamEvent.getEvent_status());

                arr_event_lists.add(model_event_lit);
                eventListAdapter.notifyDataSetChanged();
            }
        } else {
            arr_event_lists.clear();
            eventListAdapter.notifyDataSetChanged();
//            txt_event_of_month.setVisibility(View.GONE);
            txt_event_not_available.setVisibility(View.VISIBLE);
//            txt_event_not_available.setText("Events are not available on ");
        }
        realm.commitTransaction();

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
                String event_List = jObj.getString("Event_List");
                StringBuilder strFlashNews = new StringBuilder();
                jObj_event_array = new JSONArray(event_List);


                for (int i = 0; i < jObj_event_array.length(); i++) {
                    jObj_event = new JSONObject(jObj_event_array.get(i).toString());

                    model_event_lit = new EventResponseModel.Event_List();
                    model_event_lit.setEvent_id(jObj_event.getString("event_id"));
                    model_event_lit.setEvent_sdate(jObj_event.getString("event_sdate"));
                    model_event_lit.setEvent_edate(jObj_event.getString("event_edate"));
                    model_event_lit.setEvent_name(jObj_event.getString("event_name"));
                    model_event_lit.setEvent_type(jObj_event.getString("event_type"));
                    model_event_lit.setEvent_venue(jObj_event.getString("event_venue"));
                    model_event_lit.setEvent_program(jObj_event.getString("event_program"));
                    model_event_lit.setEvent_image(jObj_event.getString("event_image"));
                    model_event_lit.setEvent_status(jObj_event.getString("event_status"));

                    arr_event_lists.add(model_event_lit);
                    eventListAdapter.notifyDataSetChanged();
                }

            } else {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showEventError(String responseError) {
        Toast.makeText(getApplicationContext(), responseError, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back_press:
                goBackActivity();
                break;
            case R.id.ll_filter_view:
                showFilterDialog();
                break;
        }
    }

    private void showFilterDialog() {
        eventFilterDailog = new EventFilterDailog(this, this);
        eventFilterDailog.show();
    }

    private void goBackActivity() {
        startActivity(new Intent(getApplicationContext(), DashBoardActivity.class));
        finish();
    }


    @Override
    public void clickOk(int mSelectedMonth, int mSelectedYear, String strEventType) {
        Log.e(EventListActivity.class.getSimpleName(), "Selected month" + String.valueOf(mSelectedMonth) + "\t" + String.valueOf(mSelectedYear));
        txt_event_of_month.setText("Events of " + months_caps[mSelectedMonth] + "-" + mSelectedYear);
        getEventList(String.valueOf(mSelectedMonth), String.valueOf(mSelectedYear));

        eventFilterDailog.dismiss();
    }

    @Override
    public void clickCancel() {
        Toast.makeText(getApplicationContext(), "you click cancel", Toast.LENGTH_SHORT).show();
        eventFilterDailog.dismiss();
    }
}
