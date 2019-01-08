package com.satvatinfosole.ssss.sangam.videofullScreenControlar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.MediaController;

import com.satvatinfosole.ssss.sangam.R;
import com.satvatinfosole.ssss.sangam.gallery.FullScreenVideoActivity;

/**
 * Created by SATHISH on 11/1/2018.
 */
public class FullScreenMediaController extends MediaController {
    private ImageButton fullScreen;
    private String isFullScreen;

    public FullScreenMediaController(Context context) {
        super(context);
    }


    @Override
    public void setAnchorView(View view) {

        super.setAnchorView(view);

        //image button for full screen to be added to media controller
        fullScreen = new ImageButton(super.getContext());

        FrameLayout.LayoutParams params =
                new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.RIGHT;
        params.rightMargin = 80;
        addView(fullScreen, params);

        //fullscreen indicator from intent
        isFullScreen = ((Activity) getContext()).getIntent().
                getStringExtra("fullScreenInd");

        if ("y".equals(isFullScreen)) {
            fullScreen.setImageResource(R.drawable.ic_fullscreen_exit_black_24dp);
        } else {
            fullScreen.setImageResource(R.drawable.ic_fullscreen_black_24dp);
        }

        //add listener to image button to handle full screen and exit full screen events
        fullScreen.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), FullScreenVideoActivity.class);

                if ("y".equals(isFullScreen)) {
                    intent.putExtra("fullScreenInd", "");
                } else {
                    intent.putExtra("fullScreenInd", "y");
                }
                ((Activity) getContext()).startActivity(intent);
            }
        });
    }
}
