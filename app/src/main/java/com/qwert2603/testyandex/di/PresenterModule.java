package com.qwert2603.testyandex.di;

import com.qwert2603.testyandex.model.DataManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Модуль для удовлетворения зависимостей презентеров для Dagger.
 */
@Module
public class PresenterModule {

    @Provides
    @Singleton
    DataManager provideDataManager() {
        return DataManager.get();
    }

}
