package com.qwert2603.testyandex.di;

import com.qwert2603.testyandex.artist_details.ArtistDetailsPresenter;
import com.qwert2603.testyandex.artist_list.ArtistListPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Модуль для удовлетворения зависимостей представления для Dagger.
 */
@Module
public class ViewModule {
    @Provides
    ArtistListPresenter provideArtistListPresenter() {
        return new ArtistListPresenter();
    }

    @Provides
    ArtistDetailsPresenter provideArtistDetailsPresenter() {
        return new ArtistDetailsPresenter();
    }
}
