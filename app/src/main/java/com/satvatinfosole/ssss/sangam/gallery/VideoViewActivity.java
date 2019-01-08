package com.satvatinfosole.ssss.sangam.gallery;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.satvatinfosole.ssss.sangam.R;
import com.satvatinfosole.ssss.sangam.utility.CheckNetwork;

public class VideoViewActivity extends AppCompatActivity {

    String TAG = VideoViewActivity.class.getSimpleName();
    ProgressDialog pd;

    CheckNetwork checkNetwork;
    ImageView img_back_press;
    VideoView viv_media_video;
    Uri uri;
    String video_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view);
        Bundle bundle = getIntent().getExtras();
        checkNetwork = new CheckNetwork(this);
        video_url = "https://www.quirksmode.org/html5/videos/big_buck_bunny.mp4";
        pd = new ProgressDialog(VideoViewActivity.this);
        pd.setMessage("Buffering video please wait...");
        initUI();

    }

    private void initUI() {
        img_back_press = (ImageView) findViewById(R.id.img_back_press);
        viv_media_video = (VideoView) findViewById(R.id.viv_media_video);
        if (!checkNetwork.isNetworkAvailable()) {
        } else {
            uri = Uri.parse(video_url);
        }
        viv_media_video.setVideoURI(uri);

        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(viv_media_video);

        viv_media_video.setMediaController(mediaController);

    }

}
