package com.satvatinfosole.ssss.sangam.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.satvatinfosole.ssss.sangam.R;
import com.satvatinfosole.ssss.sangam.constants.AppConstants;
import com.satvatinfosole.ssss.sangam.model.responseModel.EventResponseModel;
import com.satvatinfosole.ssss.sangam.model.responseModel.NotificationModel;
import com.satvatinfosole.ssss.sangam.notification.NotificationDetailsActivity;
import com.satvatinfosole.ssss.sangam.notification.NotificationListActivity;

import java.util.ArrayList;

import static com.satvatinfosole.ssss.sangam.R.drawable.ic_dash_board_live_video;

/**
 * Created by SATHISH on 11/23/2018.
 */
public class NotificationListAdapter extends RecyclerView.Adapter<NotificationListAdapter.ViewHolder> {
    private ArrayList<NotificationModel> notificationLists;
    Activity activity;

    public NotificationListAdapter(ArrayList<NotificationModel> notificationLists, Activity activity) {
        this.notificationLists = notificationLists;
        this.activity = activity;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view =LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_notification_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        final NotificationModel notificationModel = notificationLists.get(position);
     /*   for (int i=0;i<notificationLists.size();i++){
            if (!viewHolder.txt_notification_date_time.getText().toString().equalsIgnoreCase(notificationLists.get(i).getDate())){
                viewHolder.rvl_date_time.setVisibility(View.VISIBLE);
                viewHolder.txt_notification_date_time.setText(notificationModel.getDate());
            }else{
                viewHolder.rvl_date_time.setVisibility(View.GONE);
            }
        }*/
        viewHolder.txt_notification_time.setText(notificationModel.getDate());
        viewHolder.txt_notification_title.setText(notificationModel.getTitle());
        viewHolder.txt_notification_message.setText(notificationModel.getMessage());
//        viewHolder.img_event_type.setImageResource(activity.getResources().getDrawable(R.drawable.ic_dash_board_live_video));
//        viewHolder.img_event_type.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_event_calender));

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle =new Bundle();
                Intent intent =new Intent(activity.getApplicationContext(), NotificationDetailsActivity.class);
                bundle.putString("title",notificationModel.getTitle());
                bundle.putString("message",notificationModel.getMessage());
                bundle.putString("date_time",notificationModel.getDate());
                intent.putExtras(bundle);
                activity.startActivity(intent);
            }
        });

    }



    @Override
    public int getItemCount() {
        return notificationLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CardView rvl_date_time;
        TextView txt_notification_date_time,txt_notification_title,txt_notification_message,txt_notification_time;
        ImageView img_event_type;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rvl_date_time = itemView.findViewById(R.id.rvl_date_time);
            txt_notification_date_time = itemView.findViewById(R.id.txt_notification_date_time);
            txt_notification_title = itemView.findViewById(R.id.txt_notification_title);
            txt_notification_message = itemView.findViewById(R.id.txt_notification_message);
            txt_notification_time = itemView.findViewById(R.id.txt_notification_time);
            img_event_type = itemView.findViewById(R.id.img_event_type);
        }
    }
}
