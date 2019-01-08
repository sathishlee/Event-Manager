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
import com.satvatinfosole.ssss.sangam.interactor.FeedbackInteractor;
import com.satvatinfosole.ssss.sangam.view.FeedbackViews;
import com.satvatinfosole.ssss.sangam.volleyservice.VolleySingleton;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by SATHISH on 11/15/2018.
 */
public class FeedbackPresenter implements FeedbackInteractor {

    public Context context;
    public FeedbackViews feedbackViews;
    
    public FeedbackPresenter(Context context, FeedbackViews feedbackViews) {
        this.context = context;
        this.feedbackViews = feedbackViews;
    }

    @Override
    public void postFeedback(final String strDevoteeId, final String strFeddback) {


        feedbackViews.showProgress();
        String url = ApiConstants.BASE_URL + ApiConstants.FEEDBACK;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(EventPresenter.class.getSimpleName(), "success response ---->" + response);
                feedbackViews.hideProgress();
                feedbackViews.postFeedbackSuccess(response);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(EventPresenter.class.getSimpleName(),"error response ---->"+error.toString());
                feedbackViews.hideProgress();
                if (error instanceof NetworkError) {

                }
                else if (error instanceof ServerError) {
                    feedbackViews.postFeedbackError( "Server error!");

                } else if (error instanceof AuthFailureError) {
                    feedbackViews.postFeedbackError( "AuthFailure error!");

                } else if (error instanceof ParseError) {
                    feedbackViews.postFeedbackError( "Parse error!");

                } else if (error instanceof NoConnectionError) {
                    feedbackViews.postFeedbackError( "NoConnection error!");

                } else if (error instanceof TimeoutError) {

                    feedbackViews.postFeedbackError( "Timeout error!");

                }

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();

                params.put("txt_devoteeid", strDevoteeId);
                params.put("txt_feedback", strFeddback);

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
