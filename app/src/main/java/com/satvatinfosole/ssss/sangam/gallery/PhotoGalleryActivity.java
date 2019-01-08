package com.satvatinfosole.ssss.sangam.gallery;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.satvatinfosole.ssss.sangam.R;

import com.satvatinfosole.ssss.sangam.galleryAdapter.GalleryViewAdapter;
import com.satvatinfosole.ssss.sangam.model.responseModel.MediaEventResponseModel;
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


public class PhotoGalleryActivity extends AppCompatActivity implements GalleryViews, View.OnClickListener {
    String TAG = PhotoGalleryActivity.class.getSimpleName();
    RecyclerView recvPhotoGalary;
    ImageView img_back_press;
    ProgressDialog pDialog;
    CheckNetwork checkNetwork;
    PreferenceData preferenceData;
    GalleryPresenter galleryPresenter;
    JSONArray jObj_gallery_array = null;
    ArrayList<MediaEventResponseModel.Media_details> arr_photo_gallery_lists;
    MediaEventResponseModel.Media_details model_photoGalleryList;
    ArrayList<String> strImageUrl;
    ArrayList<String> arrMediaId;
    ArrayList<String> arrVenueId;
    int mGallerySize = 0;
    GalleryViewAdapter galleryViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_gallery);
        initUI();
        img_back_press.setOnClickListener(this);
    }

    private void initUI() {
        checkNetwork = new CheckNetwork(this);
        preferenceData = new PreferenceData(this);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Please Wait ...");

        arr_photo_gallery_lists = new ArrayList<>();
        strImageUrl = new ArrayList<>();

        arrMediaId = new ArrayList<>();
        arrVenueId = new ArrayList<>();

        galleryPresenter = new GalleryPresenter(this, this);

        if (checkNetwork.isNetworkAvailable()) {
            galleryPresenter.getMediaEventList();
        }

        galleryViewAdapter = new GalleryViewAdapter(arr_photo_gallery_lists, this, 1);

        recvPhotoGalary = (RecyclerView) findViewById(R.id.recv_photo_galary);
        img_back_press = (ImageView) findViewById(R.id.img_back_press);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(PhotoGalleryActivity.this, 2);
        recvPhotoGalary.setLayoutManager(mLayoutManager);
        recvPhotoGalary.setItemAnimator(new DefaultItemAnimator());
        recvPhotoGalary.setAdapter(galleryViewAdapter);


    }

    private void getGalleryData() {
        if (checkNetwork.isNetworkAvailable()) {
            for (int i = 0; i < mGallerySize; i++) {

                if (!arrMediaId.get(i).equalsIgnoreCase("null")) {
                    if (!arrVenueId.get(i).equalsIgnoreCase("null")) {

                        galleryPresenter.getPhotoThumbnail(arrMediaId.get(i), arrVenueId.get(i));
                    } else {
                    }
                } else {
                }

            }

        } else {
        }
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
        JSONObject jObj = null;
        JSONObject jObj_event = null;

        try {
            jObj = new JSONObject(responseSuccess);

            String status = jObj.getString("status");
            String message = jObj.getString("message");

            if (status.equalsIgnoreCase("1")) {

                String gallery_list = jObj.getString("Media_details");

                jObj_gallery_array = new JSONArray(gallery_list);


                for (int i = 0; i < jObj_gallery_array.length(); i++) {
                    jObj_event = new JSONObject(jObj_gallery_array.get(i).toString());
                    if (jObj_event.getString("media_photo").equalsIgnoreCase("photo")) {
                        Log.e(TAG, i + "media_photo add");
                        arrMediaId.add(jObj_event.getString("media_id"));
                        arrVenueId.add(jObj_event.getString("media_venue"));
                        mGallerySize++;
                    }
                }
            } else {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        getGalleryData();
    }


    @Override
    public void showGalleryError(String responseError) {
        Toast.makeText(getApplicationContext(), responseError, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getthumbnailSuccess(String responseSuccess) {


        Log.e("PGA", "server response->" + responseSuccess);


        JSONObject jObj = null;

        JSONObject jObj_event = null;

        try {
            jObj = new JSONObject(responseSuccess);

            String status = jObj.getString("status");
            String message = jObj.getString("message");
            if (status.equalsIgnoreCase("1")) {
                String strPhoto_Gallery_Thumb = jObj.getString("Photo_Gallery_Thumb");
                JSONObject imgJObj = new JSONObject(strPhoto_Gallery_Thumb);

                String id = imgJObj.getString("pg_id");
                String pg_media_id = imgJObj.getString("pg_media_id");
                String pg_mediavenue_id = imgJObj.getString("pg_mediavenue_id");
                String pg_title = imgJObj.getString("pg_title");
                String imaUri = imgJObj.getString("pg_image_name");
                String pg_user_permission = imgJObj.getString("pg_user_permission");
                String pg_position = imgJObj.getString("pg_position");

                model_photoGalleryList = new MediaEventResponseModel.Media_details();
                /*media_id,media_venue,media_tile,media_date*/
                model_photoGalleryList.setMedia_id(pg_media_id);
                model_photoGalleryList.setMedia_date(imaUri);
                model_photoGalleryList.setMedia_venue(pg_mediavenue_id);
                model_photoGalleryList.setMedia_title(pg_title);

                arr_photo_gallery_lists.add(model_photoGalleryList);

            } else {
            }

            galleryViewAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void getthumbnailError(String responseError) {
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back_press:
                goBackActivity();
        }
    }

    private void goBackActivity() {
        finish();
    }
}
