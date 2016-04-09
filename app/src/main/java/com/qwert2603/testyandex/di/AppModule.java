package com.qwert2603.testyandex.di;

import android.content.Context;

import com.qwert2603.testyandex.TestYandexApplication;
import com.qwert2603.testyandex.model.ArtistService;
import com.qwert2603.testyandex.model.ArtistServiceHelper;
import com.qwert2603.testyandex.model.CacheInterceptor;
import com.qwert2603.testyandex.model.DataManager;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;

@Module
public class AppModule {

    @Provides
    @Singleton
    Context provideTestYandexApplication() {
        return TestYandexApplication.getTestYandexApplication();
    }

    @Provides
    @Singleton
    ArtistService provideArtistService() {
        return ArtistServiceHelper.get().getArtistService();
    }

    @Provides
    @Singleton
    DataManager provideDataManager() {
        return DataManager.get();
    }

    @Provides
    @Singleton
    @Named(CacheInterceptor.TAG)
    Interceptor provideCacheInterceptor() {
        return CacheInterceptor.get();
    }

}
