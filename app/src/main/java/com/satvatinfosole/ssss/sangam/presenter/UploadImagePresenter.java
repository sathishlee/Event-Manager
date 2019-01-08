package com.satvatinfosole.ssss.sangam.presenter;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.satvatinfosole.ssss.sangam.constants.ApiConstants;
import com.satvatinfosole.ssss.sangam.interactor.UploadImageInteractor;
import com.satvatinfosole.ssss.sangam.view.UploadImageViews;
import com.satvatinfosole.ssss.sangam.volleyservice.VolleySingleton;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by SATHISH on 11/17/2018.
 */
public class UploadImagePresenter implements UploadImageInteractor {

    public Context context;
    UploadImageViews uploadImageViews;

    public UploadImagePresenter(Context context, UploadImageViews uploadImageViews) {
        this.context = context;
        this.uploadImageViews = uploadImageViews;
    }

    @Override
    public void uploadImage(final String strDevoteeId, final String strEncodeSource,final String photo_type) {



        uploadImageViews.showProgress();
        String url = ApiConstants.BASE_URL + ApiConstants.UPLOAD_PROFILE_PHOTO;
        Log.e(UploadImagePresenter.class.getSimpleName(), "url  ---->" + url);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(UploadImagePresenter.class.getSimpleName(), "success response ---->" + response);
                uploadImageViews.hideProgress();
                uploadImageViews.imageResponseSuccess(response);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(UploadImagePresenter.class.getSimpleName(),"error response ---->"+error.toString());
                uploadImageViews.hideProgress();
                if (error instanceof NetworkError) {

                }
                else if (error instanceof ServerError) {
                    uploadImageViews.imageResponseError( "Server error!");

                } else if (error instanceof AuthFailureError) {
                    uploadImageViews.imageResponseError( "AuthFailure error!");

                } else if (error instanceof ParseError) {
                    uploadImageViews.imageResponseError( "Parse error!");

                } else if (error instanceof NoConnectionError) {
                    uploadImageViews.imageResponseError( "NoConnection error!");

                } else if (error instanceof TimeoutError) {

                    uploadImageViews.imageResponseError( "Timeout error!");

                }

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();

                params.put("devoteeid", strDevoteeId);
                params.put("devoteeimage", strEncodeSource);
                params.put("photo_type", photo_type);

                Log.e(RegistreationPresenter.class.getSimpleName(),"params--->"+ params.toString());

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
    public void getuploadImage(final String strDevoteeId) {



        uploadImageViews.showProgress();
        String url = ApiConstants.BASE_URL + ApiConstants.VIEW_PROFILE_PHOTO;
        Log.e(UploadImagePresenter.class.getSimpleName(), "url ---->" + url);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(UploadImagePresenter.class.getSimpleName(), "success response ---->" + response);
                uploadImageViews.hideProgress();
                uploadImageViews.imageResponseSuccess(response);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(UploadImagePresenter.class.getSimpleName(),"error response ---->"+error.toString());
                uploadImageViews.hideProgress();
                if (error instanceof NetworkError) {

                }
                else if (error instanceof ServerError) {
                    uploadImageViews.imageResponseError( "Server error!");

                } else if (error instanceof AuthFailureError) {
                    uploadImageViews.imageResponseError( "AuthFailure error!");

                } else if (error instanceof ParseError) {
                    uploadImageViews.imageResponseError( "Parse error!");

                } else if (error instanceof NoConnectionError) {
                    uploadImageViews.imageResponseError( "NoConnection error!");

                } else if (error instanceof TimeoutError) {

                    uploadImageViews.imageResponseError( "Timeout error!");

                }

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("devoteeid", strDevoteeId);

                Log.e(RegistreationPresenter.class.getSimpleName(),"params--->"+ params.toString());

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
