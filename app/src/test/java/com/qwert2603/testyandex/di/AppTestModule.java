package com.qwert2603.testyandex.di;

import android.content.Context;

import com.qwert2603.testyandex.TestApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppTestModule {
    private TestApplication mTestApplication;

    public AppTestModule(TestApplication testApplication) {
        mTestApplication = testApplication;
    }

    @Provides
    @Singleton
    Context provideTestApplication() {
        return mTestApplication.getApplicationContext();
    }

}
