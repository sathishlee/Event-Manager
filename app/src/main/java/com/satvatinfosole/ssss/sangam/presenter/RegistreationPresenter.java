package com.satvatinfosole.ssss.sangam.presenter;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.satvatinfosole.ssss.sangam.constants.ApiConstants;
import com.satvatinfosole.ssss.sangam.interactor.RegistrationInteractor;
import com.satvatinfosole.ssss.sangam.view.RegistrationViews;
import com.satvatinfosole.ssss.sangam.volleyservice.VolleySingleton;

import java.util.HashMap;
import java.util.Map;

public class RegistreationPresenter implements RegistrationInteractor {

    RegistrationViews registrationViews;
    Context context;

    public RegistreationPresenter(RegistrationViews registrationViews, Context context) {
        this.registrationViews = registrationViews;
        this.context = context;
    }


    @Override
    public void registration(final String txt_first_name, final String txt_last_name, final String txt_father_name, final String txt_gender, final String txt_spouse_name, final String txt_dob, final String txt_address, final String txt_city, final String txt_state, final String txt_country, final String txt_pincode, final String txt_mobile, final String txt_landline, final String txt_reference, final String txt_reference_info, final String txt_userid, final String txt_password, String requestMailId, String ActivationKey) {

        registrationViews.showProgress();
        String strMail = requestMailId.replace("@", "%40");
        String url = ApiConstants.BASE_URL + ApiConstants.CHECK_REGISTRATION + strMail + "/" + ActivationKey;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(RegistreationPresenter.class.getSimpleName(), "success response ---->" + response);
                registrationViews.hideProgress();
                registrationViews.showRegistrationSuccess(response);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(RegistreationPresenter.class.getSimpleName(), "error response ---->" + error.toString());
                registrationViews.hideProgress();
                registrationViews.shoRegistrationError(error.toString());

            }
        }) {


            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("txt_first_name", txt_first_name);
                params.put("txt_last_name", txt_last_name);
                params.put("txt_father_name", txt_father_name);
                params.put("txt_gender", txt_gender);
                params.put("txt_spouse_name", txt_spouse_name);
                params.put("txt_dob", txt_dob);
                params.put("txt_address", txt_address);
                params.put("txt_city", txt_city);
                params.put("txt_state", txt_state);
                params.put("txt_country", txt_country);
                params.put("txt_pincode", txt_pincode);
                params.put("txt_mobile", txt_mobile);
                params.put("txt_landline", txt_landline);
                params.put("txt_reference", txt_reference);
                params.put("txt_reference_info", txt_reference_info);
                params.put("txt_userid", txt_userid);
                params.put("txt_password", txt_password);
                params.put("btn_submit", "123");

                Log.e(RegistreationPresenter.class.getSimpleName(), "params--->" + params.toString());

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String credentials = "admin" + ":" + "1234";
                String base64EncodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.DEFAULT);
                HashMap<String, String> header = new HashMap<>();
//                header.put("Content-Type", "application/x-www-from-urlencoded; charset=utf-8");
                header.put("Authorization", "Basic " + base64EncodedCredentials);
                Log.e("Credentials ", "Basic " + base64EncodedCredentials.toString());

                return header;
            }

//            public String getBodyContentType() {
//                return "application/x-www-from-urlencoded; charset=utf-8";
//            }

            public int getMethod() {
                return Method.POST;
            }
        };
        // Adding request to request queue
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
        VolleySingleton.getInstance(context).getRequestQueue().getCache().remove(url);
    }

    @Override
    public void updateProfile(final String devoteeid, final String txt_first_name, final String txt_last_name, final String txt_father_name, final String txt_gender, final String txt_spouse_name, final String txt_dob, final String txt_address, final String txt_city, final String txt_state, final String txt_country, final String txt_pincode, final String txt_mobile, final String txt_landline, final String txt_reference, final String txt_reference_info) {
        registrationViews.showProgress();

        String url = ApiConstants.BASE_URL + ApiConstants.UPDATE_PROFILE;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(RegistreationPresenter.class.getSimpleName(), "success response ---->" + response);
                registrationViews.hideProgress();
                registrationViews.showRegistrationSuccess(response);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(RegistreationPresenter.class.getSimpleName(), "error response ---->" + error.toString());
                registrationViews.hideProgress();
                registrationViews.shoRegistrationError(error.toString());

            }
        }) {


            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("devoteeid", devoteeid);
                params.put("txt_first_name", txt_first_name);
                params.put("txt_last_name", txt_last_name);
                params.put("txt_father_name", txt_father_name);
                params.put("txt_gender", txt_gender);
                params.put("txt_spouse_name", txt_spouse_name);
                params.put("txt_dob", txt_dob);
                params.put("txt_address", txt_address);
                params.put("txt_city", txt_city);
                params.put("txt_state", txt_state);
                params.put("txt_country", txt_country);
                params.put("txt_pincode", txt_pincode);
                params.put("txt_mobile", txt_mobile);
                params.put("txt_landline", txt_landline);
                params.put("txt_reference", txt_reference);
                params.put("txt_reference_info", txt_reference_info);

//                params.put("txt_userid", txt_userid);
//                params.put("txt_password", txt_password);
//                params.put("btn_submit", "123");

                Log.e(RegistreationPresenter.class.getSimpleName(), "params--->" + params.toString());

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String credentials = "admin" + ":" + "1234";
                String base64EncodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.DEFAULT);
                HashMap<String, String> header = new HashMap<>();
//                header.put("Content-Type", "application/x-www-from-urlencoded; charset=utf-8");
                header.put("Authorization", "Basic " + base64EncodedCredentials);
                Log.e("Credentials ", "Basic " + base64EncodedCredentials.toString());

                return header;
            }

//            public String getBodyContentType() {
//                return "application/x-www-from-urlencoded; charset=utf-8";
//            }

            public int getMethod() {
                return Method.POST;
            }
        };
        // Adding request to request queue
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
        VolleySingleton.getInstance(context).getRequestQueue().getCache().remove(url);

    }


    @Override
    public void postEmailAddress(final String strEmailId) {

        registrationViews.showProgress();
        String url = ApiConstants.BASE_URL + ApiConstants.PRIMARY_REGISTRATION;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(RegistreationPresenter.class.getSimpleName(), "success response ---->" + response);
                registrationViews.hideProgress();
                registrationViews.getRequestEmailSuccess(response);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e(RegistreationPresenter.class.getSimpleName(), "error response ---->" + error.toString());

                registrationViews.hideProgress();

                registrationViews.getRequestEmailError(error.toString());

            }
        }) {


            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();

                params.put("txt_emailid", strEmailId);

                Log.e(RegistreationPresenter.class.getSimpleName(), "params--->" + params.toString());

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String credentials = "admin" + ":" + "1234";
                String base64EncodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.DEFAULT);
                HashMap<String, String> header = new HashMap<>();
//                header.put("Content-Type", "application/x-www-from-urlencoded; charset=utf-8");
                header.put("Authorization", "Basic " + base64EncodedCredentials);
                Log.e("Credentials ", "Basic " + base64EncodedCredentials.toString());

                return header;
            }

//            public String getBodyContentType() {
//                return "application/x-www-from-urlencoded; charset=utf-8";
//            }

            public int getMethod() {
                return Method.POST;
            }
        };
        // Adding request to request queue
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
        VolleySingleton.getInstance(context).getRequestQueue().getCache().remove(url);

    }

    @Override
    public void changePassword(final String strDevoteeId, final String strPassword) {
        registrationViews.showProgress();
        String url = ApiConstants.BASE_URL + ApiConstants.CHANGE_PASSWORD;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(RegistreationPresenter.class.getSimpleName(), "success response ---->" + response);
                registrationViews.hideProgress();
                registrationViews.getRequestStatusSuccess(response);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(RegistreationPresenter.class.getSimpleName(), "error response ---->" + error.toString());
                registrationViews.hideProgress();
                registrationViews.getRequestStatusError(error.toString());

            }
        }) {


            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();

                params.put("devoteeid", strDevoteeId);
                params.put("password", strPassword);

                Log.e(RegistreationPresenter.class.getSimpleName(), "params--->" + params.toString());

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String credentials = "admin" + ":" + "1234";
                String base64EncodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.DEFAULT);
                HashMap<String, String> header = new HashMap<>();
//                header.put("Content-Type", "application/x-www-from-urlencoded; charset=utf-8");
                header.put("Authorization", "Basic " + base64EncodedCredentials);
                Log.e("Credentials ", "Basic " + base64EncodedCredentials.toString());

                return header;
            }

//            public String getBodyContentType() {
//                return "application/x-www-from-urlencoded; charset=utf-8";
//            }

            public int getMethod() {
                return Method.POST;
            }
        };
        // Adding request to request queue
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
        VolleySingleton.getInstance(context).getRequestQueue().getCache().remove(url);


    }
}
