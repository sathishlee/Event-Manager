package com.satvatinfosole.ssss.sangam.application;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.satvatinfosole.ssss.sangam.broadcastReceiver.ConnectivityReceiver;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by SATHISH on 10/29/2018.
 */
public class MyApplication extends MultiDexApplication {
    private static MyApplication mInstance;

    //@Override
//protected void attachBaseContext(Context base) { super.attachBaseContext(base); Multidex.install(this); }
    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);

        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("ThaimaiVhn.realm")
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build();

        Realm.setDefaultConfiguration(config);
        mInstance = this;
    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//        super.attachBaseContext(LocaleHelper.setLocale(base, "en"));
        MultiDex.install(this);
    }
}
