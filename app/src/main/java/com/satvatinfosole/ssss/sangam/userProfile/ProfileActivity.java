package com.satvatinfosole.ssss.sangam.userProfile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.satvatinfosole.ssss.sangam.DailogInterface;
import com.satvatinfosole.ssss.sangam.R;
import com.satvatinfosole.ssss.sangam.SplashActivity;
import com.satvatinfosole.ssss.sangam.constants.ApiConstants;
import com.satvatinfosole.ssss.sangam.constants.AppConstants;
import com.satvatinfosole.ssss.sangam.dashboard.DashBoardActivity;
import com.satvatinfosole.ssss.sangam.dashboard.UnApprovalUserActivity;
import com.satvatinfosole.ssss.sangam.events.EventImageViewActivity;
import com.satvatinfosole.ssss.sangam.model.responseModel.UserResponseModel;
import com.satvatinfosole.ssss.sangam.sharedpreference.PreferenceData;
import com.satvatinfosole.ssss.sangam.utility.CheckNetwork;
import com.satvatinfosole.ssss.sangam.utility.CustomDialogBox;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener, DailogInterface {
    TextView txt_user_name, txt_use_email, txt_user_address, txt_contact_number, txt_landline_number, txt_how_do_u_know, txt_additional_info;
    LinearLayout ll_user_view, ll_additinal_info_view, ll_login_view, ll_upload_profile_photo;
    Button but_sign_out;
    ImageView img_user_photo, img_user_type, img_cover_photo, img_add_cover_photo;

    UserResponseModel userResponseModel;

    PreferenceData preferenceData;
    CheckNetwork checkNetwork;

    RelativeLayout revl_primary_details_view;
    CustomDialogBox customDialogBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        initUI();
        onClickListner();
        setValue();
    }

    private void setValue() {

        txt_user_name.setText(preferenceData.getDevoteeName() + " " + preferenceData.getDevoteeLName());
//        txt_use_email.setText(preferenceData.getDevoteEmail());

//        txt_use_email.setText(preferenceData.getDevoteeStatus());
        txt_user_address.setText(preferenceData.getDevoteAddress()
                + ",\n"
                + preferenceData.getDevoteCity() + ",\t"
                + preferenceData.getDevoteState()
                + ",\n" + preferenceData.getDevoteCountry() + ",\t"
//                + preferenceData.getDevotePincode().codePointAt(4));
                + preferenceData.getDevotePincode()+".");
        txt_contact_number.setText(preferenceData.getDevoteMobile());
    }

    private void onClickListner() {
        ll_user_view.setOnClickListener(this);
        ll_additinal_info_view.setOnClickListener(this);
        ll_login_view.setOnClickListener(this);
        ll_upload_profile_photo.setOnClickListener(this);

        but_sign_out.setOnClickListener(this);

        img_add_cover_photo.setOnClickListener(this);
        img_user_photo.setOnClickListener(this);

    }

    private void initUI() {
        userResponseModel = new UserResponseModel();

        preferenceData = new PreferenceData(this);
        checkNetwork = new CheckNetwork(this);

        txt_user_name = (TextView) findViewById(R.id.txt_user_name);
        txt_use_email = (TextView) findViewById(R.id.txt_use_email);
        txt_user_address = (TextView) findViewById(R.id.txt_user_address);
        txt_contact_number = (TextView) findViewById(R.id.txt_contact_number);

        txt_landline_number = (TextView) findViewById(R.id.txt_landline_number);
        txt_how_do_u_know = (TextView) findViewById(R.id.txt_how_do_u_know);
        txt_additional_info = (TextView) findViewById(R.id.txt_additional_info);

        ll_user_view = (LinearLayout) findViewById(R.id.ll_user_view);
        ll_additinal_info_view = (LinearLayout) findViewById(R.id.ll_additinal_info_view);
        ll_login_view = (LinearLayout) findViewById(R.id.ll_login_details_view);
        ll_upload_profile_photo = (LinearLayout) findViewById(R.id.ll_upload_profile_photo);
        but_sign_out = (Button) findViewById(R.id.but_sign_out);

        img_user_photo = (ImageView) findViewById(R.id.img_user_photo);
        img_user_type = (ImageView) findViewById(R.id.img_user_type);
        img_add_cover_photo = (ImageView) findViewById(R.id.img_add_cover_photo);
        img_cover_photo = (ImageView) findViewById(R.id.img_cover_photo);

        revl_primary_details_view = (RelativeLayout) findViewById(R.id.revl_primary_details_view);
        setUserStatus();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), DashBoardActivity.class));
        finish();
    }

    private void setUserStatus() {
        if (preferenceData.getDevoteeStatus().equalsIgnoreCase("1")) {
            txt_use_email.setText("Pending User");

            startActivity(new Intent(getApplicationContext(), UnApprovalUserActivity.class));
        } else if (preferenceData.getDevoteeStatus().equalsIgnoreCase("2")) {
            txt_use_email.setText("Canceled User");

            startActivity(new Intent(getApplicationContext(), UnApprovalUserActivity.class));
        } else if (preferenceData.getDevoteeStatus().equalsIgnoreCase("3")) {
            txt_use_email.setText("Query User");

            startActivity(new Intent(getApplicationContext(), UnApprovalUserActivity.class));
        } else if (preferenceData.getDevoteeStatus().equalsIgnoreCase("4")) {
            txt_use_email.setText("Normal");

            img_user_type.setImageDrawable(getResources().getDrawable(R.drawable.shape_user_status_narmal));
        } else if (preferenceData.getDevoteeStatus().equalsIgnoreCase("5")) {
            txt_use_email.setText("Advanced");

            img_user_type.setImageDrawable(getResources().getDrawable(R.drawable.shape_user_status_advanced));
        } else if (preferenceData.getDevoteeStatus().equalsIgnoreCase("6")) {
            txt_use_email.setText("Supream");

            img_user_type.setImageDrawable(getResources().getDrawable(R.drawable.shape_user_status_supreme));
        } else if (preferenceData.getDevoteeStatus().equalsIgnoreCase("7")) {
            txt_use_email.setText("Normal with video rights");

            img_user_type.setImageDrawable(getResources().getDrawable(R.drawable.shape_user_status_normal_with_video));
        }

        /*String imageUri = preferenceData.getDevoteeProfilePhoto();

        Picasso.with(this)
                .load(       R.drawable.ic_launcher    )                        // optional
)
                .placeholder(R.drawable.ic_launcher)   // optional
                .error(R.drawable.ic_launcher)      // optional
                .into(img_user_photo);*/
        if (checkNetwork.isNetworkAvailable()) {

            String imageUri = preferenceData.getDevoteeProfilePhoto();
            Picasso.with(this)
//                    .load(ApiConstants.PROFILE_IMAGE_URL+imageUri)
                    .load(ApiConstants.PROFILE_IMAGE_URL + "devotee_image/" + preferenceData.getDevoteeId() + ".jpg")
                    .placeholder(R.drawable.ic_launcher)   // optional
                    .error(R.drawable.ic_launcher)      // optional
                    .into(img_user_photo);

           /* Picasso.with(this)
//                    .load(ApiConstants.PROFILE_IMAGE_URL+imageUri)
                    .load(ApiConstants.PROFILE_IMAGE_URL + "devotee_cover_image/" + preferenceData.getDevoteeId() + ".jpg")
                    .placeholder(R.drawable.ic_launcher)   // optional
                    .error(R.drawable.ic_launcher)      // optional
                    .into(img_cover_photo);*/


        } else {
//            img_user_photo.setImageResource(getResources().getDrawable(R.drawable.ic_launcher));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_user_view:
                viewUserInfo();
                break;
            case R.id.ll_additinal_info_view:
                viewUserAdditinalInfo();
                break;
            case R.id.ll_login_details_view:
                viewLogInDeatils();
                break;

            case R.id.ll_upload_profile_photo:
                AppConstants.UPDATE_PHOTO_TYPE = 1;
                startActivity(new Intent(getApplicationContext(), UploadProfilePhotoActivity.class));
                break;

            case R.id.img_add_cover_photo:
                AppConstants.UPDATE_PHOTO_TYPE = 0;
                startActivity(new Intent(getApplicationContext(), UploadProfilePhotoActivity.class));
                break;
            case R.id.img_back_press:
                goBackActivity();
                break;
            case R.id.but_sign_out:
                goSignOut();
                break;
            case R.id.img_user_photo:
                AppConstants.FULL_IMAGE_URI = ApiConstants.PROFILE_IMAGE_URL + "devotee_image/" + preferenceData.getDevoteeId() + ".jpg";
                startActivity(new Intent(getApplicationContext(), EventImageViewActivity.class));
                break;
        }
    }

    private void goBackActivity() {
        finish();
    }

    private void goSignOut() {
//        preferenceData.clearPreferenceData();
//        preferenceData.setLogin(false);
//        startActivity(new Intent(getApplicationContext(), SplashActivity.class));
//        finish();
        customDialogBox = new CustomDialogBox(ProfileActivity.this, this, "ssss-sangam", "Are you Sure do you want to sign out?", true, "Sign out", "Cancel");
        customDialogBox.show();
    }

    private void viewLogInDeatils() {
//        startActivity(new Intent(getApplicationContext(), LoginDetailsActivity.class));

        AppConstants.EDIT_ACTIVITY = "3";
        startActivity(new Intent(getApplicationContext(), EditProfileActivity.class));
    }

    private void viewUserAdditinalInfo() {
        startActivity(new Intent(getApplicationContext(), AdditionalInfoActivity.class));
    }

    private void viewUserInfo() {
        startActivity(new Intent(getApplicationContext(), ProfileDetailsActivity.class));

    }


    @Override
    public void okonclick() {
        preferenceData.clearPreferenceData();
        preferenceData.setLogin(false);
        customDialogBox.dismiss();
        startActivity(new Intent(getApplicationContext(), SplashActivity.class));
        finish();
    }

    @Override
    public void cancelonclick() {
        customDialogBox.dismiss();
    }
}
