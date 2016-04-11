package com.qwert2603.testyandex.di;

import com.qwert2603.testyandex.Const;
import com.qwert2603.testyandex.model.ArtistService;

import org.mockito.Mockito;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;
import rx.schedulers.Schedulers;

@Module
public class ModelTestModule {

    @Provides
    @Singleton
    ArtistService provideArtistService() {
        return Mockito.mock(ArtistService.class);
    }

    @Provides
    @Singleton
    @Named(Const.UI_THREAD)
    Scheduler provideSchedulerUI() {
        return Schedulers.immediate();
    }

    @Provides
    @Singleton
    @Named(Const.IO_THREAD)
    Scheduler provideSchedulerIO() {
        return Schedulers.immediate();
    }

}
