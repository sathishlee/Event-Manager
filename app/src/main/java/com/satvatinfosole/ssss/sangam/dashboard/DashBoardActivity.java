package com.satvatinfosole.ssss.sangam.dashboard;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.satvatinfosole.ssss.sangam.DailogInterface;
import com.satvatinfosole.ssss.sangam.R;
import com.satvatinfosole.ssss.sangam.constants.ApiConstants;
import com.satvatinfosole.ssss.sangam.constants.AppConstants;
import com.satvatinfosole.ssss.sangam.events.EventImageViewActivity;
import com.satvatinfosole.ssss.sangam.events.EventMainActivity;
import com.satvatinfosole.ssss.sangam.feedback.FeedbackActivity;
import com.satvatinfosole.ssss.sangam.gallery.PhotoGalleryActivity;
import com.satvatinfosole.ssss.sangam.gallery.VideoGalleryActivity;
import com.satvatinfosole.ssss.sangam.interfaces.GalleryDialogInterface;
import com.satvatinfosole.ssss.sangam.liveVideoStream.LiveVideoPlayerActivity;
import com.satvatinfosole.ssss.sangam.model.responseModel.UserResponseModel;
import com.satvatinfosole.ssss.sangam.notification.NotificationListActivity;
import com.satvatinfosole.ssss.sangam.presenter.DashboardPresenter;
import com.satvatinfosole.ssss.sangam.quiz.QuizActivity;
import com.satvatinfosole.ssss.sangam.quiz.QuizHomeActivity;
import com.satvatinfosole.ssss.sangam.sharedpreference.PreferenceData;
import com.satvatinfosole.ssss.sangam.userProfile.ProfileActivity;
import com.satvatinfosole.ssss.sangam.utility.GalleryAlertDialogBox;
import com.satvatinfosole.ssss.sangam.utility.CheckNetwork;
import com.satvatinfosole.ssss.sangam.utility.CustomDialogBox;
import com.satvatinfosole.ssss.sangam.view.DashbordViews;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;

public class DashBoardActivity extends AppCompatActivity implements DashbordViews, View.OnClickListener, DailogInterface, GalleryDialogInterface {
    TextView txt_user_name, txt_user_address, txt_flash_news_content,txt_user_type;
    LinearLayout ll_user_profile, ll_event, ll_photo_gallery, ll_quiz, ll_feedback, ll_alert;
    LinearLayout rvel_user_view;
    ImageView img_user_photo, img_user_type, img_cover_photo;
    ProgressDialog pDialog;
    CheckNetwork checkNetwork;
    PreferenceData preferenceData;
    DashboardPresenter dashboardPresenter;

    UserResponseModel userResponseModel;

    CustomDialogBox customDialogBox;
    GalleryAlertDialogBox galleryAlertDialogBox;


    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        Log.e("Display matrixs:-", "width\t" + width + " \n height\t" + height);

        initUI();
        getCurrentTimeDetails();
        onCliclkListner();
    }

    private void getCurrentTimeDetails() {
        Calendar c = Calendar.getInstance();

        AppConstants.TODAY_DATE = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
        AppConstants.TODAY_MONTH = String.valueOf(c.get(Calendar.MONTH) + 1);
        AppConstants.TODAY_YEAR = String.valueOf(c.get(Calendar.YEAR));
    }

    private void onCliclkListner() {
        ll_user_profile.setOnClickListener(this);
        ll_event.setOnClickListener(this);
        ll_photo_gallery.setOnClickListener(this);
        ll_quiz.setOnClickListener(this);
        ll_feedback.setOnClickListener(this);
        ll_alert.setOnClickListener(this);
        rvel_user_view.setOnClickListener(this);
        img_user_photo.setOnClickListener(this);
    }

    private void initUI() {
        checkNetwork = new CheckNetwork(this);
        preferenceData = new PreferenceData(this);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Please Wait ...");

        dashboardPresenter = new DashboardPresenter(DashBoardActivity.this, DashBoardActivity.this);
        if (checkNetwork.isNetworkAvailable()) {
            dashboardPresenter.getDashBoard(preferenceData.getDevoteeId(), preferenceData.getActivationKey());
            dashboardPresenter.getFlashNews();
        }
        galleryAlertDialogBox = new GalleryAlertDialogBox(DashBoardActivity.this, DashBoardActivity.this);
        txt_user_name = (TextView) findViewById(R.id.txt_user_name);
        txt_user_address = (TextView) findViewById(R.id.txt_user_address_1);
        txt_flash_news_content = (TextView) findViewById(R.id.txt_flash_news_content);
        txt_user_type = (TextView) findViewById(R.id.txt_user_type);
        txt_flash_news_content.setSelected(true);
        ll_user_profile = (LinearLayout) findViewById(R.id.ll_profile);
        ll_event = (LinearLayout) findViewById(R.id.ll_event);
        ll_photo_gallery = (LinearLayout) findViewById(R.id.ll_photo_gallery);
        ll_quiz = (LinearLayout) findViewById(R.id.ll_quiz);
        ll_feedback = (LinearLayout) findViewById(R.id.ll_feedback);
        ll_alert = (LinearLayout) findViewById(R.id.ll_alert);
        rvel_user_view = (LinearLayout) findViewById(R.id.rvel_user_view);
        img_user_photo = (ImageView) findViewById(R.id.img_user_photo);
        img_user_type = (ImageView) findViewById(R.id.img_user_type);
        img_cover_photo = (ImageView) findViewById(R.id.img_cover_photo);

        setUserStatus();

    }

    private void setUserStatus() {
        if (preferenceData.getDevoteeStatus().equalsIgnoreCase("1")) {
            startActivity(new Intent(getApplicationContext(), UnApprovalUserActivity.class));
        } else if (preferenceData.getDevoteeStatus().equalsIgnoreCase("2")) {
            startActivity(new Intent(getApplicationContext(), UnApprovalUserActivity.class));
        } else if (preferenceData.getDevoteeStatus().equalsIgnoreCase("3")) {
            startActivity(new Intent(getApplicationContext(), UnApprovalUserActivity.class));
        } else if (preferenceData.getDevoteeStatus().equalsIgnoreCase("4")) {
            img_user_type.setImageDrawable(getResources().getDrawable(R.drawable.shape_user_status_narmal));
            txt_user_type.setText("Normal User");
        } else if (preferenceData.getDevoteeStatus().equalsIgnoreCase("5")) {
            img_user_type.setImageDrawable(getResources().getDrawable(R.drawable.shape_user_status_advanced));
            txt_user_type.setText("Advanced User");

        } else if (preferenceData.getDevoteeStatus().equalsIgnoreCase("6")) {
            txt_user_type.setText("Supreme User");
            img_user_type.setImageDrawable(getResources().getDrawable(R.drawable.shape_user_status_supreme));
        } else if (preferenceData.getDevoteeStatus().equalsIgnoreCase("7")) {
            txt_user_type.setText("Normal with video rights User");

            img_user_type.setImageDrawable(getResources().getDrawable(R.drawable.shape_user_status_normal_with_video));
        }

        if (checkNetwork.isNetworkAvailable()) {

            String imageUri = preferenceData.getDevoteeProfilePhoto();
            Picasso.with(this)
//                    .load(ApiConstants.PROFILE_IMAGE_URL+imageUri)
                    .load(ApiConstants.PROFILE_IMAGE_URL + "devotee_image/" + preferenceData.getDevoteeId() + ".jpg")
                    .placeholder(R.drawable.ic_launcher)   // optional
                    .error(R.drawable.ic_launcher)      // optional
                    .resize(150, 150)
                    .into(img_user_photo);

            Picasso.with(this)
//                    .load(ApiConstants.PROFILE_IMAGE_URL+imageUri)
//                    .load(ApiConstants.PROFILE_IMAGE_URL + "devotee_cover_image/" + preferenceData.getDevoteeId() + ".jpg")
                    .load(ApiConstants.PROFILE_IMAGE_URL + "devotee_image/" + preferenceData.getDevoteeId() + ".jpg")
                    .placeholder(R.drawable.ic_launcher)   // optional
                    .error(R.drawable.ic_siva)      // optional
                    .into(img_cover_photo);
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
    public void getResponseDashbordSuccess(String responseSuccess) {
        JSONObject jObj = null;
        JSONObject jobj_user = null;
        try {
            jObj = new JSONObject(responseSuccess);
            String status = jObj.getString("status");
            String message = jObj.getString("message");
            if (status.equalsIgnoreCase("1")) {
                String login_Details = jObj.getString("Login_Details");
                jobj_user = new JSONObject(login_Details);
                txt_user_name.setText("Welcome, " + jobj_user.getString("fname") + " "
                        + jobj_user.getString("lname"));
                txt_user_address.setText(jobj_user.getString("address") + ",\n"
                        + jobj_user.getString("city") + ",\t"
                        + jobj_user.getString("state")
                        + ",\t" + jobj_user.getString("country") + ",\n"
                        + jobj_user.getString("pincode")+".");
//                        + jobj_user.getString("pincode").codePointAt(4));

                preferenceData.setDevoteeInfo(
                        jobj_user.getString("fname"),
                        jobj_user.getString("lname"),
                        jobj_user.getString("uname"),
                        jobj_user.getString("fathername"),
                        jobj_user.getString("email"),

                        jobj_user.getString("address"),
                        jobj_user.getString("contactphone"),
                        jobj_user.getString("howdouknow"),
                        jobj_user.getString("additionalinfo"),
                        jobj_user.getString("mobile"),
                        jobj_user.getString("dob"),
                        jobj_user.getString("city"),
                        jobj_user.getString("state"),
                        jobj_user.getString("country"),
                        jobj_user.getString("pincode"),
                        jobj_user.getString("status"),
                        jobj_user.getString("devotee_image"),
                        jobj_user.getString("sex"),
                        jobj_user.getString("devotee_cover_image")
                );


                userResponseModel = new UserResponseModel(
                        jobj_user.getString("reg_date"),
                        jobj_user.getString("videostatus"),
                        jobj_user.getString("fathername"),
                        jobj_user.getString("additionalinfo"),
                        jobj_user.getString("howdouknow"),
                        jobj_user.getString("email"),
                        jobj_user.getString("sex"),
                        jobj_user.getString("mobile"),
                        jobj_user.getString("pincode"),
                        jobj_user.getString("contactphone"),
                        jobj_user.getString("state"),
                        jobj_user.getString("city"),
                        jobj_user.getString("address"),
                        jobj_user.getString("activation"),
                        jobj_user.getString("dob"),
                        jobj_user.getString("suppose_name"),
                        jobj_user.getString("lname"),
                        jobj_user.getString("fname"),
                        jobj_user.getString("pword"),
                        jobj_user.getString("uname"),
                        jobj_user.getString("devoteeid"),
                        jobj_user.getString("country"),
                        jobj_user.getString("devotee_cover_image")
                );

            } else {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void getResponseDashboardFailiur(String responseError) {
        Toast.makeText(getApplicationContext(), responseError, Toast.LENGTH_LONG).show();

    }

    @Override
    public void getResponseFlashNewsSuccess(String responseSuccess) {
        JSONObject jObj = null;
        JSONArray jObj_news_array = null;
        JSONObject jObj_news = null;
        try {
            jObj = new JSONObject(responseSuccess);

            String status = jObj.getString("status");
            String message = jObj.getString("message");
            if (status.equalsIgnoreCase("1")) {
                String fash_News = jObj.getString("Fash_News");
                StringBuilder strFlashNews = new StringBuilder();
                jObj_news_array = new JSONArray(fash_News);
                for (int i = 0; i < jObj_news_array.length(); i++) {
                    jObj_news = new JSONObject(jObj_news_array.get(i).toString());
                    strFlashNews.append(jObj_news.getString("flash_news") + "\n");
                }
                txt_flash_news_content.setText(Html.fromHtml(String.valueOf(strFlashNews)));


            } else {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getResponseFlashNewsFailiur(String responseError) {
        Toast.makeText(getApplicationContext(), responseError, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.rvel_user_view:
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                break;
            case R.id.ll_profile:
                startActivity(new Intent(getApplicationContext(), LiveVideoPlayerActivity.class));
                break;
            case R.id.ll_event:
                startActivity(new Intent(getApplicationContext(), EventMainActivity.class));
                break;
            case R.id.ll_photo_gallery:

                galleryAlertDialogBox.showAlertDialogBox("Select Gallery", "");
                break;
            case R.id.ll_quiz:
//                startActivity(new Intent(getApplicationContext(), QuizActivity.class));
                startActivity(new Intent(getApplicationContext(), QuizHomeActivity.class));
                break;
            case R.id.ll_feedback:
                startActivity(new Intent(getApplicationContext(), FeedbackActivity.class));
                break;

                case R.id.img_user_photo:
                    AppConstants.FULL_IMAGE_URI = ApiConstants.PROFILE_IMAGE_URL + "devotee_image/" + preferenceData.getDevoteeId() + ".jpg";
                    startActivity(new Intent(getApplicationContext(), EventImageViewActivity.class));
                break;
            case R.id.ll_alert:
//                customDialogBox = new CustomDialogBox(DashBoardActivity.this, this, "Contact Us", "If you have any Suggestions / Comments or difficulty using the Application, Please email to admin support at: admin@ssss-sangam.org", true, "SEND E-MAIL", "CANCEL");
//                customDialogBox.show();
                startActivity(new Intent(getApplicationContext(), NotificationListActivity.class));
                break;
        }

    }


    @Override
    public void okonclick() {
        finish();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
        startActivity(intent);
        finish();
        System.exit(0);

       /* Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", "sathish.s@satvatinfosol.com", null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "ssss-sangam-test");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "test mail");
        startActivity(Intent.createChooser(emailIntent, "Send email..."));
*/
        customDialogBox.dismiss();
    }

    @Override
    public void cancelonclick() {
        customDialogBox.dismiss();
    }

    @Override
    public void selectPhotoGallery() {
        galleryAlertDialogBox.hideAlertDialog();
        startActivity(new Intent(getApplicationContext(), PhotoGalleryActivity.class));
    }

    @Override
    public void selectVideoGallery() {
        galleryAlertDialogBox.hideAlertDialog();
        startActivity(new Intent(getApplicationContext(), VideoGalleryActivity.class));

    }

    @Override
    public void selectGalleryCancel() {
        galleryAlertDialogBox.hideAlertDialog();
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            customDialogBox = new CustomDialogBox(DashBoardActivity.this, this, "ssss-sangam", "Are you Sure do you want to exit?", true, "Exit", "Cancel");
            customDialogBox.show();
        } else {
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }
}
