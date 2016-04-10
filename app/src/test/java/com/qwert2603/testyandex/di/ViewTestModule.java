package com.qwert2603.testyandex.di;

import com.qwert2603.testyandex.artist_details.ArtistDetailsPresenter;
import com.qwert2603.testyandex.artist_list.ArtistListPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static org.mockito.Mockito.mock;

@Module
public class ViewTestModule {

    @Provides
    @Singleton
    ArtistListPresenter provideArtistListPresenter() {
        return mock(ArtistListPresenter.class);
    }

    @Provides
    @Singleton
    ArtistDetailsPresenter provideArtistDetailsPresenter() {
        return mock(ArtistDetailsPresenter.class);
    }

}
