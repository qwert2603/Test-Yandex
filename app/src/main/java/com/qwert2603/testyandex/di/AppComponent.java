package com.qwert2603.testyandex.di;

import com.qwert2603.testyandex.artist_details.ArtistDetailsFragment;
import com.qwert2603.testyandex.artist_details.ArtistDetailsPresenter;
import com.qwert2603.testyandex.artist_list.ArtistListAdapter;
import com.qwert2603.testyandex.artist_list.ArtistListFragment;
import com.qwert2603.testyandex.artist_list.ArtistListPresenter;
import com.qwert2603.testyandex.model.ArtistServiceHelper;
import com.qwert2603.testyandex.model.CacheInterceptor;
import com.qwert2603.testyandex.model.DataManager;
import com.qwert2603.testyandex.model.InternetHelper;
import com.qwert2603.testyandex.model.NetworkCacheInterceptor;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Компонент приложения для Dagger.
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

    void inject(ArtistListAdapter.ArtistDetailsViewHolder artistDetailsViewHolder);

    void inject(InternetHelper internetHelper);

    void inject(NetworkCacheInterceptor networkCacheInterceptor);
}
