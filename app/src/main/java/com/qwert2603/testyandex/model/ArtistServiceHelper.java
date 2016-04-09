package com.qwert2603.testyandex.model;

import android.content.Context;

import com.qwert2603.testyandex.TestYandexApplication;

import javax.inject.Inject;
import javax.inject.Named;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public final class ArtistServiceHelper {

    private static final String BASE_URL = "http://download.cdn.yandex.net/mobilization-2016/";

    private static ArtistServiceHelper sArtistServiceHelper;

    public static ArtistServiceHelper get() {
        if (sArtistServiceHelper == null) {
            synchronized (ArtistServiceHelper.class) {
                if (sArtistServiceHelper == null) {
                    sArtistServiceHelper = new ArtistServiceHelper();
                }
            }
        }
        return sArtistServiceHelper;
    }

    @Inject
    Context mAppContext;

    @Inject
    @Named(CacheInterceptor.TAG)
    Interceptor mInterceptor;

    private ArtistServiceHelper() {
        TestYandexApplication.getAppComponent().inject(ArtistServiceHelper.this);
    }

    public ArtistService getArtistService() {
        // создаем OkHttpClient, способный кешировать данные.
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.cache(new Cache(mAppContext.getFilesDir(), 10 * 1024 * 1024));   // 10 Мб
        client.addInterceptor(mInterceptor);
        client.addNetworkInterceptor(mInterceptor);

        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .client(client.build())
                .build();

        return retrofit.create(ArtistService.class);
    }

}
