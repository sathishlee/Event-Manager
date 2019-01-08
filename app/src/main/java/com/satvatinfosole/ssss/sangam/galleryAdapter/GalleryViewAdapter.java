package com.satvatinfosole.ssss.sangam.galleryAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.satvatinfosole.ssss.sangam.R;
import com.satvatinfosole.ssss.sangam.adapter.EventListAdapter;
import com.satvatinfosole.ssss.sangam.constants.ApiConstants;
import com.satvatinfosole.ssss.sangam.constants.AppConstants;
import com.satvatinfosole.ssss.sangam.gallery.PhotoViewActivity;
import com.satvatinfosole.ssss.sangam.gallery.VideoViewActivity;
import com.satvatinfosole.ssss.sangam.model.responseModel.EventResponseModel;
import com.satvatinfosole.ssss.sangam.model.responseModel.MediaEventResponseModel;
import com.satvatinfosole.ssss.sangam.model.responseModel.PhotoGalleryResponseModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by SATHISH on 10/30/2018.
 */
public class GalleryViewAdapter extends RecyclerView.Adapter<GalleryViewAdapter.ViewHolder> {
    String TAG = GalleryViewAdapter.class.getSimpleName();
    private ArrayList<MediaEventResponseModel.Media_details> photo_gallery_lists;
    Activity activity;
    int galleryType;

    public GalleryViewAdapter(ArrayList<MediaEventResponseModel.Media_details> photo_gallery_lists, Activity activity, int galleryType) {
        this.photo_gallery_lists = photo_gallery_lists;
        this.activity = activity;
        this.galleryType = galleryType;
        Log.e(TAG, "adapter list size  " + photo_gallery_lists.size());
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_gallery_view, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final MediaEventResponseModel.Media_details event_list_model = photo_gallery_lists.get(i);
        viewHolder.txt_event_name.setText(event_list_model.getMedia_title());
        viewHolder.txt_event_date.setText(event_list_model.getMedia_date());
        viewHolder.txt_event_date.setVisibility(View.GONE);

        Picasso.with(activity)
//                    .load(ApiConstants.PROFILE_IMAGE_URL+imageUri)
                .load(ApiConstants.GALLERY_PHOTO_URL + event_list_model.getMedia_date())
                .placeholder(R.drawable.ic_launcher)   // optional
                .error(R.drawable.ic_launcher)      // optional
                .into(viewHolder.img_event_holder);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (galleryType == 1) {
                    Intent intent = new Intent(new Intent(activity.getApplicationContext(), PhotoViewActivity.class));
                    Bundle bundle = new Bundle();

                    bundle.putString("media_id", event_list_model.getMedia_id());
                    bundle.putString("media_venue", event_list_model.getMedia_venue());
                    bundle.putString("media_tile", event_list_model.getMedia_title());
                    bundle.putString("media_date", event_list_model.getMedia_date());

                    intent.putExtras(bundle);

                    activity.startActivity(intent);
                } else if (galleryType == 2) {
                    Intent intent = new Intent(new Intent(activity.getApplicationContext(), VideoViewActivity.class));
                    Bundle bundle = new Bundle();

                    bundle.putString("media_id", event_list_model.getMedia_id());
                    bundle.putString("media_venue", event_list_model.getMedia_venue());
                    bundle.putString("media_tile", event_list_model.getMedia_title());
                    bundle.putString("media_date", event_list_model.getMedia_date());

                    intent.putExtras(bundle);

                    activity.startActivity(intent);
                } else {
                    Toast.makeText(activity.getApplicationContext(), "please wait", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return photo_gallery_lists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        FrameLayout ll_gallery_view;
        RelativeLayout rvel_gallery_view;
        ImageView img_event_holder;
        TextView txt_event_name, txt_event_date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rvel_gallery_view = itemView.findViewById(R.id.rvel_gallery_view);
            ll_gallery_view = itemView.findViewById(R.id.ll_gallery_view);
            img_event_holder = itemView.findViewById(R.id.img_event_holder);
            txt_event_name = itemView.findViewById(R.id.txt_event_name);
            txt_event_date = itemView.findViewById(R.id.txt_event_date);
        }

    }
}
