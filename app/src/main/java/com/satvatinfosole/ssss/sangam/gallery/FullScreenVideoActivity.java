package com.satvatinfosole.ssss.sangam.gallery;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

import com.satvatinfosole.ssss.sangam.R;
import com.satvatinfosole.ssss.sangam.constants.ApiConstants;
import com.satvatinfosole.ssss.sangam.videofullScreenControlar.FullScreenMediaController;

public class FullScreenVideoActivity extends AppCompatActivity {
String TAG =FullScreenVideoActivity.class.getSimpleName();
    private VideoView videoView;
    private MediaController mediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_video);
        videoView = findViewById(R.id.videoView);


        String fullScreen =  getIntent().getStringExtra("fullScreenInd");
        String videoName =  getIntent().getStringExtra("video_name");
        Log.e(TAG,"Video name"+videoName);
        Log.e(TAG,"Video url"+ ApiConstants.EVENT_VIDEO_URL+videoName);
        if("y".equals(fullScreen)){
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getSupportActionBar().hide();
        }
        Uri videoUri = Uri.parse(ApiConstants.EVENT_VIDEO_URL+videoName);
        videoView.setVideoURI(videoUri);
        mediaController = new FullScreenMediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        videoView.start();



    }
}
