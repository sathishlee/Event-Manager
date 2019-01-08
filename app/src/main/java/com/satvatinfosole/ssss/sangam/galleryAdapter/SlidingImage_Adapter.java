package com.satvatinfosole.ssss.sangam.galleryAdapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.satvatinfosole.ssss.sangam.R;
import com.satvatinfosole.ssss.sangam.constants.ApiConstants;
import com.satvatinfosole.ssss.sangam.constants.AppConstants;
import com.satvatinfosole.ssss.sangam.events.EventImageViewActivity;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by SATHISH on 10/30/2018.
 */
public class SlidingImage_Adapter extends PagerAdapter {


    private ArrayList<String> IMAGES;
    private LayoutInflater inflater;
    private Context context;


    public SlidingImage_Adapter(Context context,ArrayList<String> IMAGES) {
        this.context = context;
        this.IMAGES=IMAGES;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return IMAGES.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, final int position) {
        View imageLayout = inflater.inflate(R.layout.slidingimages_layout, view, false);

        assert imageLayout != null;
        final ImageView imageView = (ImageView) imageLayout
                .findViewById(R.id.image);

        Log.e(SlidingImage_Adapter.class.getSimpleName(),"image url "+ApiConstants.GALLERY_PHOTO_URL+IMAGES.get(position));
        Picasso.with(context)
                .load(ApiConstants.GALLERY_PHOTO_URL+IMAGES.get(position))
                .placeholder(R.drawable.ic_launcher)
                .fit()
                .centerCrop()
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .error(R.drawable.ic_launcher)
                .into(imageView);

    /*    imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppConstants.FULL_IMAGE_URI = ApiConstants.GALLERY_PHOTO_URL+IMAGES.get(position);
                context.startActivity(new Intent(context.getApplicationContext(), EventImageViewActivity.class));
            }
        });*/

        view.addView(imageLayout, 0);

        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

}
