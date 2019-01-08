package com.satvatinfosole.ssss.sangam.notification;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.satvatinfosole.ssss.sangam.DailogInterface;
import com.satvatinfosole.ssss.sangam.R;
import com.satvatinfosole.ssss.sangam.adapter.NotificationListAdapter;
import com.satvatinfosole.ssss.sangam.dashboard.DashBoardActivity;
import com.satvatinfosole.ssss.sangam.feedback.FeedbackActivity;
import com.satvatinfosole.ssss.sangam.model.responseModel.NotificationModel;
import com.satvatinfosole.ssss.sangam.presenter.NotificationPresenter;
import com.satvatinfosole.ssss.sangam.sharedpreference.PreferenceData;
import com.satvatinfosole.ssss.sangam.utility.CheckNetwork;
import com.satvatinfosole.ssss.sangam.utility.CustomDialogBox;
import com.satvatinfosole.ssss.sangam.utility.TimeAgo;
import com.satvatinfosole.ssss.sangam.view.NotificationViews;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class NotificationListActivity extends AppCompatActivity implements NotificationViews, DailogInterface {

    ImageView img_back_press;
    RecyclerView recv_notification_list;

    ArrayList<NotificationModel> arr_notificationModel;
    NotificationModel notificationModel;
    NotificationListAdapter notificationListAdapter;

    //Api call
    ProgressDialog pDialog;
    CheckNetwork checkNetwork;
    PreferenceData preferenceData;
    NotificationPresenter notificationPresenter;

    CustomDialogBox customDialogBox;

    JSONArray jObj_event_array = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_list);
        initUI();
    }

    private void initUI() {

        checkNetwork = new CheckNetwork(this);
        preferenceData = new PreferenceData(this);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Please Wait ...");

        notificationPresenter = new NotificationPresenter(this, this);

        if (checkNetwork.isNetworkAvailable()) {
            notificationPresenter.getNotificationList();
        } else {
            customDialogBox = new CustomDialogBox(NotificationListActivity.this, this, "You are in Offline", "please check your internat connection", false, "OK", "CANCEL");
            customDialogBox.show();
        }

        img_back_press = (ImageView) findViewById(R.id.img_back_press);
        recv_notification_list = (RecyclerView) findViewById(R.id.recv_notification_list);

        arr_notificationModel = new ArrayList<>();
        notificationListAdapter = new NotificationListAdapter(arr_notificationModel, NotificationListActivity.this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(NotificationListActivity.this);
        recv_notification_list.setLayoutManager(mLayoutManager);
        recv_notification_list.setItemAnimator(new DefaultItemAnimator());
        recv_notification_list.setAdapter(notificationListAdapter);
    }

    @Override
    public void showProgress() {
        pDialog.show();
    }

    @Override
    public void hideProgress() {
        pDialog.dismiss();

    }

    @Override
    public void getNotificationListSuccess(String responseSuccess) {
        try {
            JSONObject mJsnobject = new JSONObject(responseSuccess);
            String status = mJsnobject.getString("status");
            String message = mJsnobject.getString("message");
            if (status.equalsIgnoreCase("1")) {
                JSONArray jsonArray = mJsnobject.getJSONArray("Notification_List");
                if (jsonArray.length() != 0) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        notificationModel = new NotificationModel();
                        notificationModel.setId(jsonObject.getString("id"));
                        notificationModel.setMessage(jsonObject.getString("message"));
//                        notificationModel.setDate(getDate(jsonObject.getString("date_time")));
                        notificationModel.setDate(TimeAgo.getTimeAgo(jsonObject.getString("date_time")));
//                        notificationModel.setTime(getTime(jsonObject.getString("date_time")));
                        notificationModel.setTitle(jsonObject.getString("title"));
//        notificationModel.setTime(jsonObject.getString("type"));
                        arr_notificationModel.add(notificationModel);
                    }
                    notificationListAdapter.notifyDataSetChanged();

                } else {
                    customDialogBox = new CustomDialogBox(NotificationListActivity.this, this, "Notitfications", " Not Found", false, "OK", "CANCEL");
                    customDialogBox.show();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getTime(String date_time) {
        String[] strTime = date_time.split(" ");
        return strTime[1].toString();
    }

    private String getDate(String date_time) {
        String[] strTime = date_time.split(" ");
        return strTime[0].toString();
    }

    @Override
    public void getNotificationListError(String responseError) {
        customDialogBox = new CustomDialogBox(NotificationListActivity.this, this, "Server Response", responseError, false, "OK", "CANCEL");
        customDialogBox.show();
    }

    @Override
    public void okonclick() {
        customDialogBox.dismiss();
        startActivity(new Intent(getApplicationContext(), DashBoardActivity.class));
    }

    @Override
    public void cancelonclick() {
        customDialogBox.dismiss();
        startActivity(new Intent(getApplicationContext(), DashBoardActivity.class));

    }
}
