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
import com.satvatinfosole.ssss.sangam.interactor.QuizInteractor;
import com.satvatinfosole.ssss.sangam.model.requestModel.AnswerQuizRequestModel;
import com.satvatinfosole.ssss.sangam.view.QuizView;
import com.satvatinfosole.ssss.sangam.volleyservice.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by SATHISH on 11/13/2018.
 */
public class QuizPresenter implements QuizInteractor {


    Context context;
    QuizView quizView;

    public QuizPresenter(Context context, QuizView quizView) {
        this.context = context;
        this.quizView = quizView;
    }

    @Override
    public void getQuizQuestions(final String StringDevoteeid) {
        quizView.showProgress();
        String url = ApiConstants.BASE_URL + ApiConstants.QUIZ_GET_QUESTION;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(EventPresenter.class.getSimpleName(), "success response ---->" + response);
                quizView.hideProgress();
                quizView.getQuestionSuccess(response);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(EventPresenter.class.getSimpleName(), "error response ---->" + error.toString());
                quizView.hideProgress();
                if (error instanceof NetworkError) {

                } else if (error instanceof ServerError) {
                    quizView.getQuestionError("Server error!");

                } else if (error instanceof AuthFailureError) {
                    quizView.getQuestionError("AuthFailure error!");

                } else if (error instanceof ParseError) {
                    quizView.getQuestionError("Parse error!");

                } else if (error instanceof NoConnectionError) {
                    quizView.getQuestionError("NoConnection error!");

                } else if (error instanceof TimeoutError) {

                    quizView.getQuestionError("Timeout error!");

                }

            }
        })

        {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();

                params.put("devoteeid", StringDevoteeid);


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
    public void postQuizAnswers(final AnswerQuizRequestModel answerQuizRequestModel) {

        quizView.showProgress();
        String url = ApiConstants.BASE_URL + ApiConstants.QUIZ_POST_ANSWER;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(EventPresenter.class.getSimpleName(), "success response ---->" + response);
                quizView.hideProgress();
                quizView.postQuestionSuccess(response);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(EventPresenter.class.getSimpleName(), "error response ---->" + error.toString());
                quizView.hideProgress();
                if (error instanceof NetworkError) {

                } else if (error instanceof ServerError) {
                    quizView.postQuestionError("Server error!");

                } else if (error instanceof AuthFailureError) {
                    quizView.postQuestionError("AuthFailure error!");

                } else if (error instanceof ParseError) {
                    quizView.postQuestionError("Parse error!");

                } else if (error instanceof NoConnectionError) {
                    quizView.postQuestionError("NoConnection error!");

                } else if (error instanceof TimeoutError) {

                    quizView.postQuestionError("Timeout error!");

                }

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();

                params.put("qusername", answerQuizRequestModel.getQusername());
                params.put("quserid", answerQuizRequestModel.getQuserid());
                params.put("qid", answerQuizRequestModel.getQid());
                params.put("questionid", answerQuizRequestModel.getQuestionid());
                params.put("answerid", answerQuizRequestModel.getAnswerid());

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
    public void getScore(final String StringDevoteeid) {

        quizView.showProgress();
        String url = ApiConstants.BASE_URL + ApiConstants.QUIZ_SCORE;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(EventPresenter.class.getSimpleName(), "success response ---->" + response);
                quizView.hideProgress();
                quizView.getScoreSuccess(response);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(EventPresenter.class.getSimpleName(), "error response ---->" + error.toString());
                quizView.hideProgress();
                if (error instanceof NetworkError) {

                } else if (error instanceof ServerError) {
                    quizView.getScoreError("Server error!");

                } else if (error instanceof AuthFailureError) {
                    quizView.getScoreError("AuthFailure error!");

                } else if (error instanceof ParseError) {
                    quizView.getScoreError("Parse error!");

                } else if (error instanceof NoConnectionError) {
                    quizView.getScoreError("NoConnection error!");

                } else if (error instanceof TimeoutError) {

                    quizView.getScoreError("Timeout error!");

                }

            }
        })

        {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();

                params.put("devoteeid", StringDevoteeid);


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
    public void getpreviousQuestions() {


        quizView.showProgress();
        String url = ApiConstants.BASE_URL + ApiConstants.QUIZ_PRIVIOUS;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(EventPresenter.class.getSimpleName(), "success response ---->" + response);
                quizView.hideProgress();
                quizView.getPreviousQuestionSuccess(response);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(EventPresenter.class.getSimpleName(), "error response ---->" + error.toString());
                quizView.hideProgress();
                if (error instanceof NetworkError) {

                } else if (error instanceof ServerError) {
                    quizView.getPreviousQuestionError("Server error!");

                } else if (error instanceof AuthFailureError) {
                    quizView.getPreviousQuestionError("AuthFailure error!");

                } else if (error instanceof ParseError) {
                    quizView.getPreviousQuestionError("Parse error!");

                } else if (error instanceof NoConnectionError) {
                    quizView.getPreviousQuestionError("NoConnection error!");

                } else if (error instanceof TimeoutError) {

                    quizView.getPreviousQuestionError("Timeout error!");

                }

            }
        })

        {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();

//                params.put("devoteeid", StringDevoteeid);


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
    public void getTopRunners() {
        quizView.showProgress();
        String url = ApiConstants.BASE_URL + ApiConstants.QUIZ_RUNNER;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(EventPresenter.class.getSimpleName(), "success response ---->" + response);
                quizView.hideProgress();
                quizView.getTopRunnerSuccess(response);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(EventPresenter.class.getSimpleName(), "error response ---->" + error.toString());
                quizView.hideProgress();
                if (error instanceof NetworkError) {

                } else if (error instanceof ServerError) {
                    quizView.getTopRunnerError("Server error!");

                } else if (error instanceof AuthFailureError) {
                    quizView.getTopRunnerError("AuthFailure error!");

                } else if (error instanceof ParseError) {
                    quizView.getTopRunnerError("Parse error!");

                } else if (error instanceof NoConnectionError) {
                    quizView.getTopRunnerError("NoConnection error!");

                } else if (error instanceof TimeoutError) {

                    quizView.getTopRunnerError("Timeout error!");

                }

            }
        })

        {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();

//                params.put("devoteeid", StringDevoteeid);


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
