package com.qwert2603.testyandex.di;

import com.qwert2603.testyandex.artist_details.ArtistDetailsFragmentTest;
import com.qwert2603.testyandex.artist_details.ArtistDetailsPresenterTest;
import com.qwert2603.testyandex.artist_list.ArtistListFragmentTest;
import com.qwert2603.testyandex.artist_list.ArtistListPresenterTest;
import com.qwert2603.testyandex.base.BaseFragmentTest;
import com.qwert2603.testyandex.base.BaseRecyclerViewAdapterTest;
import com.qwert2603.testyandex.model.ArtistServiceTest;
import com.qwert2603.testyandex.model.DataManagerTest;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        AppTestModule.class,
        ModelTestModule.class,
        PresenterTestModule.class,
        ViewTestModule.class
})
public interface TestComponent extends AppComponent {

    void inject(BaseFragmentTest baseFragmentTest);

    void inject(BaseRecyclerViewAdapterTest baseRecyclerViewAdapterTest);

    void inject(ArtistServiceTest artistServiceTest);

    void inject(DataManagerTest dataManagerTest);

    void inject(ArtistListPresenterTest artistListPresenterTest);

    void inject(ArtistListFragmentTest artistListFragmentTest);

    void inject(ArtistDetailsPresenterTest artistDetailsPresenterTest);

    void inject(ArtistDetailsFragmentTest artistDetailsFragmentTest);
}
