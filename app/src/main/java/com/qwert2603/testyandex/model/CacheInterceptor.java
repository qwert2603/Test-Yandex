package com.qwert2603.testyandex.model;

import android.content.Context;
import android.support.annotation.NonNull;

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
        }
        Response response = chain.proceed(request.build());
        return response.newBuilder()
                .removeHeader("Pragma")
                .removeHeader("Cache-Control")
                .header("Cache-Control", cacheControl())
                .build();
    }

    @NonNull
    private String cacheControl() {
        String cacheHeaderValue;
        // выбираем значение http заголовка "Cache-Control" в зависимости от наличия интернета.
        if (mInternetHelper.isInternetConnected()) {
            cacheHeaderValue = "public, max-age=2419200";   // 4 недели
        } else {
            cacheHeaderValue = "public, only-if-cached, max-stale=2419200";   // 4 недели
        }
        return cacheHeaderValue;
    }
}
