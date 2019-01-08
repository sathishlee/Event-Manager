package com.satvatinfosole.ssss.sangam.utility;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.satvatinfosole.ssss.sangam.DailogInterface;
import com.satvatinfosole.ssss.sangam.R;

/**
 * Created by SATHISH on 10/20/2018.
 */
public class CustomDialogBox extends Dialog implements View.OnClickListener {
    public Context context;
    public Button butCancel, butOk,butSingleOk;
    public TextView txtAlertTitle,txtAlertMsg;
    public LinearLayout llButtonBlock;
    ImageView imagAlertIcon;
    String strTitle;
    String strMessage;
    boolean displyButton;
    String strButton1Name;
    String strButton2Name;

    DailogInterface dailogInterface;


    public CustomDialogBox(@NonNull Context context,DailogInterface dailogInterface) {
        super(context);
        this.context =context;
        this.dailogInterface=dailogInterface;
    }

    public CustomDialogBox(@NonNull Context context,DailogInterface dailogInterface,String strTitle,String strMessage,boolean displyButton,String strButton1Name, String strButton2Name) {
        super(context);
        this.context =context;
        this.dailogInterface=dailogInterface;
        this.strTitle =strTitle;
        this.strMessage =strMessage;
        this.displyButton =displyButton;
        this.strButton1Name =strButton1Name;
        this.strButton2Name =strButton2Name;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_custom_dialog_box);
        initUi();
        onClickListner();

    }

    private void onClickListner() {
        butOk.setOnClickListener(this);
        butCancel.setOnClickListener(this);
        butSingleOk.setOnClickListener(this);
    }



    private void initUi() {
        imagAlertIcon = (ImageView) findViewById(R.id.img_alert_icon);
        txtAlertTitle = (TextView) findViewById(R.id.txt_alert_title);
        txtAlertMsg = (TextView) findViewById(R.id.txt_alert_msg);
        butOk = (Button) findViewById(R.id.but_ok);
        butCancel = (Button) findViewById(R.id.but_cancel);
        butSingleOk = (Button) findViewById(R.id.but_single_ok);
        llButtonBlock = (LinearLayout) findViewById(R.id.ll_button_block);
        imagAlertIcon.setVisibility(View.GONE);
        txtAlertTitle.setText(strTitle);
        txtAlertMsg.setText(strMessage);
        if (displyButton){
            llButtonBlock.setVisibility(View.VISIBLE);
            butOk.setText(strButton1Name);
            butCancel.setText(strButton2Name);
            butSingleOk.setVisibility(View.GONE);
        }else{
            llButtonBlock.setVisibility(View.GONE);
            butSingleOk.setVisibility(View.VISIBLE);
            }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.but_ok:
                dailogInterface.okonclick();
                break;

            case R.id.but_cancel:
                dailogInterface.cancelonclick();
                break;

                case R.id.but_single_ok:
                dailogInterface.cancelonclick();
                break;
        }

    }
    public void showWithIcon(String title, String message, Boolean displyButton, String strButton1Name, String strButton2Name){
//        imagAlertIcon.setVisibility(View.GONE);
        txtAlertTitle.setText(title);
        txtAlertMsg.setText(message);
        if (displyButton){
            butOk.setText(strButton1Name);
            butCancel.setText(strButton2Name);
        }else{
            butCancel.setText(strButton1Name);

        }
        show();
    }
}
