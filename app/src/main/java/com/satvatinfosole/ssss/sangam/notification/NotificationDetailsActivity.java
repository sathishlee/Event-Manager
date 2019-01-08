package com.satvatinfosole.ssss.sangam.notification;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.satvatinfosole.ssss.sangam.R;

public class NotificationDetailsActivity extends AppCompatActivity implements View.OnClickListener {
public  ImageView img_back_press;
public TextView txt_notification_title,txt_notification_message,txt_notification_date_time,txt_notification_type;
public String strNotifiyTitle,strNotifiyMessage,strNotifiyTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_details);
        getValuefromIntent();
        initUI();
        onClickListner();
        setvaluetoUI();
    }

    private void setvaluetoUI() {
        txt_notification_title.setText(strNotifiyTitle);
        txt_notification_message.setText(strNotifiyMessage);
        txt_notification_date_time.setText(strNotifiyTime);
        txt_notification_type.setText("ALERT DETAILS");
    }

    private void getValuefromIntent() {
        Bundle extras = getIntent().getExtras();

        strNotifiyTitle = extras.getString("title").toString();
        strNotifiyMessage =extras.getString("message").toString();
        strNotifiyTime = extras.getString("date_time").toLowerCase();
    }

    private void onClickListner() {
        img_back_press.setOnClickListener(this);
    }

    private void initUI() {
         img_back_press =(ImageView)findViewById(R.id.img_back_press);
         txt_notification_title =( TextView)findViewById(R.id.txt_notification_title);
        txt_notification_message =( TextView)findViewById(R.id.txt_notification_message);
        txt_notification_date_time =( TextView)findViewById(R.id.txt_notification_date_time);
        txt_notification_type =( TextView)findViewById(R.id.txt_notification_type);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_back_press: goBackActivity(); break;
        }
    }

    private void goBackActivity() {
        startActivity(new Intent(getApplicationContext(),NotificationListActivity.class));
        finish();
    }
}
