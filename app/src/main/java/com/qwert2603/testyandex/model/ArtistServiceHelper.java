package com.qwert2603.testyandex.model;

import android.content.Context;

import com.qwert2603.testyandex.TestYandexApplication;

import javax.inject.Inject;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ArtistServiceHelper {

    @Inject
    Context mAppContext;

    public ArtistServiceHelper() {
        TestYandexApplication.getAppComponent().inject(ArtistServiceHelper.this);
    }

    public ArtistService getArtistService(String baseUrl) {
        // создаем OkHttpClient, способный кешировать данные.
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.cache(new Cache(mAppContext.getFilesDir(), 10 * 1024 * 1024));   // 10 Мб
        client.addInterceptor(new CacheInterceptor());
        client.addNetworkInterceptor(new NetworkCacheInterceptor());

        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .client(client.build())
                .build();

        return retrofit.create(ArtistService.class);
    }

}
