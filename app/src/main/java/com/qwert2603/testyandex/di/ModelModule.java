package com.qwert2603.testyandex.di;

import com.qwert2603.testyandex.Const;
import com.qwert2603.testyandex.model.ArtistService;
import com.qwert2603.testyandex.model.ArtistServiceHelper;
import com.qwert2603.testyandex.model.InternetHelper;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Модуль для удовлетворения зависимостей модели для Dagger.
 */
@Module
public class ModelModule {

    @Provides
    @Singleton
    ArtistService provideArtistService() {
        return new ArtistServiceHelper().getArtistService(Const.BASE_URL);
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

    @Provides
    @Singleton
    InternetHelper provideTestYandexApplication() {
        return new InternetHelper();
    }

}
