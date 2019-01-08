package com.satvatinfosole.ssss.sangam.userProfile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.satvatinfosole.ssss.sangam.DailogInterface;
import com.satvatinfosole.ssss.sangam.R;
import com.satvatinfosole.ssss.sangam.constants.AppConstants;
import com.satvatinfosole.ssss.sangam.dashboard.DashBoardActivity;
import com.satvatinfosole.ssss.sangam.model.responseModel.UserResponseModel;
import com.satvatinfosole.ssss.sangam.presenter.DashboardPresenter;
import com.satvatinfosole.ssss.sangam.presenter.RegistreationPresenter;
import com.satvatinfosole.ssss.sangam.sharedpreference.PreferenceData;
import com.satvatinfosole.ssss.sangam.utility.CheckNetwork;
import com.satvatinfosole.ssss.sangam.utility.CustomDialogBox;
import com.satvatinfosole.ssss.sangam.view.DashbordViews;
import com.satvatinfosole.ssss.sangam.view.RegistrationViews;

import org.json.JSONObject;

public class EditProfileActivity extends AppCompatActivity implements RegistrationViews, View.OnClickListener, DailogInterface, DashbordViews {
    public String TAG = EditProfileActivity.class.getSimpleName();
    ImageView img_back_press;
    EditText txt_profile_user_name, txt_profile_user_lname, txt_profile_father_name, txt_profile_email, txt_profile_phone, txt_profile_address, txt_profile_dob,
            txt_how_do_u_know, txt_add_info,
            txt_profile_address_city, txt_profile_address_state, txt_profile_address_country, txt_profile_address_pincode, txt_profile_landline,
            txt_password;
    TextView txt_user_id, txtTitle;
    Button butSubmit;
    String str_profile_user_name, str_profile_user_lname, str_profile_father_name, str_profile_email, str_profile_phone, str_profile_address, str_profile_dob,
            str_how_do_u_know, str_add_info,
            str_profile_address_city, str_profile_address_state, str_profile_address_country, str_profile_address_pincode, str_profile_landline,
            strUserId, strPassword;

    LinearLayout ll_edit_primary_view, ll_edit_additional_info_view, ll_edit_login_view;
    PreferenceData preferenceData;

    ProgressDialog pDialog;
    CheckNetwork checkNetwork;
    RegistreationPresenter registreationPresenter;
    CustomDialogBox customDialogBox;

    DashboardPresenter dashboardPresenter;
    UserResponseModel userResponseModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
//        overridePendingTransition(R.animator.fade_in, R.animator.fade_out);
        initUI();
        showViewUI();
        onClickListner();
        setValueToUI();

    }

    private void showViewUI() {
        if (AppConstants.EDIT_ACTIVITY.equalsIgnoreCase("1")) {
            ll_edit_primary_view.setVisibility(View.VISIBLE);
            ll_edit_additional_info_view.setVisibility(View.GONE);
            ll_edit_login_view.setVisibility(View.GONE);
            txtTitle.setText("Edit Profile");
        }
        if (AppConstants.EDIT_ACTIVITY.equalsIgnoreCase("2")) {

            ll_edit_primary_view.setVisibility(View.GONE);
            ll_edit_additional_info_view.setVisibility(View.VISIBLE);
            ll_edit_login_view.setVisibility(View.GONE);
            txtTitle.setText("Edit Additional Info");


        }
        if (AppConstants.EDIT_ACTIVITY.equalsIgnoreCase("3")) {
            ll_edit_primary_view.setVisibility(View.GONE);
            ll_edit_additional_info_view.setVisibility(View.GONE);
            ll_edit_login_view.setVisibility(View.VISIBLE);
            txtTitle.setText("Change Password");
        }/*else{
           Toast.makeText(getApplicationContext(),"Invalid",Toast.LENGTH_SHORT).show();
        }*/
    }

    private void onClickListner() {
        butSubmit.setOnClickListener(this);
        img_back_press.setOnClickListener(this);
    }

    private void updateToServer() {
        str_profile_user_name = txt_profile_user_name.getText().toString();
        str_profile_user_lname = txt_profile_user_lname.getText().toString();
        str_profile_father_name = txt_profile_father_name.getText().toString();
        str_profile_email = txt_profile_email.getText().toString();
        str_profile_phone = txt_profile_phone.getText().toString();
        str_profile_address = txt_profile_address.getText().toString();
        str_profile_dob = txt_profile_dob.getText().toString();
        str_how_do_u_know = txt_how_do_u_know.getText().toString();
        str_add_info = txt_add_info.getText().toString();

        str_profile_address_city = txt_profile_address_city.getText().toString();
        str_profile_address_state = txt_profile_address_state.getText().toString();
        str_profile_address_country = txt_profile_address_country.getText().toString();
        str_profile_address_pincode = txt_profile_address_pincode.getText().toString();
        str_profile_landline = txt_profile_landline.getText().toString();

        strUserId = txt_user_id.getText().toString();
        strPassword = txt_password.getText().toString();

        if (checkNetwork.isNetworkAvailable()) {
            registreationPresenter.updateProfile(
                    preferenceData.getDevoteeId(),
                    str_profile_user_name,
                    str_profile_user_lname,
                    str_profile_father_name,
                    preferenceData.getDevoteeGendar(),
                    "",
                    str_profile_dob,
                    str_profile_address,
                    str_profile_address_city,
                    str_profile_address_state,
                    str_profile_address_country,
                    str_profile_address_pincode,
                    str_profile_phone,
                    str_profile_landline,
                    str_how_do_u_know,
                    str_add_info
            );
            /* registreationPresenter.registration(str_profile_user_name,
                    str_profile_user_lname,
                    str_profile_father_name,
                    "male",
                    "", str_profile_dob,
                    str_profile_address + str_profile_address,
                    str_profile_address_city,
                    str_profile_address_state + ", " + str_profile_address_country, str_profile_address_country, str_profile_address_pincode, str_profile_phone,
                    str_profile_landline, str_how_do_u_know, str_add_info,
                    strUserId, strPassword,
                    str_profile_email, preferenceData.getRegDevoteeActivationKey()
//                                preferenceData.getRequestMailID().replace("@", "%40"), preferenceData.getActivationKey()
            );*/
        } else {
            Toast.makeText(getApplicationContext(), "no internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void setValueToUI() {

        /*Log.d(TAG, "F Name" + preferenceData.getDevoteeName());
        Log.d(TAG, "L Name" + preferenceData.getDevoteeLName());
        Log.d(TAG, "FA Name" + preferenceData.getDevoteFatherName());
        Log.d(TAG, "Email" + preferenceData.getDevoteEmail());
        Log.d(TAG, "Mobile" + preferenceData.getDevoteMobile());
        Log.d(TAG, "Contact" + preferenceData.getDevoteContact());
        Log.d(TAG, "DOB" + preferenceData.getDevoteDob());
        Log.d(TAG, "city" + preferenceData.getDevoteCity());
        Log.d(TAG, "state" + preferenceData.getDevoteState());
        Log.d(TAG, "country" + preferenceData.getDevoteCountry());
        Log.d(TAG, "pincode" + preferenceData.getDevotePincode());*/

        txt_profile_user_name.setText(preferenceData.getDevoteeName());
        txt_profile_user_lname.setText(preferenceData.getDevoteeLName());
        txt_profile_father_name.setText(preferenceData.getDevoteFatherName());
        txt_profile_email.setText(preferenceData.getDevoteEmail());
        txt_profile_phone.setText(preferenceData.getDevoteMobile());
        txt_profile_landline.setText(preferenceData.getDevoteContact());
        txt_profile_address.setText(preferenceData.getDevoteAddress());
        txt_profile_dob.setText(preferenceData.getDevoteDob());

        txt_profile_address_city.setText(preferenceData.getDevoteCity());
        txt_profile_address_state.setText(preferenceData.getDevoteState());
        txt_profile_address_country.setText(preferenceData.getDevoteCountry());
        txt_profile_address_pincode.setText(preferenceData.getDevotePincode());

        txt_how_do_u_know.setText(preferenceData.getDevoteHowDoYouKnow());
        txt_add_info.setText(preferenceData.getDevoteAdd_Info());
        txt_user_id.setText(preferenceData.getDevoteUName());
    }

    private void initUI() {

        checkNetwork = new CheckNetwork(this);
        preferenceData = new PreferenceData(this);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Please Wait ...");


        registreationPresenter = new RegistreationPresenter(this, this);


        dashboardPresenter = new DashboardPresenter(EditProfileActivity.this, EditProfileActivity.this);

//        scrlv_user_primary_view = (ScrollView) findViewById(R.id.scrlv_user_primary_view);
//        ll_edit_additional_info_view = (LinearLayout) findViewById(R.id.ll_edit_additional_info_view);
        txt_profile_user_name = (EditText) findViewById(R.id.txt_profile_user_name);
        txt_profile_user_lname = (EditText) findViewById(R.id.txt_profile_user_lname);
        txt_profile_father_name = (EditText) findViewById(R.id.txt_profile_father_name);
        txt_profile_email = (EditText) findViewById(R.id.txt_profile_email);
        txt_profile_phone = (EditText) findViewById(R.id.txt_profile_phone);
        txt_profile_address = (EditText) findViewById(R.id.txt_profile_address);
        txt_profile_dob = (EditText) findViewById(R.id.txt_profile_dob);
        txt_how_do_u_know = (EditText) findViewById(R.id.txt_how_do_u_know);
        txt_add_info = (EditText) findViewById(R.id.txt_add_info);

        txt_profile_address_city = (EditText) findViewById(R.id.txt_profile_address_city);
        txt_profile_address_state = (EditText) findViewById(R.id.txt_profile_address_state);
        txt_profile_address_country = (EditText) findViewById(R.id.txt_profile_address_country);
        txt_profile_address_pincode = (EditText) findViewById(R.id.txt_profile_address_pincode);
        txt_profile_landline = (EditText) findViewById(R.id.txt_profile_landline);

        txt_user_id = (TextView) findViewById(R.id.txt_user_id);
        txt_password = (EditText) findViewById(R.id.txt_password);

        butSubmit = (Button) findViewById(R.id.but_submit);

        img_back_press = (ImageView) findViewById(R.id.img_back_press);

        ll_edit_primary_view = (LinearLayout) findViewById(R.id.ll_edit_primary_view);
        ll_edit_additional_info_view = (LinearLayout) findViewById(R.id.ll_edit_additional_info_view);
        ll_edit_login_view = (LinearLayout) findViewById(R.id.ll_edit_login_view);
        txtTitle = (TextView) findViewById(R.id.txt_title);
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

        customDialogBox = new CustomDialogBox(this, this, "Update Info", "Update successfully ", true, "Yes", "No");
        customDialogBox.show();


    }

    @Override
    public void getResponseDashboardFailiur(String responseError) {

    }

    @Override
    public void getResponseFlashNewsSuccess(String responseSuccess) {

    }

    @Override
    public void getResponseFlashNewsFailiur(String responseError) {

    }

    @Override
    public void showRegistrationSuccess(String responseSuccess) {

        JSONObject jObj = null;
        try {
            jObj = new JSONObject(responseSuccess);
            String status = jObj.getString("status");
            String message = jObj.getString("message");
            if (status.equalsIgnoreCase("1")) {
//                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

//                customDialogBox = new CustomDialogBox(this, this, "Server Response", message, true, "Yes", "No");
//                customDialogBox.show();

                dashboardPresenter.getDashBoard(preferenceData.getDevoteeId(), preferenceData.getActivationKey());
            } else {
//                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                customDialogBox = new CustomDialogBox(this, this, "Server Response", message, true, "Yes", "No");
                customDialogBox.show();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void shoRegistrationError(String responseError) {
//        Toast.makeText(getApplicationContext(), responseError, Toast.LENGTH_SHORT).show();
        customDialogBox = new CustomDialogBox(this, this, "Server Response", responseError, true, "Yes", "No");
        customDialogBox.show();
    }


    @Override
    public void getRequestEmailSuccess(String responseSuccess) {

    }

    @Override
    public void getRequestEmailError(String responseSuccess) {

    }

    @Override
    public void getRequestStatusSuccess(String responseSuccess) {


        JSONObject jObj = null;
        try {
            jObj = new JSONObject(responseSuccess);
            String status = jObj.getString("status");
            String message = jObj.getString("message");
            if (status.equalsIgnoreCase("1")) {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();


            } else {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void getRequestStatusError(String responseSuccess) {
        Toast.makeText(getApplicationContext(), responseSuccess, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.but_submit:
                if (AppConstants.EDIT_ACTIVITY.equalsIgnoreCase("3")) {
                    changePassword();
                } else {
                    updateToServer();

                }
                break;
            case R.id.img_back_press:
                goBackActivity();
                break;

        }
    }

    private void changePassword() {
        strPassword = txt_password.getText().toString();
        if (checkNetwork.isNetworkAvailable()) {
            registreationPresenter.changePassword(preferenceData.getDevoteeId(), strPassword);
        } else {
            Toast.makeText(getApplicationContext(), "no internet connection", Toast.LENGTH_SHORT).show();

        }
    }

    private void goBackActivity() {
//        finish();
        customDialogBox = new CustomDialogBox(this, this, "Not Save", "Do you want close this window?", true, "Yes", "No");
        customDialogBox.show();
//        customDialogBox.showWithIcon("Not Save","Do you want close this window?",true,"Yes","No");
    }

    @Override
    public void onBackPressed() {

        customDialogBox = new CustomDialogBox(this, this, "Not Save", "Do you want close this window?", true, "Yes", "No");
        customDialogBox.show();

//        super.onBackPressed();

    }

    @Override
    public void okonclick() {


        if (AppConstants.EDIT_ACTIVITY.equalsIgnoreCase("1")) {
            startActivity(new Intent(getApplicationContext(), ProfileDetailsActivity.class));
            finish();
        }
        if (AppConstants.EDIT_ACTIVITY.equalsIgnoreCase("2")) {

            startActivity(new Intent(getApplicationContext(), AdditionalInfoActivity.class));
            finish();


        }
        if (AppConstants.EDIT_ACTIVITY.equalsIgnoreCase("3")) {
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
            finish();
        }/*else{
           Toast.makeText(getApplicationContext(),"Invalid",Toast.LENGTH_SHORT).show();
        }*/


//        finish();
    }

    @Override
    public void cancelonclick() {
        customDialogBox.hide();
    }
}
