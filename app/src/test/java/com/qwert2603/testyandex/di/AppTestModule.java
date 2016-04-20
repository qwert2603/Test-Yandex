package com.qwert2603.testyandex.di;

import android.content.Context;

import com.qwert2603.testyandex.TestApplication;
import com.qwert2603.testyandex.TestUtils;
import com.qwert2603.testyandex.base.BaseRecyclerViewAdapter;
import com.qwert2603.testyandex.model.entity.Artist;

import org.mockito.Mockito;

import java.util.List;

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

    @Provides
    @Singleton
    List<Artist> provideArtistList() {
        return TestUtils.getTestArtistList();
    }

    @Provides
    @Singleton
    BaseRecyclerViewAdapter.ClickCallbacks provideClickCallbacks() {
        return Mockito.mock(BaseRecyclerViewAdapter.ClickCallbacks.class);
    }

    @Provides
    @Singleton
    BaseRecyclerViewAdapter.LongClickCallbacks provideLongClickCallbacks() {
        return Mockito.mock(BaseRecyclerViewAdapter.LongClickCallbacks.class);
    }
}
