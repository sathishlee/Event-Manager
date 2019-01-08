package com.satvatinfosole.ssss.sangam.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.satvatinfosole.ssss.sangam.R;
import com.satvatinfosole.ssss.sangam.events.EventListActivity;
import com.satvatinfosole.ssss.sangam.events.ShowEventActivity;
import com.satvatinfosole.ssss.sangam.model.responseModel.EventResponseModel;
import com.satvatinfosole.ssss.sangam.utility.CustomDialogBox;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SATHISH on 10/25/2018.
 */
public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.ViewHolder> {

    private ArrayList<EventResponseModel.Event_List> eventLists;
    Activity activity;

    private String months[] = new String[]{"","JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
    private View view;

    public EventListAdapter(Activity activity, ArrayList<EventResponseModel.Event_List> eventLists) {
        this.eventLists = eventLists;
        this.activity = activity;
    }



    @NonNull
    @Override
    public EventListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_event_list, viewGroup, false);
        return new ViewHolder(view);

        /* View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tt_mothers, parent, false);
        return new ViewHolder(view);*/
    }

    @Override
    public void onBindViewHolder(@NonNull EventListAdapter.ViewHolder viewHolder, final int i) {
        final EventResponseModel.Event_List event_list_model = eventLists.get(i);
        viewHolder.txt_date.setText(getDay(event_list_model.getEvent_sdate()));
        viewHolder.txt_month.setText(getMonth(event_list_model.getEvent_sdate()));
        viewHolder.txt_event_name.setText(event_list_model.getEvent_name());
        viewHolder.txt_event_program.setText(Html.fromHtml(String.valueOf(event_list_model.getEvent_program())));

        if (event_list_model.getEvent_type().equalsIgnoreCase("1")){
//            viewHolder.ll_item_event_box.setBackgroundColor(activity.getResources().getColor(R.color.color_annul_event));
//            viewHolder.ll_date_time_view.setBackgroundColor(activity.getResources().getColor(R.color.color_annul_event));
            viewHolder.ll_item_event_box.setBackground(ContextCompat.getDrawable(activity,R.drawable.bg_event_annual_content));
            viewHolder.ll_date_time_view.setBackground(ContextCompat.getDrawable(activity,R.drawable.bg_event_annual_content));
            viewHolder.content_view.setBackground(ContextCompat.getDrawable(activity,R.drawable.bg_event_annual_content));

        }else if (event_list_model.getEvent_type().equalsIgnoreCase("2")){
//            viewHolder.ll_item_event_box.setBackgroundColor(activity.getResources().getColor(R.color.color_hastham));
//            viewHolder.ll_date_time_view.setBackgroundColor(activity.getResources().getColor(R.color.color_hastham));

            viewHolder.ll_item_event_box.setBackground(ContextCompat.getDrawable(activity,R.drawable.bg_event_hastham_content));
            viewHolder.ll_date_time_view.setBackground(ContextCompat.getDrawable(activity,R.drawable.bg_event_hastham_content));
            viewHolder.content_view.setBackground(ContextCompat.getDrawable(activity,R.drawable.bg_event_hastham_content));

        }else if (event_list_model.getEvent_type().equalsIgnoreCase("3")){
            viewHolder.ll_item_event_box.setBackground(ContextCompat.getDrawable(activity,R.drawable.bg_event_poosam_content));
            viewHolder.ll_date_time_view.setBackground(ContextCompat.getDrawable(activity,R.drawable.bg_event_poosam_content));
            viewHolder.content_view.setBackground(ContextCompat.getDrawable(activity,R.drawable.bg_event_poosam_content));


//            viewHolder.ll_item_event_box.setBackgroundColor(activity.getResources().getColor(R.color.color_poosam));
//            viewHolder.ll_date_time_view.setBackgroundColor(activity.getResources().getColor(R.color.color_poosam));

        }else if (event_list_model.getEvent_type().equalsIgnoreCase("4")){

            viewHolder.ll_item_event_box.setBackground(ContextCompat.getDrawable(activity,R.drawable.bg_event_sw_visit_content));
            viewHolder.ll_date_time_view.setBackground(ContextCompat.getDrawable(activity,R.drawable.bg_event_sw_visit_content));
            viewHolder.content_view.setBackground(ContextCompat.getDrawable(activity,R.drawable.bg_event_sw_visit_content));

//            viewHolder.ll_item_event_box.setBackgroundColor(activity.getResources().getColor(R.color.color_swamigal_visit));
//            viewHolder.ll_date_time_view.setBackgroundColor(activity.getResources().getColor(R.color.color_swamigal_visit));

        }else {

            viewHolder.ll_item_event_box.setBackground(ContextCompat.getDrawable(activity,R.drawable.bg_content));
            viewHolder.ll_date_time_view.setBackground(ContextCompat.getDrawable(activity,R.drawable.bg_content));
            viewHolder.content_view.setBackground(ContextCompat.getDrawable(activity,R.drawable.bg_content));

//            viewHolder.ll_item_event_box.setBackgroundColor(activity.getResources().getColor(R.color.content_background));
//            viewHolder.ll_date_time_view.setBackgroundColor(activity.getResources().getColor(R.color.content_background));
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(new Intent(activity.getApplicationContext(), ShowEventActivity.class));
                Bundle bundle =new Bundle();
                bundle.putString("event_date",getDay(event_list_model.getEvent_sdate()));
                bundle.putString("event_month",getMonth(event_list_model.getEvent_sdate()));
                bundle.putString("event_name",event_list_model.getEvent_name());
                bundle.putString("event_program", event_list_model.getEvent_program());
                bundle.putString("event_type", event_list_model.getEvent_type());
                bundle.putString("event_venue", event_list_model.getEvent_venue());
                bundle.putString("event_image", event_list_model.getEvent_image());
                intent.putExtras(bundle);
                activity.startActivity(intent);
            }
        });
    }

    private String getMonth(String event_sdate) {
        String[] day = new String[10];
        if (!event_sdate.equalsIgnoreCase("null")) {
            day = event_sdate.split("-");
        }
        return months[Integer.parseInt(day[1])];
//        return  day[1];
    }

    private String getDay(String event_sdate) {
        String[] day = new String[10];
        if (!event_sdate.equalsIgnoreCase("null")) {
            day = event_sdate.split("-");
        }
        return day[0];
    }

    @Override
    public int getItemCount() {
        return eventLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_date, txt_month, txt_time, txt_event_name, txt_event_program;
        LinearLayout ll_item_event_box,ll_date_time_view;
        RelativeLayout content_view;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_date=itemView.findViewById(R.id.txt_date);
            txt_month = itemView.findViewById(R.id.txt_month);
            txt_time = itemView.findViewById(R.id.txt_time);
            txt_event_name = itemView.findViewById(R.id.txt_event_name);
            txt_event_program = itemView.findViewById(R.id.txt_event_program);
            ll_item_event_box =itemView.findViewById(R.id.ll_item_event_box);
            ll_date_time_view =itemView.findViewById(R.id.ll_date_time_view);
            content_view =itemView.findViewById(R.id.content_view);
        }
    }
}
