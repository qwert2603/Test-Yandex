package com.qwert2603.testyandex.model;

import android.content.Context;

import com.qwert2603.testyandex.TestYandexApplication;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Класс для организации кеширования результатов запросов, сделанных с помощью okhttp3.
 * Если есть подключение к интернету, данные будут загружены заново.
 * Если нет - будет загружена кешированная версия, если она есть. (максимум 4-х недельной давности).
 */
public class CacheInterceptor implements Interceptor {

    @Inject
    Context mAppContext;

    @Inject
    InternetHelper mInternetHelper;

    public CacheInterceptor() {
        TestYandexApplication.getAppComponent().inject(CacheInterceptor.this);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Request.Builder request = originalRequest.newBuilder();
        if (mInternetHelper.isInternetConnected()) {
            // если есть подключение к интернету, обновляем данные.
            request.cacheControl(CacheControl.FORCE_NETWORK);
        } else {
            // если нет подключения к интернету, берер данные из кеша.
            request.cacheControl(CacheControl.FORCE_CACHE);
        }
        return chain.proceed(request.build());
    }
}
