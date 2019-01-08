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
import com.satvatinfosole.ssss.sangam.interactor.NotificationInteractor;
import com.satvatinfosole.ssss.sangam.model.localDBModel.SangamEvent;
import com.satvatinfosole.ssss.sangam.view.NotificationViews;
import com.satvatinfosole.ssss.sangam.volleyservice.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by SATHISH on 11/23/2018.
 */
public class NotificationPresenter implements NotificationInteractor {
    
    public NotificationPresenter(Context context, NotificationViews notificationViews) {
        this.context = context;
        this.notificationViews = notificationViews;
    }

    public Context context;
            public NotificationViews notificationViews;
    @Override
    public void getNotificationList() {


        notificationViews.showProgress();
        String url = ApiConstants.BASE_URL + ApiConstants.GET_NOTIFICATION_LIST;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(EventPresenter.class.getSimpleName(), "success response ---->" + response);
                notificationViews.hideProgress();
                notificationViews.getNotificationListSuccess(response);
                }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(EventPresenter.class.getSimpleName(), "error response ---->" + error.toString());
                notificationViews.hideProgress();
                if (error instanceof NetworkError) {

                } else if (error instanceof ServerError) {
                    notificationViews.getNotificationListError("Server error!");

                } else if (error instanceof AuthFailureError) {
                    notificationViews.getNotificationListError("AuthFailure error!");

                } else if (error instanceof ParseError) {
                    notificationViews.getNotificationListError("Parse error!");

                } else if (error instanceof NoConnectionError) {
                    notificationViews.getNotificationListError("NoConnection error!");

                } else if (error instanceof TimeoutError) {

                    notificationViews.getNotificationListError("Timeout error!");

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
