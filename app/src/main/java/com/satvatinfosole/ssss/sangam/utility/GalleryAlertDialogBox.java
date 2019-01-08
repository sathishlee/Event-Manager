package com.satvatinfosole.ssss.sangam.utility;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.satvatinfosole.ssss.sangam.R;
import com.satvatinfosole.ssss.sangam.SplashActivity;
import com.satvatinfosole.ssss.sangam.interfaces.GalleryDialogInterface;

/**
 * Created by SATHISH on 11/9/2018.
 */
public class GalleryAlertDialogBox {
    Context context;
    private Dialog dialog;
    GalleryDialogInterface galleryDialogInterface;

    public GalleryAlertDialogBox(Context context, GalleryDialogInterface galleryDialogInterface) {
        this.context = context;
        this.galleryDialogInterface = galleryDialogInterface;
    }

    public void showAlertDialogBox(String title, String message) {

        if (dialog != null) {
            if (dialog.isShowing()) dialog.dismiss();
        }
        dialog = new Dialog(context);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_alert_box);
        TextView txt_alert_title = dialog.findViewById(R.id.txt_alert_title);
        ImageView list_img1 = dialog.findViewById(R.id.list_img1);
        ImageView list_img2 = dialog.findViewById(R.id.list_img2);
        TextView list_text1 = dialog.findViewById(R.id.list_text1);
        TextView list_text2 = dialog.findViewById(R.id.list_text2);
        ;
        txt_alert_title.setText(title);
        if (title.equalsIgnoreCase("Pick Photo")) {
            list_text1.setText("Open Gallery");
            list_text2.setText("Open Camera");
        }
        Button btn_cancel = dialog.findViewById(R.id.but_cancel);
        LinearLayout dg_ll_photo_gallery = dialog.findViewById(R.id.dg_ll_photo_gallery);
        LinearLayout dg_ll_video_gallery = dialog.findViewById(R.id.dg_ll_video_gallery);


        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                galleryDialogInterface.selectGalleryCancel();
            }
        });
        dg_ll_photo_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                galleryDialogInterface.selectPhotoGallery();
            }
        });
        dg_ll_video_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                galleryDialogInterface.selectVideoGallery();
            }
        });
        dialog.show();
    }

    public void hideAlertDialog() {
        dialog.dismiss();
    }
}
