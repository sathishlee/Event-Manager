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
import com.satvatinfosole.ssss.sangam.interactor.LoginInteractor;
import com.satvatinfosole.ssss.sangam.view.LoginViews;
import com.satvatinfosole.ssss.sangam.volleyservice.VolleySingleton;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by SATHISH on 10/16/2018.
 */
public class LogInPresenter implements LoginInteractor {


    LoginViews loginViews;
    Context context;

    public LogInPresenter(LoginViews loginViews, Context context) {
        this.loginViews = loginViews;
        this.context = context;
    }


    @Override
    public void doLogIn(final String strUserName, final String strPassword, final String strDeviceToken) {
        loginViews.showProgress();
        String url = ApiConstants.BASE_URL + ApiConstants.LOGIN;


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(LogInPresenter.class.getSimpleName(),"success response ---->"+response);
                loginViews.hideProgress();
                loginViews.showLoginSuccess(response);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(LogInPresenter.class.getSimpleName(),"error response ---->"+error.toString());
                loginViews.hideProgress();
                if (error instanceof NetworkError) {

                }
                else if (error instanceof ServerError) {
                    loginViews.showLoginError( "Server error!");

                } else if (error instanceof AuthFailureError) {
                    loginViews.showLoginError( "AuthFailure error!");

                } else if (error instanceof ParseError) {
                    loginViews.showLoginError( "Parse error!");

                } else if (error instanceof NoConnectionError) {
                    loginViews.showLoginError( "NoConnection error!");

                } else if (error instanceof TimeoutError) {

                    loginViews.showLoginError( "Timeout error!");

                }

            }
        }) {


            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", strUserName);
                params.put("password", strPassword);
                params.put("device_token", strDeviceToken);

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
    public void getEmail(final String strKey) {

        loginViews.showProgress();
        String url = ApiConstants.BASE_URL + ApiConstants.CREDENTIAL;


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(LogInPresenter.class.getSimpleName(),"success response ---->"+response);
                loginViews.hideProgress();
                loginViews.showLoginSuccess(response);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(LogInPresenter.class.getSimpleName(),"error response ---->"+error.toString());
                loginViews.hideProgress();
                if (error instanceof NetworkError) {

                }
                else if (error instanceof ServerError) {
                    loginViews.showLoginError( "Server error!");

                } else if (error instanceof AuthFailureError) {
                    loginViews.showLoginError( "AuthFailure error!");

                } else if (error instanceof ParseError) {
                    loginViews.showLoginError( "Parse error!");

                } else if (error instanceof NoConnectionError) {
                    loginViews.showLoginError( "NoConnection error!");

                } else if (error instanceof TimeoutError) {

                    loginViews.showLoginError( "Timeout error!");

                }

            }
        }) {


            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("activation", strKey);


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
    public void getKey(final String strEmail) {


        loginViews.showProgress();
        String url = ApiConstants.BASE_URL + ApiConstants.CREDENTIAL;


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(LogInPresenter.class.getSimpleName(),"success response ---->"+response);
                loginViews.hideProgress();
                loginViews.showLoginSuccess(response);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(LogInPresenter.class.getSimpleName(),"error response ---->"+error.toString());
                loginViews.hideProgress();
                if (error instanceof NetworkError) {

                }
                else if (error instanceof ServerError) {
                    loginViews.showLoginError( "Server error!");

                } else if (error instanceof AuthFailureError) {
                    loginViews.showLoginError( "AuthFailure error!");

                } else if (error instanceof ParseError) {
                    loginViews.showLoginError( "Parse error!");

                } else if (error instanceof NoConnectionError) {
                    loginViews.showLoginError( "NoConnection error!");

                } else if (error instanceof TimeoutError) {

                    loginViews.showLoginError( "Timeout error!");

                }

            }
        }) {


            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", strEmail);


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
}
