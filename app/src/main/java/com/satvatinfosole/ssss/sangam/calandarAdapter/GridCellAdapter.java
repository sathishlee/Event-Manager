package com.satvatinfosole.ssss.sangam.calandarAdapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.satvatinfosole.ssss.sangam.R;
import com.satvatinfosole.ssss.sangam.application.RealmController;
import com.satvatinfosole.ssss.sangam.events.EventMainActivity;
import com.satvatinfosole.ssss.sangam.events.ShowEventActivity;
import com.satvatinfosole.ssss.sangam.model.localDBModel.CalandarEvent;
import com.satvatinfosole.ssss.sangam.model.localDBModel.SangamEvent;
import com.satvatinfosole.ssss.sangam.model.responseModel.EventResponseModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by SATHISH on 10/24/2018.
 */
public class GridCellAdapter extends BaseAdapter {

    private static final String tag = "GridCellAdapter";
    private Context _context;
    private List<String> list;

    private List<String> event_date_list;
    private List<String> event_type_list;

    private List<String> event_position_list;
    EventResponseModel.Event_List event_list_model;

    private static final int DAY_OFFSET = 1;

    private String[] weekdays = new String[]{"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
    private String months[] = new String[]{"January", "February", "March", "April", "May", "June", "July", "Augest", "September", "October", "November", "December"};
    private String arr_months[] = new String[]{"January", "February", "March", "April", "May", "June", "July", "Augest", "September", "October", "November", "December"};
    private int[] daysOfMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    private int daysInMonth;
    private int currentDayOfMonth;
    private int currentWeekDay;
    private Button gridcell;
    private TextView num_events_per_day;
    private HashMap<String, Integer> eventsPerMonthMap;
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MMM-yyyy");

    ArrayList<CalandarEvent> arr_calandar_event_lists;
    Realm realm;

    // Days in Current Month
    public GridCellAdapter(Context context, int textViewResourceId,
                           int month, int year, ArrayList<CalandarEvent> arr_calandar_event_lists, Realm realm) {

        /* public GridCellAdapter(Context context, int textViewResourceId,
                           int month, int year,ArrayList<EventResponseModel.Event_List> arr_event_lists) {*/
        super();
        this._context = context;
        this.list = new ArrayList<String>();

        this.realm = realm; // opens "myrealm.realm"

        this.event_date_list = new ArrayList<String>();
        this.event_type_list = new ArrayList<String>();

        this.event_position_list = new ArrayList<String>();

        this.arr_calandar_event_lists = arr_calandar_event_lists;

        Calendar calendar = Calendar.getInstance();
        setCurrentDayOfMonth(calendar.get(Calendar.DAY_OF_MONTH));
        setCurrentWeekDay(calendar.get(Calendar.DAY_OF_WEEK));


        /*if (arr_calandar_event_lists.size() != 0) {
            for (int i = 0; i < arr_calandar_event_lists.size(); i++) {

                if (arr_calandar_event_lists.get(i).getEvent_sdate().equalsIgnoreCase(arr_calandar_event_lists.get(i).getEvent_sdate())) {
                    event_date_list.add(arr_calandar_event_lists.get(i).getEvent_sdate());
                    event_type_list.add(arr_calandar_event_lists.get(i).getEvent_type());
                } else {
//                    event_date_list.add(arr_calandar_event_lists.get(i).getEvent_edate());
                    event_type_list.add(arr_calandar_event_lists.get(i).getEvent_type());
                }
            }
        } else {
        }*/

// Print Month
        printMonth(month, year);


// Find Number of Events
        eventsPerMonthMap = findNumberOfEventsPerMonth(year, month);
    }


    private void printMonth(int mm, int yy) {
        EventMainActivity.txt_month_year.setText(months[mm - 1] + " " + yy);
        int trailingSpaces = 0;
        int daysInPrevMonth = 0;
        int prevMonth = 0;
        int prevYear = 0;
        int nextMonth = 0;
        int nextYear = 0;

        int currentMonth = mm - 1;
//        int currentMonth = mm ;
        String currentMonthName = getMonthAsString(currentMonth);
        daysInMonth = getNumberOfDaysOfMonth(currentMonth);
        GregorianCalendar cal = new GregorianCalendar(yy, currentMonth, 1);
        if (currentMonth == 11) {
            prevMonth = currentMonth - 1;
            daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
            nextMonth = 0;
            prevYear = yy;
            nextYear = yy + 1;

        } else if (currentMonth == 0) {
            prevMonth = 11;
            prevYear = yy - 1;
            nextYear = yy;
            daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
            nextMonth = 1;
        } else {
            prevMonth = currentMonth - 1;
            nextMonth = currentMonth + 1;
            nextYear = yy;
            prevYear = yy;
            daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);

        }

        int currentWeekDay = cal.get(Calendar.DAY_OF_WEEK) - 1;
        trailingSpaces = currentWeekDay;

        if (cal.isLeapYear(cal.get(Calendar.YEAR)))
            if (mm == 2)
                ++daysInMonth;
            else if (mm == 3)
                ++daysInPrevMonth;

// Trailing Month days
        for (int i = 0; i < trailingSpaces; i++) {

            list.add(String
                    .valueOf((daysInPrevMonth - trailingSpaces + DAY_OFFSET)
                            + i)
                    + "-GREY"
                    + "-"
                    + getMonthAsString(prevMonth)
                    + "-"
                    + prevYear);
        }

// Current Month Days
        for (int i = 1; i <= daysInMonth; i++) {
            if (i == getCurrentDayOfMonth()) {
                list.add(String.valueOf(i) + "-BLUE" + "-"
                        + getMonthAsString(currentMonth) + "-" + yy);
            } else {
                // add sangam event into calandar grid view
                boolean dateset = false;

                for (int j = 0; j < arr_calandar_event_lists.size(); j++) {
                    Log.e(GridCellAdapter.class.getSimpleName(),j+"th event start date"+arr_calandar_event_lists.get(j).getEvent_sdate());
                    Log.e(GridCellAdapter.class.getSimpleName(),j+"th event end date"+arr_calandar_event_lists.get(j).getEvent_edate());

                    if (arr_calandar_event_lists.get(j).getEvent_sdate().equalsIgnoreCase(arr_calandar_event_lists.get(j).getEvent_sdate())) {
                        Log.e(GridCellAdapter.class.getSimpleName(),j+"one day event"+arr_calandar_event_lists.get(j).getEvent_edate());
                        if (yy == getYearformList(arr_calandar_event_lists.get(j).getEvent_sdate().toString())) {
                            if (mm == getMonthformList(arr_calandar_event_lists.get(j).getEvent_sdate().toString())) {
                                if (i == getDatefromList(arr_calandar_event_lists.get(j).getEvent_sdate().toString())) {

                                    if (arr_calandar_event_lists.get(j).getEvent_type().equalsIgnoreCase("2")) {
                                        dateset = true;
                                        list.add(String.valueOf(i) + "-Annul" + "-" + getMonthAsString(currentMonth) + "-" + yy);
                                    } else if (arr_calandar_event_lists.get(j).getEvent_type().equalsIgnoreCase("2")) {
                                        dateset = true;
                                        list.add(String.valueOf(i) + "-hastham" + "-" + getMonthAsString(currentMonth) + "-" + yy);
                                    } else if (arr_calandar_event_lists.get(j).getEvent_type().equalsIgnoreCase("3")) {
                                        dateset = true;
                                        list.add(String.valueOf(i) + "-poosam" + "-" + getMonthAsString(currentMonth) + "-" + yy);
                                    } else if (arr_calandar_event_lists.get(j).getEvent_type().equalsIgnoreCase("4")) {
                                        dateset = true;
                                        list.add(String.valueOf(i) + "-swamigal visit" + "-" + getMonthAsString(currentMonth) + "-" + yy);
                                    }
                                }
                            }

                        }
                    } else {
                        if (yy == getYearformList(arr_calandar_event_lists.get(j).getEvent_edate().toString())) {
                            if (mm == getMonthformList(arr_calandar_event_lists.get(j).getEvent_edate().toString())) {
                                if (i == getDatefromList(arr_calandar_event_lists.get(j).getEvent_edate().toString())) {

                                    if (arr_calandar_event_lists.get(j).getEvent_type().equalsIgnoreCase("2")) {
                                        dateset = true;
                                        list.add(String.valueOf(i) + "-Annul" + "-" + getMonthAsString(currentMonth) + "-" + yy);
                                    } else if (arr_calandar_event_lists.get(j).getEvent_type().equalsIgnoreCase("2")) {
                                        dateset = true;
                                        list.add(String.valueOf(i) + "-hastham" + "-" + getMonthAsString(currentMonth) + "-" + yy);
                                    } else if (arr_calandar_event_lists.get(j).getEvent_type().equalsIgnoreCase("3")) {
                                        dateset = true;
                                        list.add(String.valueOf(i) + "-poosam" + "-" + getMonthAsString(currentMonth) + "-" + yy);
                                    } else if (arr_calandar_event_lists.get(j).getEvent_type().equalsIgnoreCase("4")) {
                                        dateset = true;
                                        list.add(String.valueOf(i) + "-swamigal visit" + "-" + getMonthAsString(currentMonth) + "-" + yy);
                                    }
                                }
                            }

                        }
                    }





                }


                if (!dateset) {
                    list.add(String.valueOf(i) + "-WHITE" + "-"
                            + getMonthAsString(currentMonth) + "-" + yy);
                }
            }
        }

// Leading Month days
        for (int i = 0; i < list.size() % 7; i++) {
            Log.d(tag, "NEXT MONTH:= " + getMonthAsString(nextMonth));
            list.add(String.valueOf(i + 1) + "-GREY" + "-"
                    + getMonthAsString(nextMonth) + "-" + nextYear);
        }
    }

    private int getYearformList(String s) {
        String[] day = s.split("-");
        String strDay = day[2];
        return Integer.parseInt(strDay);

    }

    private int getDatefromList(String s) {
        String[] day = s.split("-");
        String strDay = day[0];
        return Integer.parseInt(strDay);
    }

    private int getMonthformList(String s) {
        String[] day = s.split("-");
        String strDay = day[1];
        return Integer.parseInt(strDay);

    }

    /**
     * NOTE: YOU NEED TO IMPLEMENT THIS PART Given the YEAR, MONTH, retrieve
     * ALL entries from a SQLite database for that month. Iterate over the
     * List of All entries, and get the dateCreated, which is converted into
     * day.
     *
     * @param year
     * @param month
     * @return
     */
    private HashMap<String, Integer> findNumberOfEventsPerMonth(int year,
                                                                int month) {
        HashMap<String, Integer> map = new HashMap<String, Integer>();


        return map;
    }


    private String getMonthAsString(int i) {
        return months[i];
    }

    private String getWeekDayAsString(int i) {
        return weekdays[i];
    }

    private int getNumberOfDaysOfMonth(int i) {
        return daysOfMonth[i];
    }


    public String getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) _context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.item_day_cel, parent, false);
        }
// Get a reference to the Day gridcell
        gridcell = (Button) row.findViewById(R.id.calendar_day_gridcell);
        gridcell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String date_month_year = (String) view.getTag();
                int realmSize = 0;

// get value from realm db based on selected date

                getSelectedDate(date_month_year);
                getSelectedMonth(date_month_year);
                getSelectedYear(date_month_year);
                if (realm.isInTransaction()) {
                    realm.cancelTransaction();
                }
                realm.beginTransaction();
                String strEvent_id, strEvent_name = null, strEvent_program, strEvent_type = null, strEvent_venue = null, strEvent_image = null;
                StringBuilder strdate = new StringBuilder();

                strdate.append(getSelectedDate(date_month_year))
                        .append("-")
                        .append(getSelectedMonth(date_month_year))
                        .append("-")
                        .append(getSelectedYear(date_month_year));

                RealmResults<SangamEvent> realmResults = realm.where(SangamEvent.class).equalTo("event_sdate", strdate.toString()).findAll();
//                RealmResults<SangamEvent> realmResults = realm.where(SangamEvent.class).equalTo("event_sdate","16-05-2013").findAll();

                realmSize = realmResults.size();
                if (realmResults.size() != 0) {
                    for (int i = 0; i < realmResults.size(); i++) {
                        strEvent_id = realmResults.get(i).getEvent_id();
                        strEvent_name = realmResults.get(i).getEvent_name();
                        strEvent_program = realmResults.get(i).getEvent_program();
                        strEvent_type = realmResults.get(i).getEvent_type();
                        strEvent_venue = realmResults.get(i).getEvent_venue();
                        strEvent_image = realmResults.get(i).getEvent_image();

                    }
                } else {
                }
                realm.commitTransaction();
                try {
                    if (realmSize != 0) {
                        Intent intent = new Intent(_context.getApplicationContext(), ShowEventActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("event_date", getSelectedDate(date_month_year));
                        bundle.putString("event_month", getSelectedMonth(date_month_year));
                        bundle.putString("event_name", strEvent_name);
                        bundle.putString("event_program", strEvent_name);
                        bundle.putString("event_type", strEvent_type);
                        bundle.putString("event_venue", strEvent_venue);
                        bundle.putString("event_image", strEvent_image);
                        intent.putExtras(bundle);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        _context.startActivity(intent);
                    } else {
                        Toast.makeText(_context.getApplicationContext(), "No Event on Selected Date", Toast.LENGTH_SHORT).show();
                    }

                    Date parsedDate = dateFormatter.parse(date_month_year);

//                    Log.d(tag, "Parsed Date: " + parsedDate.toString());

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        String[] day_color = list.get(position).split("-");
        String theday = day_color[0];
        String themonth = day_color[2];
        String theyear = day_color[3];
        num_events_per_day = (TextView) row
                .findViewById(R.id.num_events_per_day);

        // Set the Day GridCell
        gridcell.setText(theday);
        gridcell.setTag(theday + "-" + themonth + "-" + theyear);

        if (day_color[1].equals("GREY")) {
            gridcell.setTextColor(_context.getResources()
                    .getColor(R.color.calandar_background));
            gridcell.setBackgroundColor(_context.getResources().getColor(R.color.calandar_background));

        }
        if (day_color[1].equals("WHITE")) {
            gridcell.setTextColor(_context.getResources().getColor(
                    R.color.black));
            gridcell.setBackgroundColor(_context.getResources().getColor(R.color.calandar_background));

        }
        if (day_color[1].equals("BLUE")) {
            gridcell.setTextColor(_context.getResources().getColor(R.color.black));
            gridcell.setBackgroundColor(_context.getResources().getColor(R.color.divider));
        }
        if (day_color[1].equals("BLUE1")) {
            gridcell.setTextColor(_context.getResources().getColor(R.color.black));
            gridcell.setBackgroundColor(_context.getResources().getColor(R.color.colorPrimary));
        }
        if (day_color[1].equals("Annul")) {
            gridcell.setTextColor(_context.getResources().getColor(R.color.black));
            gridcell.setBackgroundColor(_context.getResources().getColor(R.color.color_annul_event));
        }
        if (day_color[1].equals("hastham")) {
            gridcell.setTextColor(_context.getResources().getColor(R.color.black));
            gridcell.setBackgroundColor(_context.getResources().getColor(R.color.color_hastham));
        }
        if (day_color[1].equals("swamigal visit")) {
            gridcell.setTextColor(_context.getResources().getColor(R.color.black));
            gridcell.setBackgroundColor(_context.getResources().getColor(R.color.color_swamigal_visit));
        }
        if (day_color[1].equals("poosam")) {
            gridcell.setTextColor(_context.getResources().getColor(R.color.black));
            gridcell.setBackgroundColor(_context.getResources().getColor(R.color.color_poosam));
        }
        return row;
    }

    private String getSelectedYear(String date_month_year) {
        String[] day = date_month_year.split("-");
        String strDay = day[2];
        return strDay;
    }

    private String getSelectedMonth(String date_month_year) {
        String selectedMonth = "0";
        String[] day = date_month_year.split("-");
        String strDay = day[1];
        for (int i = 0; i < arr_months.length; i++) {
            if (strDay.equalsIgnoreCase(arr_months[i])) {
                selectedMonth = i + 1 + "";
            }
            if (selectedMonth.length() == 1) {
                selectedMonth = "0" + selectedMonth;
            }
        }
        return selectedMonth;
    }

    private String getSelectedDate(String date_month_year) {
        String[] day = date_month_year.split("-");
        String strDay = day[0];
        if (strDay.length() == 1) {
            strDay = "0" + strDay;
        }
        return strDay;
    }


    public int getCurrentDayOfMonth() {
        return currentDayOfMonth;
    }

    private void setCurrentDayOfMonth(int currentDayOfMonth) {
        this.currentDayOfMonth = currentDayOfMonth;
    }

    public void setCurrentWeekDay(int currentWeekDay) {
        this.currentWeekDay = currentWeekDay;
    }

    public int getCurrentWeekDay() {
        return currentWeekDay;
    }
}
