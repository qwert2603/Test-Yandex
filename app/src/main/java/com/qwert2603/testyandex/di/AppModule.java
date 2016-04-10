package com.qwert2603.testyandex.di;

import android.content.Context;

import com.qwert2603.testyandex.TestYandexApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Модуль для поставки Context приложения для Dagger.
 */
@Module
public class AppModule {

    private TestYandexApplication mTestYandexApplication;

    public AppModule(TestYandexApplication testYandexApplication) {
        mTestYandexApplication = testYandexApplication;
    }

    @Provides
    @Singleton
    Context provideTestYandexApplication() {
        return mTestYandexApplication.getApplicationContext();
    }

}
