package com.qwert2603.testyandex.model;

import android.content.Context;

import com.qwert2603.testyandex.TestYandexApplication;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Класс для организации кеширования результатов запросов, сделанных с помощью okhttp3.
 * Если есть подключение к интернету, данные будут загружены заново.
 * Если нет - будет загружена кешированная версия, если она есть. (максимум 4-х недельной давности).
 */
public class NetworkCacheInterceptor implements Interceptor {

    @Inject
    Context mAppContext;

    @Inject
    InternetHelper mInternetHelper;

    public NetworkCacheInterceptor() {
        TestYandexApplication.getAppComponent().inject(NetworkCacheInterceptor.this);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Request.Builder request = originalRequest.newBuilder();
        Response response = chain.proceed(request.build());
        return response.newBuilder()
                .removeHeader("Pragma")
                .header("Cache-Control", "max-stale=2419200")   // 4 недели
                .build();
    }
}
