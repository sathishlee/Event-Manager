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
import com.satvatinfosole.ssss.sangam.interactor.EventInteractor;
import com.satvatinfosole.ssss.sangam.model.localDBModel.SangamEvent;
import com.satvatinfosole.ssss.sangam.view.EventViews;
import com.satvatinfosole.ssss.sangam.volleyservice.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by SATHISH on 10/24/2018.
 */
public class EventPresenter implements EventInteractor {
    EventViews eventViews;
    Context context;
    Realm realm;

    public EventPresenter(EventViews eventViews, Context context, Realm realm) {
        this.eventViews = eventViews;
        this.context = context;
        this.realm = realm;

    }

    @Override
    public void getEventList() {

        eventViews.showProgress();
        String url = ApiConstants.BASE_URL + ApiConstants.EVENT_LIST;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(EventPresenter.class.getSimpleName(), "success response ---->" + response);
                eventViews.hideProgress();
                eventViews.showEventSuccess(response);

                try {
                    JSONObject mJsnobject = new JSONObject(response);
                    String status = mJsnobject.getString("status");
                    String message = mJsnobject.getString("message");
                    if (status.equalsIgnoreCase("1")) {
                        JSONArray jsonArray = mJsnobject.getJSONArray("Event_List");
                        RealmResults<SangamEvent> sangamEventRealmResults = null;
                        sangamEventRealmResults = realm.where(SangamEvent.class).findAll();
                        Log.e("Realm size ---->", sangamEventRealmResults.size() + "");
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                realm.delete(SangamEvent.class);
                            }
                        });
                        SangamEvent sangamEventmodel;

                        if (jsonArray.length() != 0) {
                            realm.beginTransaction();
                            for (int i = 0; i < jsonArray.length(); i++) {

                                sangamEventmodel = realm.createObject(SangamEvent.class);

                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                sangamEventmodel.setEvent_id(jsonObject.getString("event_id"));
                                sangamEventmodel.setEvent_sdate(jsonObject.getString("event_sdate"));
                                sangamEventmodel.setEvent_edate(jsonObject.getString("event_edate"));

                                sangamEventmodel.setEvent_sdate_month(getMonth(jsonObject.getString("event_sdate")));
                                sangamEventmodel.setEvent_sdate_year(getYear(jsonObject.getString("event_sdate")));
                                sangamEventmodel.setEvent_edate_month(getMonth(jsonObject.getString("event_edate")));
                                sangamEventmodel.setEvent_edate_year(getYear(jsonObject.getString("event_edate")));


                                sangamEventmodel.setEvent_name(jsonObject.getString("event_name"));
                                sangamEventmodel.setEvent_type(jsonObject.getString("event_type"));
                                sangamEventmodel.setEvent_venue(jsonObject.getString("event_venue"));
                                sangamEventmodel.setEvent_program(jsonObject.getString("event_program"));
                                sangamEventmodel.setEvent_image(jsonObject.getString("event_image"));
                                sangamEventmodel.setEvent_status(jsonObject.getString("event_status"));
                            }
                            realm.commitTransaction();

                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(EventPresenter.class.getSimpleName(), "error response ---->" + error.toString());
                eventViews.hideProgress();
                if (error instanceof NetworkError) {

                } else if (error instanceof ServerError) {
                    eventViews.showEventError("Server error!");

                } else if (error instanceof AuthFailureError) {
                    eventViews.showEventError("AuthFailure error!");

                } else if (error instanceof ParseError) {
                    eventViews.showEventError("Parse error!");

                } else if (error instanceof NoConnectionError) {
                    eventViews.showEventError("NoConnection error!");

                } else if (error instanceof TimeoutError) {

                    eventViews.showEventError("Timeout error!");

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

    private String getMonth(String event_mdate) {
        String month = "0";
        if (event_mdate.equalsIgnoreCase("null")) {

        } else {
            String[] strDate = event_mdate.split("-");
            month = strDate[1];
        }
        return month;
    }

    private String getYear(String event_ydate) {
        String month = "0";
        if (event_ydate.equalsIgnoreCase("null")) {

        } else {
            String[] strDate = event_ydate.split("-");
            month = strDate[2];
        }
        return month;
    }

    private Date toDate(String event_edate) {
        return null;
    }

    @Override
    public void getEventList(final String event_id) {


        eventViews.showProgress();
        String url = ApiConstants.BASE_URL + ApiConstants.EVENT_VENUE;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(LogInPresenter.class.getSimpleName(), "success response ---->" + response);
                eventViews.hideProgress();
                eventViews.showEventSuccess(response);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(DashboardPresenter.class.getSimpleName(), "error response ---->" + error.toString());
                eventViews.hideProgress();
                if (error instanceof NetworkError) {

                } else if (error instanceof ServerError) {
                    eventViews.showEventError("Server error!");

                } else if (error instanceof AuthFailureError) {
                    eventViews.showEventError("AuthFailure error!");

                } else if (error instanceof ParseError) {
                    eventViews.showEventError("Parse error!");

                } else if (error instanceof NoConnectionError) {
                    eventViews.showEventError("NoConnection error!");

                } else if (error instanceof TimeoutError) {

                    eventViews.showEventError("Timeout error!");

                }

            }
        })

        {
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("event_venue", event_id);


                Log.e(LogInPresenter.class.getSimpleName(), "params--->" + params.toString());

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
