package com.satvatinfosole.ssss.sangam.gallery;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.satvatinfosole.ssss.sangam.R;
import com.satvatinfosole.ssss.sangam.constants.ApiConstants;
import com.satvatinfosole.ssss.sangam.constants.AppConstants;
import com.satvatinfosole.ssss.sangam.galleryAdapter.PhotoViewAdapter;
import com.satvatinfosole.ssss.sangam.galleryAdapter.SlidingImage_Adapter;
import com.satvatinfosole.ssss.sangam.interfaces.SelectPhoto;
import com.satvatinfosole.ssss.sangam.model.responseModel.PhotoGalleryResponseModel;
import com.satvatinfosole.ssss.sangam.presenter.GalleryPresenter;
import com.satvatinfosole.ssss.sangam.sharedpreference.PreferenceData;
import com.satvatinfosole.ssss.sangam.utility.CheckNetwork;
import com.satvatinfosole.ssss.sangam.view.GalleryViews;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class PhotoViewActivity extends AppCompatActivity implements GalleryViews, SelectPhoto, View.OnClickListener {
    String TAG = PhotoViewActivity.class.getSimpleName();

    TextView txtMediaTitle, txtMediaDate, txtPhotoPosition, txtSlidePosition, txtPhotoName;
    ImageView imgSelectedPhoto;
    RecyclerView recvPhotoGalary;
    ImageView img_back_press;
    Button btnSlideShow;
    ProgressDialog pDialog;
    CheckNetwork checkNetwork;
    PreferenceData preferenceData;
    GalleryPresenter galleryPresenter;
    JSONArray jObj_gallery_array = null;
    ArrayList<PhotoGalleryResponseModel.Photo_Gallery_List> arr_photo_gallery_lists;
    PhotoGalleryResponseModel.Photo_Gallery_List model_photo_gallery_lists;
    PhotoViewAdapter photoViewAdapter;
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;


    private ArrayList<String> ImagesArray = new ArrayList<String>();


    String strTitle, strDate, strMediaId, getStrMediaVenue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);
        Bundle extras = getIntent().getExtras();
        strTitle = extras.getString("media_tile");
        strDate = extras.getString("media_date");
        strMediaId = extras.getString("media_id");
        getStrMediaVenue = extras.getString("media_venue");
        initUI();
        onClickListner();
    }

    private void onClickListner() {
        btnSlideShow.setOnClickListener(this);
        img_back_press.setOnClickListener(this);
    }

    private void initUI() {
        checkNetwork = new CheckNetwork(this);
        preferenceData = new PreferenceData(this);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Please Wait ...");
        arr_photo_gallery_lists = new ArrayList<>();
        galleryPresenter = new GalleryPresenter(this, this);
        if (checkNetwork.isNetworkAvailable()) {

            galleryPresenter.getPhotoGalleryList(strMediaId, getStrMediaVenue);

        }

        mPager = (ViewPager) findViewById(R.id.pager);

        mPager.setVisibility(View.GONE);

        final float density = getResources().getDisplayMetrics().density;


        photoViewAdapter = new PhotoViewAdapter(arr_photo_gallery_lists, this, this);
        recvPhotoGalary = (RecyclerView) findViewById(R.id.recv_photo_list);
        img_back_press = (ImageView) findViewById(R.id.img_back_press);

        imgSelectedPhoto = (ImageView) findViewById(R.id.img_seleced_photo);

        txtMediaTitle = (TextView) findViewById(R.id.txt_media_title);
        txtMediaDate = (TextView) findViewById(R.id.txt_media_date);
        txtPhotoPosition = (TextView) findViewById(R.id.txt_photo_counter);
        txtSlidePosition = (TextView) findViewById(R.id.txt_slide_counter);
        txtPhotoName = (TextView) findViewById(R.id.txt_photo_title);

        btnSlideShow = (Button) findViewById(R.id.but_slide_show);

        txtMediaTitle.setText(strTitle);
        txtMediaDate.setText(strDate);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true);

        recvPhotoGalary.setLayoutManager(mLayoutManager);
        recvPhotoGalary.setItemAnimator(new DefaultItemAnimator());
        recvPhotoGalary.setAdapter(photoViewAdapter);
    }

    @Override
    public void showProgress() {
        pDialog.show();
    }

    @Override
    public void hideProgress() {
        pDialog.hide();
    }

    @Override
    public void showGallerySuccess(String responseSuccess) {


        Log.e(TAG, "server response->" + responseSuccess);


        JSONObject jObj = null;

        JSONObject jObj_event = null;

        try {
            jObj = new JSONObject(responseSuccess);

            String status = jObj.getString("status");
            String message = jObj.getString("message");
            Log.e(TAG, "satus->" + status);

            if (status.equalsIgnoreCase("1")) {

                String gallery_list = jObj.getString("Photo_Gallery_List");

                jObj_gallery_array = new JSONArray(gallery_list);
                Log.e(TAG, "size of photo gallery" + jObj_gallery_array.length());

                String strImgeFileName = null;
                for (int i = 0; i < jObj_gallery_array.length(); i++) {

                    jObj_event = new JSONObject(jObj_gallery_array.get(i).toString());
                    model_photo_gallery_lists = new PhotoGalleryResponseModel.Photo_Gallery_List();

                    model_photo_gallery_lists.setPg_id(jObj_event.getString("pg_id"));
                    model_photo_gallery_lists.setPg_media_id(jObj_event.getString("pg_media_id"));
                    model_photo_gallery_lists.setPg_mediavenue_id(jObj_event.getString("pg_mediavenue_id"));
                    model_photo_gallery_lists.setPg_title(jObj_event.getString("pg_title"));
                    model_photo_gallery_lists.setPg_image_name(jObj_event.getString("pg_image_name"));
                    strImgeFileName = jObj_event.getString("pg_image_name");
                    model_photo_gallery_lists.setPg_user_permission(jObj_event.getString("pg_user_permission"));
                    model_photo_gallery_lists.setPg_position(jObj_event.getString("pg_position"));

                    arr_photo_gallery_lists.add(model_photo_gallery_lists);
                    photoViewAdapter.notifyDataSetChanged();
                    Log.e(TAG, i + "th Image name->" + strImgeFileName);

                    ImagesArray.add(strImgeFileName);
                }


                txtPhotoName.setText(arr_photo_gallery_lists.get(0).getPg_title());
                txtPhotoPosition.setText(0 + 1 + "/" + arr_photo_gallery_lists.size());
                Picasso.with(this)
                        .load(ApiConstants.GALLERY_PHOTO_URL + arr_photo_gallery_lists.get(0).getPg_image_name())
                        .placeholder(R.drawable.ic_launcher)
                        .fit()
                        .centerCrop()
                        .memoryPolicy(MemoryPolicy.NO_CACHE)
                        .networkPolicy(NetworkPolicy.NO_CACHE)
                        .error(R.drawable.ic_launcher)
                        .into(imgSelectedPhoto);
            } else {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void showGalleryError(String responseError) {
        Toast.makeText(getApplicationContext(), responseError, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getthumbnailSuccess(String responseSuccess) {

    }

    @Override
    public void getthumbnailError(String responseError) {

    }

    @Override
    public void pickPhoto(int position) {
        txtPhotoName.setText(arr_photo_gallery_lists.get(position).getPg_title());
        txtPhotoPosition.setText(position + 1 + "/" + arr_photo_gallery_lists.size());
        Picasso.with(this)
                .load(ApiConstants.GALLERY_PHOTO_URL + arr_photo_gallery_lists.get(position).getPg_image_name())
                .placeholder(R.drawable.ic_launcher)
                .fit()
                .centerCrop()
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .error(R.drawable.ic_launcher)
                .into(imgSelectedPhoto);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back_press:
                goBackActivity();
                break;
            case R.id.but_slide_show:
                if (btnSlideShow.getText().toString().equalsIgnoreCase("Slide Show")) {
                    btnSlideShow.setText("Stop");
                    startSlideShow();
                } else {
                    stopSlideShow();
                }
                break;
        }
    }

    private void goBackActivity() {
        finish();
    }

    private void stopSlideShow() {
        startActivity(new Intent(getApplicationContext(), PhotoViewActivity.class));
    }

    private void startSlideShow() {
        mPager.setVisibility(View.VISIBLE);
        imgSelectedPhoto.setVisibility(View.GONE);
        recvPhotoGalary.setVisibility(View.GONE);
        mPager.setAdapter(new SlidingImage_Adapter(PhotoViewActivity.this, ImagesArray));
        NUM_PAGES = arr_photo_gallery_lists.size();
        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                txtPhotoPosition.setText(currentPage + 1 + "/" + NUM_PAGES);
                txtPhotoName.setText(arr_photo_gallery_lists.get(currentPage + 1).getPg_title());
                mPager.setCurrentItem(currentPage++, true);
            }
        };


        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppConstants.MEDIA_ID = "";
        AppConstants.MEDIA_VENUE_ID = "";
    }
}
