package com.satvatinfosole.ssss.sangam.notification;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.satvatinfosole.ssss.sangam.sharedpreference.PreferenceData;

/**
 * Created by SATHISH on 11/22/2018.
 */
public class MyFirebaseInstanceIDService  extends FirebaseInstanceIdService {
    private static final String TAG = MyFirebaseInstanceIDService.class.getSimpleName();
    PreferenceData preferenceData;
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String refreshedToken) {
        Log.e(TAG,"refreshedToken ->"+refreshedToken);
        preferenceData =new PreferenceData(this);
        preferenceData.setFirebaseRefrenceToken(refreshedToken);

    }
}
