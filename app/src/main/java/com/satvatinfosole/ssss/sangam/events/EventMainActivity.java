package com.satvatinfosole.ssss.sangam.events;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.satvatinfosole.ssss.sangam.R;
import com.satvatinfosole.ssss.sangam.application.RealmController;
import com.satvatinfosole.ssss.sangam.calandarAdapter.GridCellAdapter;
import com.satvatinfosole.ssss.sangam.constants.AppConstants;
import com.satvatinfosole.ssss.sangam.dashboard.DashBoardActivity;
import com.satvatinfosole.ssss.sangam.interfaces.EventFillterDialogInterface;
import com.satvatinfosole.ssss.sangam.model.localDBModel.CalandarEvent;
import com.satvatinfosole.ssss.sangam.model.localDBModel.SangamEvent;
import com.satvatinfosole.ssss.sangam.presenter.EventPresenter;
import com.satvatinfosole.ssss.sangam.sharedpreference.PreferenceData;
import com.satvatinfosole.ssss.sangam.utility.CheckNetwork;
import com.satvatinfosole.ssss.sangam.utility.EventFilterDailog;
import com.satvatinfosole.ssss.sangam.view.DashbordViews;
import com.satvatinfosole.ssss.sangam.view.EventViews;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmResults;


public class EventMainActivity extends AppCompatActivity implements View.OnClickListener, EventViews, EventFillterDialogInterface {
    private static final String tag = EventMainActivity.class.getSimpleName();

    ImageButton imgGridView, imgListView;
    ImageView imgShowEventActivity, img_back_press;
    CalendarView mCalendarView;
    TextView txtEventToday, txtTime, txtAmPm, txt_eventName, txtEventDiscription;
    String strEventId, strEventName, strEventType, strEventProgram, strEventVenue, strEventImage, strEventStatus;
    LinearLayout ll_filter_view;

    public static TextView txt_month_year;
    ImageView but_previus_month, but_next_montn;
    GridView grid_calander;
    private GridCellAdapter adapter;
    private Calendar _calendar;

    TextView txt_today_event;
    ProgressDialog pDialog;
    CheckNetwork checkNetwork;
    PreferenceData preferenceData;
    EventPresenter eventPresenter;
    JSONArray jObj_event_array = null;
    ArrayList<String> arr_event_lists;
    ArrayList<CalandarEvent> arr_calandar_event_lists;
    CalandarEvent calandarEvent;

    Realm realm;
    RealmResults<SangamEvent> sangamEventRealmResults = null;

    EventFilterDailog eventFilterDailog;
    @SuppressLint("NewApi")

    private static final String dateTemplate = "MMMM yyyy";
    private int day, month, year;
    private String months[] = new String[]{"January", "February", "March", "April", "May",
            "June", "July", "Augest", "September", "October", "November", "December"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm = RealmController.with(this).getRealm(); // opens "myrealm.realm"
        setContentView(R.layout.activity_event_main);
        initUI();
        onClickListner();
    }

    private void onClickListner() {
        img_back_press.setOnClickListener(this);
        ll_filter_view.setOnClickListener(this);

        imgGridView.setOnClickListener(this);
        imgShowEventActivity.setOnClickListener(this);
        imgListView.setOnClickListener(this);

        but_previus_month.setOnClickListener(this);
        but_next_montn.setOnClickListener(this);

    }

    private void initUI() {
        checkNetwork = new CheckNetwork(this);
        preferenceData = new PreferenceData(this);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Please Wait ...");

        arr_event_lists = new ArrayList<>();
        arr_calandar_event_lists = new ArrayList<>();

        eventPresenter = new EventPresenter(EventMainActivity.this,
                EventMainActivity.this, realm);

        if (checkNetwork.isNetworkAvailable()) {
//            eventPresenter.getEventList();
        }

        img_back_press = (ImageView) findViewById(R.id.img_back_press);

        imgGridView = (ImageButton) findViewById(R.id.img_grid_view);
        imgListView = (ImageButton) findViewById(R.id.img_list_view);
        imgShowEventActivity = (ImageView) findViewById(R.id.img_show_envent_activity);
        mCalendarView = (CalendarView) findViewById(R.id.cldr_view);
        txtEventToday = (TextView) findViewById(R.id.txt_event_today);
        txtTime = (TextView) findViewById(R.id.txt_time);
        txtAmPm = (TextView) findViewById(R.id.txt_am_pm);
        txt_eventName = (TextView) findViewById(R.id.txt_event_name);
        txtEventDiscription = (TextView) findViewById(R.id.txt_event_discription);
        ll_filter_view = (LinearLayout) findViewById(R.id.ll_filter_view);
        txt_today_event = (TextView) findViewById(R.id.txt_today_event);
        txt_month_year = (TextView) findViewById(R.id.txt_month_year);
        but_previus_month = (ImageView) findViewById(R.id.but_previus_month);
        but_next_montn = (ImageView) findViewById(R.id.but_next_montn);
        grid_calander = (GridView) findViewById(R.id.grid_calander);
        _calendar = Calendar.getInstance(Locale.getDefault());
        day = _calendar.get(Calendar.DATE);
        month = _calendar.get(Calendar.MONTH);
        year = _calendar.get(Calendar.YEAR);
        month = month + 1;
        txtEventToday.setText("Event on " + day + "-" + month + "-" + year);
        realm.beginTransaction();
        sangamEventRealmResults = realm.where(SangamEvent.class).findAll();
        for (int i = 0; i < sangamEventRealmResults.size(); i++) {
            SangamEvent sangamEvent = sangamEventRealmResults.get(i);
            calandarEvent = new CalandarEvent();
            calandarEvent.setEvent_sdate(sangamEvent.getEvent_sdate().toString());

            if (sangamEvent.getEvent_sdate().toString().equalsIgnoreCase(AppConstants.TODAY_DATE + "-" + AppConstants.TODAY_MONTH + "-" + AppConstants.TODAY_YEAR)) {
                txt_eventName.setText(sangamEvent.getEvent_name());
                txtEventDiscription.setText(Html.fromHtml(sangamEvent.getEvent_program()));
                txt_today_event.setText(sangamEvent.getEvent_name() + Html.fromHtml(sangamEvent.getEvent_program()));
            } else {
                txt_today_event.setText("No Events Today");
            }
            calandarEvent.setEvent_edate(sangamEvent.getEvent_edate().toString());
            calandarEvent.setEvent_id(sangamEvent.getEvent_id());
            calandarEvent.setEvent_type(sangamEvent.getEvent_type());
            calandarEvent.setEvent_name(sangamEvent.getEvent_name());
            calandarEvent.setEvent_venue(sangamEvent.getEvent_venue());
            calandarEvent.setEvent_program(sangamEvent.getEvent_program());
            calandarEvent.setEvent_image(sangamEvent.getEvent_image());
            calandarEvent.setEvent_status(sangamEvent.getEvent_status());

            strEventId = sangamEvent.getEvent_id();
            strEventName = sangamEvent.getEvent_name();
            strEventType = sangamEvent.getEvent_type();
            strEventVenue = sangamEvent.getEvent_venue();
            strEventProgram = sangamEvent.getEvent_program();
            strEventImage = sangamEvent.getEvent_image();
            strEventStatus = sangamEvent.getEvent_status();
            arr_calandar_event_lists.add(calandarEvent);
        }

        realm.commitTransaction();

        adapter = new GridCellAdapter(getApplicationContext(),
                R.id.calendar_day_gridcell, month, _calendar.get(Calendar.YEAR), arr_calandar_event_lists, realm
        );
        adapter.notifyDataSetChanged();
        grid_calander.setAdapter(adapter);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_grid_view:
                goCalandarView();
                break;
            case R.id.img_list_view:
                goListView();
                break;
            case R.id.img_show_envent_activity:
                goDailogView();
                break;
            case R.id.but_previus_month:
                goPreviusMonth();
                break;
            case R.id.but_next_montn:
                goNextMonth();
                break;
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

    private void goCalandarView() {
        startActivity(new Intent(getApplicationContext(), EventMainActivity.class));
    }

    private void goListView() {
        startActivity(new Intent(getApplicationContext(), EventListActivity.class));
    }

    private void goDailogView() {



        Intent intent = new Intent(getApplicationContext(), ShowEventActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("event_date", AppConstants.TODAY_DATE);
        bundle.putString("event_month", AppConstants.TODAY_MONTH);
        bundle.putString("event_name", strEventName);
        bundle.putString("event_program", strEventProgram);
        bundle.putString("event_type", strEventType);
        bundle.putString("event_venue", strEventVenue);
        bundle.putString("event_image", strEventImage);
        intent.putExtras(bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void goNextMonth() {
        if (month > 11) {
            month = 1;
            year = year + 1;
            setGridCellAdapterToDate(month, year);

        } else {
            month = month + 1;
            setGridCellAdapterToDate(month, year);

        }
    }

    private void goPreviusMonth() {
        if (month <= 1) {
            month = 12;
            year = year - 1;
        } else {
            month = month - 1;
        }
        setGridCellAdapterToDate(month, year);
    }

    /**
     * @param month
     * @param year
     */
    private void setGridCellAdapterToDate(int month, int year) {
        adapter = new GridCellAdapter(getApplicationContext(),
                R.id.calendar_day_gridcell, month, year, arr_calandar_event_lists, realm);
        adapter.notifyDataSetChanged();
        grid_calander.setAdapter(adapter);
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
        try {
            jObj = new JSONObject(responseSuccess);

            String status = jObj.getString("status");
            String message = jObj.getString("message");
            if (status.equalsIgnoreCase("1")) {
                String event_List = jObj.getString("Event_List");
                StringBuilder strFlashNews = new StringBuilder();
                jObj_event_array = new JSONArray(event_List);

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
    public void clickOk(int mSelectedMonth, int mSelectedYear, String strEventType) {
        setGridCellAdapterToDate(mSelectedMonth, mSelectedYear);
        eventFilterDailog.dismiss();
    }

    @Override
    public void clickCancel() {
        eventFilterDailog.dismiss();
//        Toast.makeText(getApplicationContext(), "you click cancel", Toast.LENGTH_SHORT).show();
    }
}
