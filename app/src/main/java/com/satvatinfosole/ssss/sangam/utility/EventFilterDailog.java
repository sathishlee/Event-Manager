package com.satvatinfosole.ssss.sangam.utility;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.satvatinfosole.ssss.sangam.DailogInterface;
import com.satvatinfosole.ssss.sangam.R;
import com.satvatinfosole.ssss.sangam.constants.AppConstants;
import com.satvatinfosole.ssss.sangam.events.EventMainActivity;
import com.satvatinfosole.ssss.sangam.interfaces.EventFillterDialogInterface;

/**
 * Created by SATHISH on 11/21/2018.
 */
public class EventFilterDailog extends Dialog implements View.OnClickListener {
    public Context context;
    AutoCompleteTextView edtMonth, edtYear;
    String strMonth, strYear, strEventType;
    Spinner spEventType;
    TextView txt_error;
    public Button butCancel, butOk;
    EventFillterDialogInterface eventFillterDialogInterface;


    private String months[] = new String[]{"Select", "January", "February", "March", "April", "May",
            "June", "July", "Augest", "September", "October", "November", "December"};

    private String year[] = new String[]{"Select", "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020"};

    private String eventType[] = new String[]{"Select", "All", "Annual Event", "Hastham", "Poosam", "Swamigal Visit"};

    public EventFilterDailog(@NonNull Context context, EventFillterDialogInterface eventFillterDialogInterface) {
        super(context);
        this.context = context;
        this.eventFillterDialogInterface = eventFillterDialogInterface;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.but_ok:
                strMonth = edtMonth.getText().toString();
                strYear = edtYear.getText().toString();

                if (strMonth.equalsIgnoreCase("") && strYear.equalsIgnoreCase("")) {
                    if (strYear.equalsIgnoreCase("")) {
                        txt_error.setVisibility(View.VISIBLE);
                        txt_error.setText("Please select year");
                    } else if (strMonth.equalsIgnoreCase("")) {
                        txt_error.setVisibility(View.VISIBLE);
                        txt_error.setText("Please choose month");
                    }
                } else {
                    for (int i = 1; i < months.length; i++) {
                        if (strMonth.equalsIgnoreCase(months[i])) {
                            AppConstants.TODAY_MONTH =String.valueOf(i);
                            AppConstants.TODAY_YEAR = strYear;
                            eventFillterDialogInterface.clickOk(i, Integer.parseInt(strYear), strEventType);
                        } else {
                            Log.e(EventMainActivity.class.getSimpleName(), i + "th postition false");
                        }
                    }

                }





                /*if (strMonth.equalsIgnoreCase("Select") && strYear.equalsIgnoreCase("Select")) {
                    if (strYear.equalsIgnoreCase("Select")) {
                        txt_error.setVisibility(View.VISIBLE);
                        txt_error.setText("Please choose year");
                    } else if (strMonth.equalsIgnoreCase("Select")) {
                        txt_error.setVisibility(View.VISIBLE);
                        txt_error.setText("Please choose month");
                    }
                } else {
                    Log.e(EventFilterDailog.class.getSimpleName(),"Month"+strMonth+"\n year"+strYear);
//                    eventFillterDialogInterface.clickOk(strMonth, strYear, strEventType);
                    for (int i = 1; i < months.length; i++){
                        if (strMonth.equalsIgnoreCase(months[i])) {
                            eventFillterDialogInterface.clickOk(i,Integer.parseInt(strYear), strEventType);
                        }else{
                            Log.e(EventMainActivity.class.getSimpleName(),i+"th postition false");

                        }
                    }
                }*/
                break;
            case R.id.but_cancel:
                eventFillterDialogInterface.clickCancel();
                break;

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dailog_filter_view);
        initUi();
        onClickListner();

    }

    private void onClickListner() {
        butOk.setOnClickListener(this);
        butCancel.setOnClickListener(this);
    }

    private void initUi() {
        edtMonth = (AutoCompleteTextView) findViewById(R.id.edt_month);
        edtYear = (AutoCompleteTextView) findViewById(R.id.edt_year);
        edtMonth.setText(months[Integer.parseInt(AppConstants.TODAY_MONTH)]);
        edtYear.setText(AppConstants.TODAY_YEAR);
        spEventType = (Spinner) findViewById(R.id.sp_event_type);
        butCancel = (Button) findViewById(R.id.but_cancel);
        butOk = (Button) findViewById(R.id.but_ok);
        txt_error = (TextView) findViewById(R.id.txt_error);
        ArrayAdapter<String> arrayAdapter_month = new ArrayAdapter<String>
                (context, android.R.layout.simple_dropdown_item_1line, months);
        ArrayAdapter<String> arrayAdapter_year = new ArrayAdapter<String>
                (context, android.R.layout.simple_dropdown_item_1line, year);
        ArrayAdapter<String> arrayAdapter_event_type = new ArrayAdapter<String>
                (context, android.R.layout.simple_dropdown_item_1line, eventType);
        spEventType.setAdapter(arrayAdapter_event_type);
        spEventType.setPrompt("Select");
        edtMonth.setAdapter(arrayAdapter_month);
        edtYear.setAdapter(arrayAdapter_year);
        for (int i = 0; i < months.length; i++) {
            if (AppConstants.TODAY_MONTH.equalsIgnoreCase(months[i])) {
                edtMonth.setSelection(i);
            }
        }
        for (int i = 0; i < year.length; i++) {
            if (AppConstants.TODAY_MONTH.equalsIgnoreCase(year[i])) {
                edtYear.setSelection(i);
            }
        }
    }
}
