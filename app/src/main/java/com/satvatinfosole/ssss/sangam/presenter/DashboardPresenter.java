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
import com.satvatinfosole.ssss.sangam.interactor.DashBoardInteractor;
import com.satvatinfosole.ssss.sangam.view.DashbordViews;
import com.satvatinfosole.ssss.sangam.view.LoginViews;
import com.satvatinfosole.ssss.sangam.volleyservice.VolleySingleton;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by SATHISH on 10/23/2018.
 */
public class DashboardPresenter implements DashBoardInteractor {


    DashbordViews dashbordViews;
    Context context;

    public DashboardPresenter(DashbordViews dashbordViews, Context context) {
        this.dashbordViews = dashbordViews;
        this.context = context;
    }

    @Override
    public void getDashBoard(final String devoteeid, final String activation_key) {
        dashbordViews.showProgress();
        String url = ApiConstants.BASE_URL + ApiConstants.DASHBOARD;



        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(DashboardPresenter.class.getSimpleName(),"success response ---->"+response);
                dashbordViews.hideProgress();
                dashbordViews.getResponseDashbordSuccess(response);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(DashboardPresenter.class.getSimpleName(),"error response ---->"+error.toString());
                dashbordViews.hideProgress();
                if (error instanceof NetworkError) {

                }
                else if (error instanceof ServerError) {
                    dashbordViews.getResponseDashboardFailiur( "Server error!");

                } else if (error instanceof AuthFailureError) {
                    dashbordViews.getResponseDashboardFailiur( "AuthFailure error!");

                } else if (error instanceof ParseError) {
                    dashbordViews.getResponseDashboardFailiur( "Parse error!");

                } else if (error instanceof NoConnectionError) {
                    dashbordViews.getResponseDashboardFailiur( "NoConnection error!");

                } else if (error instanceof TimeoutError) {

                    dashbordViews.getResponseDashboardFailiur( "Timeout error!");

                }

            }
        }) {


            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("devoteeid", devoteeid);
                params.put("activation_key", activation_key);

                Log.e(LogInPresenter.class.getSimpleName(),"params--->"+ params.toString());

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
    public void getFlashNews() {
        dashbordViews.showProgress();
        String url = ApiConstants.BASE_URL + ApiConstants.FLASH_NEWS;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(LogInPresenter.class.getSimpleName(),"success response ---->"+response);
                dashbordViews.hideProgress();
                dashbordViews.getResponseFlashNewsSuccess(response);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(DashboardPresenter.class.getSimpleName(),"error response ---->"+error.toString());
                dashbordViews.hideProgress();
                if (error instanceof NetworkError) {

                }
                else if (error instanceof ServerError) {
                    dashbordViews.getResponseFlashNewsFailiur( "Server error!");

                } else if (error instanceof AuthFailureError) {
                    dashbordViews.getResponseFlashNewsFailiur( "AuthFailure error!");

                } else if (error instanceof ParseError) {
                    dashbordViews.getResponseFlashNewsFailiur( "Parse error!");

                } else if (error instanceof NoConnectionError) {
                    dashbordViews.getResponseFlashNewsFailiur( "NoConnection error!");

                } else if (error instanceof TimeoutError) {

                    dashbordViews.getResponseFlashNewsFailiur( "Timeout error!");

                }

            }
        }) {


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
