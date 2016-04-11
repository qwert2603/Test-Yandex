package com.qwert2603.testyandex.di;

import com.qwert2603.testyandex.Const;
import com.qwert2603.testyandex.TestArtistService;
import com.qwert2603.testyandex.model.ArtistService;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@Module
public class ModelTestModule {

    @Provides
    @Singleton
    ArtistService provideArtistService() {
        return new TestArtistService();
    }

    @Provides
    @Singleton
    @Named(Const.UI_THREAD)
    Scheduler provideSchedulerUI() {
        return AndroidSchedulers.mainThread();
        //return Schedulers.immediate();
    }

    @Provides
    @Singleton
    @Named(Const.IO_THREAD)
    Scheduler provideSchedulerIO() {
        return Schedulers.io();
        //return Schedulers.immediate();
    }

}
