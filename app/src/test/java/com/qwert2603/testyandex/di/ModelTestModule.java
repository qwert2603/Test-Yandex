package com.qwert2603.testyandex.di;

import com.qwert2603.testyandex.Const;
import com.qwert2603.testyandex.model.ArtistService;
import com.qwert2603.testyandex.model.ArtistServiceHelper;
import com.qwert2603.testyandex.model.CacheInterceptor;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@Module
public class ModelTestModule {
    @Provides
    @Singleton
    ArtistService provideArtistService() {
        return ArtistServiceHelper.get().getArtistService();
    }

    @Provides
    @Singleton
    @Named(Const.CACHE_INTERCEPTOR)
    Interceptor provideInterceptor() {
        return new CacheInterceptor();
    }

    @Provides
    @Singleton
    @Named(Const.UI_THREAD)
    Scheduler provideSchedulerUI() {
        return AndroidSchedulers.mainThread();
    }


    @Provides
    @Singleton
    @Named(Const.IO_THREAD)
    Scheduler provideSchedulerIO() {
        return Schedulers.io();
    }
}
