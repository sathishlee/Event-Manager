package com.satvatinfosole.ssss.sangam.sharedpreference;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.TextView;

import com.satvatinfosole.ssss.sangam.constants.AppConstants;


public class PreferenceData {
    SharedPreferences sharedPreferences;

    public PreferenceData(Context context) {
        sharedPreferences = context.getSharedPreferences(AppConstants.PREF_NAME, Context.MODE_PRIVATE);
    }

    public SharedPreferences getPreference() {
        return sharedPreferences;
    }

    public void  clearPreferenceData(){
        sharedPreferences.edit().clear().commit();
    }

    public void setLogin(boolean isLogin) {
        sharedPreferences.edit().putBoolean(AppConstants.IS_LOGIN, isLogin).commit();
    }

    public boolean getLogin() {
        return sharedPreferences.getBoolean(AppConstants.IS_LOGIN, Boolean.parseBoolean(""));
    }

    public void setLoginDetails(String strDevoteeid, String strActivationKey,String devoteestatus,String strProfilePhoto) {
        sharedPreferences.edit().putString(AppConstants.DEVOTEE_ID, strDevoteeid).commit();
        sharedPreferences.edit().putString(AppConstants.ACTIVATION_KEY, strActivationKey).commit();
        sharedPreferences.edit().putString(AppConstants.DEVOTEE_STATUS, devoteestatus).commit();
        sharedPreferences.edit().putString(AppConstants.DEVOTEE_PROFILE_PHOTO, strProfilePhoto).commit();
    }


    public String getDevoteeStatus(){
        return sharedPreferences.getString(AppConstants.DEVOTEE_STATUS,"");
    }

    public void setRequestMailStatus(boolean status) {
        sharedPreferences.edit().putBoolean(AppConstants.REQUESTED_MAIL_STATUS, status).commit();
    }

    public boolean getRequestMailStatus() {
        return sharedPreferences.getBoolean(AppConstants.REQUESTED_MAIL_STATUS, Boolean.parseBoolean(""));
    }

    public void setRegisterationStatus(boolean status) {
        sharedPreferences.edit().putBoolean(AppConstants.REGISTRATION_STATUS, status).commit();
    }

    public boolean getRegisterationStatus() {
        return sharedPreferences.getBoolean(AppConstants.REGISTRATION_STATUS, Boolean.parseBoolean(""));
    }

    public void setRequestMailResponse(String mailId, String ActivationKey) {
        sharedPreferences.edit().putString(AppConstants.REQUESTED_MAIL_ID, mailId).commit();
        sharedPreferences.edit().putString(AppConstants.ACTIVATION_KEY, ActivationKey).commit();
    }


    public void setRegisterCredintialsfromWebUrlResponse(String mailId, String ActivationKey) {
        sharedPreferences.edit().putString(AppConstants.REG_DEVOTEE_EMAIL, mailId).commit();
        sharedPreferences.edit().putString(AppConstants.REG_DEVOTEE_ACTIVATION_KEY, ActivationKey).commit();
    }

    public String getRegDevoteeEmail() {
        return sharedPreferences.getString(AppConstants.REG_DEVOTEE_EMAIL, "");

    }

    public String getRegDevoteeActivationKey() {
        return sharedPreferences.getString(AppConstants.REG_DEVOTEE_ACTIVATION_KEY, "");

    }

    public String getRequestMailID() {
        return sharedPreferences.getString(AppConstants.REQUESTED_MAIL_ID, "");

    }

    public String getDevoteeId() {
        return sharedPreferences.getString(AppConstants.DEVOTEE_ID, "");

    }

    public String getActivationKey() {
        return sharedPreferences.getString(AppConstants.ACTIVATION_KEY, "");

    }

    public void setDevoteeInfo(
            String strdevoteeName,String strdevoteeLName, String uname, String fatherName, String email,
            String txt_user_address, String contactphone, String howdouknow, String additionalinfo,
            String mobile, String dob, String city,String state, String  country,String pincode, String userStatus, String profilePhoto,String gendar,String cover_photo )
    {
        sharedPreferences.edit().putString(AppConstants.DEVOTEE_NAME, strdevoteeName).commit();
        sharedPreferences.edit().putString(AppConstants.DEVOTEE_LNAME, strdevoteeLName).commit();
        sharedPreferences.edit().putString(AppConstants.DEVOTEE_UNAME, uname).commit();
        sharedPreferences.edit().putString(AppConstants.DEVOTEE_FATHERNAME, fatherName).commit();
        sharedPreferences.edit().putString(AppConstants.DEVOTEE_EMAIL, email).commit();
        sharedPreferences.edit().putString(AppConstants.DEVOTEE_ADDRESS, txt_user_address).commit();

        sharedPreferences.edit().putString(AppConstants.DEVOTEE_CITY, city).commit();
        sharedPreferences.edit().putString(AppConstants.DEVOTEE_STATE, state).commit();
        sharedPreferences.edit().putString(AppConstants.DEVOTEE_COUNTRY, country).commit();
        sharedPreferences.edit().putString(AppConstants.DEVOTEE_PINCODE, pincode).commit();

        sharedPreferences.edit().putString(AppConstants.DEVOTEE_CONTACT, contactphone).commit();
        sharedPreferences.edit().putString(AppConstants.DEVOTEE_MOBILE, mobile).commit();
        sharedPreferences.edit().putString(AppConstants.DEVOTEE_HOW_DO_YOU_KNOW, howdouknow).commit();
        sharedPreferences.edit().putString(AppConstants.DEVOTEE_ADD_INFO, additionalinfo).commit();
        sharedPreferences.edit().putString(AppConstants.DEVOTEE_DOB, dob).commit();
        sharedPreferences.edit().putString(AppConstants.DEVOTEE_STATUS, userStatus).commit();
        sharedPreferences.edit().putString(AppConstants.DEVOTEE_PROFILE_PHOTO, profilePhoto).commit();
        sharedPreferences.edit().putString(AppConstants.DEVOTEE_GENDAR, gendar).commit();
        sharedPreferences.edit().putString(AppConstants.DEVOTEE_COVER_PHOTO, cover_photo).commit();

    }

    public String getDevoteeName() {
        return sharedPreferences.getString(AppConstants.DEVOTEE_NAME, "");
    }

    public String getDevoteeProfilePhoto() {
        return sharedPreferences.getString(AppConstants.DEVOTEE_PROFILE_PHOTO, "");
    }

    public String getDevoteeGendar() {
        return sharedPreferences.getString(AppConstants.DEVOTEE_PROFILE_PHOTO, "");
    }

    public String getDevoteeLName() {
        return sharedPreferences.getString(AppConstants.DEVOTEE_LNAME, "");
    }

    public String getDevoteUName() {
        return sharedPreferences.getString(AppConstants.DEVOTEE_UNAME, "");
    }

    public String getDevoteEmail() {
        return sharedPreferences.getString(AppConstants.DEVOTEE_EMAIL, "");
    }

    public String getDevoteAddress() {
        return sharedPreferences.getString(AppConstants.DEVOTEE_ADDRESS, "");
    }

    public String getDevoteContact() {
        return sharedPreferences.getString(AppConstants.DEVOTEE_CONTACT, "");
    }

    public String getDevoteMobile() {
        return sharedPreferences.getString(AppConstants.DEVOTEE_MOBILE, "");
    }

    public String getDevoteHowDoYouKnow() {
        return sharedPreferences.getString(AppConstants.DEVOTEE_HOW_DO_YOU_KNOW, "");
    }

    public String getDevoteAdd_Info() {
        return sharedPreferences.getString(AppConstants.DEVOTEE_ADD_INFO, "");
    }

    public String getDevoteFatherName() {
        return sharedPreferences.getString(AppConstants.DEVOTEE_FATHERNAME, "");
    }

    public String getDevoteDob() {
        return sharedPreferences.getString(AppConstants.DEVOTEE_DOB, "");
    }

    public String getDevoteCity() {
        return sharedPreferences.getString(AppConstants.DEVOTEE_CITY, "");
    }
 public String getDevoteState() {
        return sharedPreferences.getString(AppConstants.DEVOTEE_STATE, "");
    }
    public String getDevoteCountry() {
        return sharedPreferences.getString(AppConstants.DEVOTEE_COUNTRY, "");
    }
  public String getDevotePincode() {
        return sharedPreferences.getString(AppConstants.DEVOTEE_PINCODE, "");
    }

    public void setFirebaseRefrenceToken(String token){
        Log.e(PreferenceData.class.getSimpleName(),"set token"+token);
        sharedPreferences.edit().putString(AppConstants.NOTIFICATION_FIREBASE_REFRENCE_TOKEN,token).commit();
        Log.e(PreferenceData.class.getSimpleName()," get token"+sharedPreferences.getString(AppConstants.NOTIFICATION_FIREBASE_REFRENCE_TOKEN,""));

    }
    public String getFirebaseRefrenceToken(){

        return sharedPreferences.getString(AppConstants.NOTIFICATION_FIREBASE_REFRENCE_TOKEN,"");
    }
}
