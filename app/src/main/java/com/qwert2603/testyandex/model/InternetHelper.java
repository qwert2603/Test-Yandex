package com.qwert2603.testyandex.model;

import android.content.Context;
import android.net.ConnectivityManager;

import com.qwert2603.testyandex.TestYandexApplication;

import javax.inject.Inject;

public class InternetHelper {

    @Inject
    Context mContext;

    public InternetHelper() {
        TestYandexApplication.getAppComponent().inject(InternetHelper.this);
    }

    /**
     * Проверить наличие подключения к интернету.
     */
    public boolean isInternetConnected() {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}