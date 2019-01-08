package com.satvatinfosole.ssss.sangam.userProfile;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.satvatinfosole.ssss.sangam.DailogInterface;
import com.satvatinfosole.ssss.sangam.R;
import com.satvatinfosole.ssss.sangam.constants.ApiConstants;
import com.satvatinfosole.ssss.sangam.constants.AppConstants;
import com.satvatinfosole.ssss.sangam.events.EventImageViewActivity;
import com.satvatinfosole.ssss.sangam.feedback.FeedbackActivity;
import com.satvatinfosole.ssss.sangam.interfaces.GalleryDialogInterface;
import com.satvatinfosole.ssss.sangam.presenter.UploadImagePresenter;
import com.satvatinfosole.ssss.sangam.sharedpreference.PreferenceData;
import com.satvatinfosole.ssss.sangam.utility.CheckNetwork;
import com.satvatinfosole.ssss.sangam.utility.CustomDialogBox;
import com.satvatinfosole.ssss.sangam.utility.GalleryAlertDialogBox;
import com.satvatinfosole.ssss.sangam.view.UploadImageViews;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class UploadProfilePhotoActivity extends AppCompatActivity implements View.OnClickListener, GalleryDialogInterface, UploadImageViews, DailogInterface {
    ImageView img_back_press, imgSelectedImage;
    Button butPickImage, butUploadImage;
    GalleryAlertDialogBox galleryAlertDialogBox;
    private int PICK_IMAGE_REQUEST = 1;
    private static final int CAMERA_REQUEST = 1888;
    private Bitmap bitmap;

    private Uri filePath;
    String strDevoteeId, strUploadImage;

    ProgressDialog pDialog;
    CheckNetwork checkNetwork;
    PreferenceData preferenceData;

    UploadImagePresenter uploadImagePresenter;

    CustomDialogBox customDialogBox;



    private ScaleGestureDetector mScaleGestureDetector;
    private float mScaleFactor = 1.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_profile_photo);
        intiUI();
        onClickListner();
       }

    private void onClickListner() {
        img_back_press.setOnClickListener(this);
        imgSelectedImage.setOnClickListener(this);
        butPickImage.setOnClickListener(this);
        butUploadImage.setOnClickListener(this);
    }

    private void intiUI() {


        mScaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());

        checkNetwork = new CheckNetwork(this);
        preferenceData = new PreferenceData(this);
        strDevoteeId = preferenceData.getDevoteeId();
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Please Wait ...");

        uploadImagePresenter = new UploadImagePresenter(UploadProfilePhotoActivity.this, UploadProfilePhotoActivity.this);

        galleryAlertDialogBox = new GalleryAlertDialogBox(UploadProfilePhotoActivity.this, UploadProfilePhotoActivity.this);

        img_back_press = (ImageView) findViewById(R.id.img_back_press);
        imgSelectedImage = (ImageView) findViewById(R.id.img_selected_image);
        butPickImage = (Button) findViewById(R.id.but_pick_image);
        butUploadImage = (Button) findViewById(R.id.but_upload_image);

        if (checkNetwork.isNetworkAvailable()) {
            String imageUri = preferenceData.getDevoteeProfilePhoto();
            Picasso.with(this)
//                    .load(ApiConstants.PROFILE_IMAGE_URL + imageUri)
                    .load(ApiConstants.PROFILE_IMAGE_URL + "devotee_image/" + preferenceData.getDevoteeId() + ".jpg")
                    .placeholder(R.drawable.ic_launcher)   // optional
                    .error(R.drawable.ic_launcher)      // optional
                    .into(imgSelectedImage);
        } else {
//            img_user_photo.setImageResource(getResources().getDrawable(R.drawable.ic_launcher));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back_press:
                goBackActivity();
                break;
            case R.id.but_pick_image:
                pickImage();
                break;
            case R.id.but_upload_image:
                uploadImage();
                break;
            case R.id.img_selected_image:
                showImage();
                break;
        }
    }

    private void showImage() {

    }

    private void uploadImage() {
        if (!strUploadImage.equalsIgnoreCase("null")) {
            Log.e(UploadProfilePhotoActivity.class.getSimpleName(), "Devotee img string is -> " + strUploadImage);

            if (!strDevoteeId.equalsIgnoreCase("null")) {
                Log.e(UploadProfilePhotoActivity.class.getSimpleName(), "Devotee img id is -> " + strDevoteeId);
                uploadImagePresenter.uploadImage(strDevoteeId, strUploadImage, String.valueOf(AppConstants.UPDATE_PHOTO_TYPE));
            } else {
                Log.e(UploadProfilePhotoActivity.class.getSimpleName(), "Devotee id is null");
            }
        } else {
            Log.e(UploadProfilePhotoActivity.class.getSimpleName(), "Upload image id is null");
        }
    }

    private void pickImage() {
        galleryAlertDialogBox.showAlertDialogBox("Pick Photo", "");
    }

    private void goBackActivity() {
        finish();
    }


    // interface method for custom alert box for pick image
    @Override
    public void selectPhotoGallery() {
        galleryAlertDialogBox.hideAlertDialog();
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

    }

    @Override
    public void selectVideoGallery() {
        galleryAlertDialogBox.hideAlertDialog();


        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);

    }

    @Override
    public void selectGalleryCancel() {
        galleryAlertDialogBox.hideAlertDialog();
    }

    // interface method for custom alert box for pick image


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imgSelectedImage.setImageBitmap(bitmap);
                strUploadImage = getStringImage(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imgSelectedImage.setImageBitmap(photo);
            strUploadImage = getStringImage(photo);
        }
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    @Override
    public void showProgress() {
        pDialog.show();
    }

    @Override
    public void hideProgress() {
        pDialog.dismiss();
    }

    @Override
    public void imageResponseSuccess(String response) {


        try {
            JSONObject mJsnobject = new JSONObject(response);
            String status = mJsnobject.getString("status");
            String message = mJsnobject.getString("message");
            if (status.equalsIgnoreCase("1")) {
                customDialogBox = new CustomDialogBox(UploadProfilePhotoActivity.this, this, "Server Response", message, false, "OK", "CANCEL");
                customDialogBox.show();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void imageResponseError(String error) {
        customDialogBox = new CustomDialogBox(UploadProfilePhotoActivity.this, this, "Server Response", error, false, "OK", "CANCEL");
        customDialogBox.show();

    }

    @Override
    public void okonclick() {
        customDialogBox.dismiss();
        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        finish();

    }

    @Override
    public void cancelonclick() {
        customDialogBox.dismiss();
        finish();
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {

        @Override
        public boolean onScale(ScaleGestureDetector scaleGestureDetector){

            mScaleFactor *= scaleGestureDetector.getScaleFactor();

            mScaleFactor = Math.max(0.1f,

                    Math.min(mScaleFactor, 10.0f));

            imgSelectedImage.setScaleX(mScaleFactor);

            imgSelectedImage.setScaleY(mScaleFactor);

            return true;

        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        return super.onTouchEvent(event);
        mScaleGestureDetector.onTouchEvent(event);
        return true;
    }
}
