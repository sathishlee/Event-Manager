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
import com.satvatinfosole.ssss.sangam.interactor.GalleryInteractor;
import com.satvatinfosole.ssss.sangam.view.GalleryViews;
import com.satvatinfosole.ssss.sangam.volleyservice.VolleySingleton;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by SATHISH on 10/30/2018.
 */
public class GalleryPresenter implements GalleryInteractor {
    public GalleryViews galleryViews;
    public Context context;

    public GalleryPresenter(GalleryViews galleryViews, Context context) {
        this.galleryViews = galleryViews;
        this.context = context;
    }


    @Override
    public void getMediaEventList() {

        galleryViews.showProgress();
        String url = ApiConstants.BASE_URL + ApiConstants.EVENT_MEDIA;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(GalleryPresenter.class.getSimpleName(), "getMediaEventList success response ---->" + response);
                galleryViews.hideProgress();
                galleryViews.showGallerySuccess(response);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(DashboardPresenter.class.getSimpleName(), "getMediaEventList error response ---->" + error.toString());
                galleryViews.hideProgress();
                if (error instanceof NetworkError) {

                } else if (error instanceof ServerError) {
                    galleryViews.showGalleryError("Server error!");

                } else if (error instanceof AuthFailureError) {
                    galleryViews.showGalleryError("AuthFailure error!");

                } else if (error instanceof ParseError) {
                    galleryViews.showGalleryError("Parse error!");

                } else if (error instanceof NoConnectionError) {
                    galleryViews.showGalleryError("NoConnection error!");

                } else if (error instanceof TimeoutError) {

                    galleryViews.showGalleryError("Timeout error!");

                }

            }
        })

        {
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();


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
    public void getPhotoThumbnail(final String media_id, final String media_venu_id) {

        galleryViews.showProgress();
        String url = ApiConstants.BASE_URL + ApiConstants.EVENT_PHOTO_THUMBNAIL;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(GalleryPresenter.class.getSimpleName(), " getPhotoThumbnail success response ---->" + response);
                galleryViews.hideProgress();
                galleryViews.getthumbnailSuccess(response);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(DashboardPresenter.class.getSimpleName(), "error response ---->" + error.toString());
                galleryViews.hideProgress();
                if (error instanceof NetworkError) {

                } else if (error instanceof ServerError) {
                    galleryViews.getthumbnailError("Server error!");

                } else if (error instanceof AuthFailureError) {
                    galleryViews.getthumbnailError("AuthFailure error!");

                } else if (error instanceof ParseError) {
                    galleryViews.getthumbnailError("Parse error!");

                } else if (error instanceof NoConnectionError) {
                    galleryViews.getthumbnailError("NoConnection error!");

                } else if (error instanceof TimeoutError) {

                    galleryViews.getthumbnailError("Timeout error!");

                }

            }
        })

        {
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();

                params.put("txt_pg_media_id", media_id);
                params.put("txt_pg_media_venue_id", media_venu_id);

                Log.e(GalleryPresenter.class.getSimpleName(), "params--->" + params.toString());

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
    public void getVideoThumbnail(String media_id, String media_venu_id) {

    }

    @Override
    public void getPhotoGalleryList(final String media_id, final String media_venu_id) {
        galleryViews.showProgress();
        String url = ApiConstants.BASE_URL + ApiConstants.EVENT_PHOTO_GALARY;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(GalleryPresenter.class.getSimpleName(), "success response ---->" + response);
                galleryViews.hideProgress();
                galleryViews.showGallerySuccess(response);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(DashboardPresenter.class.getSimpleName(), "error response ---->" + error.toString());
                galleryViews.hideProgress();
                if (error instanceof NetworkError) {

                } else if (error instanceof ServerError) {
                    galleryViews.showGalleryError("Server error!");

                } else if (error instanceof AuthFailureError) {
                    galleryViews.showGalleryError("AuthFailure error!");

                } else if (error instanceof ParseError) {
                    galleryViews.showGalleryError("Parse error!");

                } else if (error instanceof NoConnectionError) {
                    galleryViews.showGalleryError("NoConnection error!");

                } else if (error instanceof TimeoutError) {

                    galleryViews.showGalleryError("Timeout error!");

                }

            }
        })

        {
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();

                params.put("txt_pg_media_id", media_id);
                params.put("txt_pg_media_venue_id", media_venu_id);

                Log.e(GalleryPresenter.class.getSimpleName(), "params--->" + params.toString());

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
    public void getVideoGalleryList(final String media_id, final String media_venu_id) {


        galleryViews.showProgress();
        String url = ApiConstants.BASE_URL + ApiConstants.EVENT_VIDEO_GALARY;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(GalleryPresenter.class.getSimpleName(), "success response ---->" + response);
                galleryViews.hideProgress();
                galleryViews.showGallerySuccess(response);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(DashboardPresenter.class.getSimpleName(), "error response ---->" + error.toString());
                galleryViews.hideProgress();
                if (error instanceof NetworkError) {

                } else if (error instanceof ServerError) {
                    galleryViews.showGalleryError("Server error!");

                } else if (error instanceof AuthFailureError) {
                    galleryViews.showGalleryError("AuthFailure error!");

                } else if (error instanceof ParseError) {
                    galleryViews.showGalleryError("Parse error!");

                } else if (error instanceof NoConnectionError) {
                    galleryViews.showGalleryError("NoConnection error!");

                } else if (error instanceof TimeoutError) {

                    galleryViews.showGalleryError("Timeout error!");

                }

            }
        })

        {
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();

//                params.put("txt_vg_media_id",media_id);
//                params.put("vg_mediavenue_id",media_venu_id);

                Log.e(GalleryPresenter.class.getSimpleName(), "params--->" + params.toString());

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
