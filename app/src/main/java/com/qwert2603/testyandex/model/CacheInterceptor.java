package com.qwert2603.testyandex.model;

import android.content.Context;
import android.support.annotation.NonNull;

import com.qwert2603.testyandex.TestYandexApplication;
import com.qwert2603.testyandex.util.InternetUtils;

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
public final class CacheInterceptor implements Interceptor {

    public static final String TAG = "CacheInterceptor";

    private static CacheInterceptor sCacheInterceptor;

    public static CacheInterceptor get() {
        if (sCacheInterceptor == null) {
            synchronized (CacheInterceptor.class) {
                if (sCacheInterceptor == null) {
                    sCacheInterceptor = new CacheInterceptor();
                }
            }
        }
        return sCacheInterceptor;
    }

    @Inject
    Context mAppContext;

    private CacheInterceptor() {
        TestYandexApplication.getAppComponent().inject(CacheInterceptor.this);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Request.Builder request = originalRequest.newBuilder();
        if (InternetUtils.isInternetConnected(mAppContext)) {
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
        if (InternetUtils.isInternetConnected(mAppContext)) {
            cacheHeaderValue = "public, max-age=2419200";   // 4 недели
        } else {
            cacheHeaderValue = "public, only-if-cached, max-stale=2419200";   // 4 недели
        }
        return cacheHeaderValue;
    }
}
