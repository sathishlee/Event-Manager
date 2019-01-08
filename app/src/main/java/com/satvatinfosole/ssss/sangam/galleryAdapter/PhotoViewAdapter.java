package com.satvatinfosole.ssss.sangam.galleryAdapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.satvatinfosole.ssss.sangam.R;
import com.satvatinfosole.ssss.sangam.constants.ApiConstants;
import com.satvatinfosole.ssss.sangam.interfaces.SelectPhoto;
import com.satvatinfosole.ssss.sangam.model.responseModel.PhotoGalleryResponseModel;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by SATHISH on 10/31/2018.
 */
public class PhotoViewAdapter extends RecyclerView.Adapter<PhotoViewAdapter.ViewHolder> {

    ArrayList<PhotoGalleryResponseModel.Photo_Gallery_List> arr_photo_gallery_lists;

    Activity activity;
    SelectPhoto selectPhoto;

    public PhotoViewAdapter(ArrayList<PhotoGalleryResponseModel.Photo_Gallery_List> arr_photo_gallery_lists, Activity activity,SelectPhoto selectPhoto) {
        this.arr_photo_gallery_lists = arr_photo_gallery_lists;
        this.activity = activity;
        this.selectPhoto=selectPhoto;

       }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_photo_view,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewAdapter.ViewHolder viewHolder, final int position) {

        Picasso.with(activity)
                .load(ApiConstants.GALLERY_PHOTO_URL+arr_photo_gallery_lists.get(position).getPg_image_name())
                .placeholder(R.drawable.ic_launcher)
                .fit()
                .centerCrop()
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .error(R.drawable.ic_launcher)
                .into(viewHolder.imgPhoto);
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
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto =(ImageView)itemView.findViewById(R.id.img_photo);
        }
    }
}
