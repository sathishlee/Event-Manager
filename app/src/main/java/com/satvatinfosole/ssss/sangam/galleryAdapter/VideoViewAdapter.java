package com.satvatinfosole.ssss.sangam.galleryAdapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.satvatinfosole.ssss.sangam.R;
import com.satvatinfosole.ssss.sangam.interfaces.SelectPhoto;
import com.satvatinfosole.ssss.sangam.model.responseModel.VideoGalleryResponseModel;

import java.util.ArrayList;

/**
 * Created by SATHISH on 10/31/2018.
 */
public class VideoViewAdapter extends RecyclerView.Adapter<VideoViewAdapter.ViewHolder>  {
    ArrayList<VideoGalleryResponseModel.Video_Gallery_List> arr_photo_gallery_lists;
    Activity activity;
    SelectPhoto selectPhoto;

    public VideoViewAdapter(ArrayList<VideoGalleryResponseModel.Video_Gallery_List> arr_photo_gallery_lists, Activity activity,    SelectPhoto selectPhoto) {
        this.arr_photo_gallery_lists = arr_photo_gallery_lists;
        this.activity = activity;
        this.selectPhoto=selectPhoto;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_gallery_view,viewGroup,false);
        return new  ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        viewHolder.txt_event_name.setText(arr_photo_gallery_lists.get(position).getVg_title());
        viewHolder.txt_event_date.setVisibility(View.GONE);


        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPhoto.pickPhoto(position);

            }
        });
    }

    @Override
    public int getItemCount() {
        return arr_photo_gallery_lists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPhoto;
        TextView txt_event_name,txt_event_date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto =(ImageView)itemView.findViewById(R.id.img_photo);
            txt_event_name=itemView.findViewById(R.id.txt_event_name);
            txt_event_date =itemView.findViewById(R.id.txt_event_date);

        }
    }
}
