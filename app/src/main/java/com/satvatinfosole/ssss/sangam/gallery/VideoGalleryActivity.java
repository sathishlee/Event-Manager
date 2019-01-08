package com.satvatinfosole.ssss.sangam.gallery;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.satvatinfosole.ssss.sangam.R;
import com.satvatinfosole.ssss.sangam.galleryAdapter.VideoViewAdapter;
import com.satvatinfosole.ssss.sangam.interfaces.SelectPhoto;
import com.satvatinfosole.ssss.sangam.model.responseModel.VideoGalleryResponseModel;
import com.satvatinfosole.ssss.sangam.presenter.GalleryPresenter;
import com.satvatinfosole.ssss.sangam.sharedpreference.PreferenceData;
import com.satvatinfosole.ssss.sangam.utility.CheckNetwork;
import com.satvatinfosole.ssss.sangam.view.GalleryViews;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class VideoGalleryActivity extends AppCompatActivity implements GalleryViews, View.OnClickListener, SelectPhoto {

    String TAG = PhotoGalleryActivity.class.getSimpleName();
    RecyclerView recvPhotoGalary;
    ImageView img_back_press;

    //Api call
    ProgressDialog pDialog;
    CheckNetwork checkNetwork;
    PreferenceData preferenceData;
    GalleryPresenter galleryPresenter;

    JSONArray jObj_gallery_array = null;
    //for list

    ArrayList<VideoGalleryResponseModel.Video_Gallery_List> arr_video_gallery_lists;
    VideoGalleryResponseModel.Video_Gallery_List model_vedio_gallery_lists;


    VideoViewAdapter videoViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_gallery);
        initUI();
        img_back_press.setOnClickListener(this);

    }

    private void initUI() {

        checkNetwork = new CheckNetwork(this);
        preferenceData = new PreferenceData(this);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Please Wait ...");

        arr_video_gallery_lists = new ArrayList<>();

        galleryPresenter = new GalleryPresenter(this, this);

        if (checkNetwork.isNetworkAvailable()) {
            galleryPresenter.getVideoGalleryList("1", "1");
        }

        videoViewAdapter = new VideoViewAdapter(arr_video_gallery_lists, this, this);

        recvPhotoGalary = (RecyclerView) findViewById(R.id.recv_photo_galary);
        img_back_press = (ImageView) findViewById(R.id.img_back_press);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(VideoGalleryActivity.this, 2);
        recvPhotoGalary.setLayoutManager(mLayoutManager);
        recvPhotoGalary.setItemAnimator(new DefaultItemAnimator());
        recvPhotoGalary.setAdapter(videoViewAdapter);

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


                String gallery_list = jObj.getString("Video_Gallery_List");

                jObj_gallery_array = new JSONArray(gallery_list);
                Log.e(TAG, "size of video gallery" + jObj_gallery_array.length());

                String strImgeFileName = null;
                for (int i = 0; i < jObj_gallery_array.length(); i++) {

                    jObj_event = new JSONObject(jObj_gallery_array.get(i).toString());
                    model_vedio_gallery_lists = new VideoGalleryResponseModel.Video_Gallery_List();

                    model_vedio_gallery_lists.setVg_id(jObj_event.getString("vg_id"));
                    model_vedio_gallery_lists.setVg_media_id(jObj_event.getString("vg_media_id"));
                    model_vedio_gallery_lists.setVg_mediavenue_id(jObj_event.getString("vg_mediavenue_id"));
                    model_vedio_gallery_lists.setVg_title(jObj_event.getString("vg_title"));
                    model_vedio_gallery_lists.setVg_video_name(jObj_event.getString("vg_video_name"));
                    strImgeFileName = jObj_event.getString("vg_video_name");
                    model_vedio_gallery_lists.setVg_user_permission(jObj_event.getString("vg_user_permission"));
                    model_vedio_gallery_lists.setVg_position(jObj_event.getString("vg_position"));

                    arr_video_gallery_lists.add(model_vedio_gallery_lists);

                }
                videoViewAdapter.notifyDataSetChanged();


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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back_press:
                goBackActivity();
        }

    }

    private void goBackActivity() {
        finish();
    }

    @Override
    public void pickPhoto(int position) {
        Bundle bundle = new Bundle();
        Intent intent = new Intent(getApplicationContext(), FullScreenVideoActivity.class);
        bundle.putString("fullScreenInd", "n");
        bundle.putString("video_name", arr_video_gallery_lists.get(position).getVg_video_name());
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
