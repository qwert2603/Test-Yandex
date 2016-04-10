package com.qwert2603.testyandex.di;

import com.qwert2603.testyandex.artist_details.ArtistDetailsFragment;
import com.qwert2603.testyandex.artist_details.ArtistDetailsPresenter;
import com.qwert2603.testyandex.artist_list.ArtistListFragment;
import com.qwert2603.testyandex.artist_list.ArtistListPresenter;
import com.qwert2603.testyandex.model.ArtistServiceHelper;
import com.qwert2603.testyandex.model.CacheInterceptor;
import com.qwert2603.testyandex.model.DataManager;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Компонент прилжения для Dagger.
 */
@Singleton
@Component(modules = {AppModule.class, ModelModule.class, PresenterModule.class, ViewModule.class})
public interface AppComponent {

    void inject(DataManager dataManager);

    void inject(CacheInterceptor cacheInterceptor);

    void inject(ArtistServiceHelper artistServiceHelper);

    void inject(ArtistListPresenter artistListPresenter);

    void inject(ArtistDetailsPresenter artistDetailsPresenter);

    void inject(ArtistListFragment artistListFragment);

    void inject(ArtistDetailsFragment artistDetailsFragment);

}
