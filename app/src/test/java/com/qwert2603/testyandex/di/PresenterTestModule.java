package com.qwert2603.testyandex.di;

import com.qwert2603.testyandex.model.DataManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class PresenterTestModule {

    @Provides
    @Singleton
    DataManager provideDataManager() {
        return DataManager.get();
    }

}
