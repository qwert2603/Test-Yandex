package com.qwert2603.testyandex.di;

import com.qwert2603.testyandex.artist_details.ArtistDetailsPresenter;
import com.qwert2603.testyandex.artist_list.ArtistListPresenter;

import org.mockito.Mockito;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ViewTestModule {
    @Provides
    @Singleton
    ArtistListPresenter provideArtistListPresenter() {
        return Mockito.mock(ArtistListPresenter.class);
    }

    @Provides
    @Singleton
    ArtistDetailsPresenter provideArtistDetailsPresenter() {
        return Mockito.mock(ArtistDetailsPresenter.class);
    }
}
