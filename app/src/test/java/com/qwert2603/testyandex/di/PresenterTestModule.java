package com.qwert2603.testyandex.di;

import com.qwert2603.testyandex.TestUtils;
import com.qwert2603.testyandex.artist_details.ArtistDetailsView;
import com.qwert2603.testyandex.artist_list.ArtistListView;
import com.qwert2603.testyandex.model.DataManager;
import com.qwert2603.testyandex.model.entity.Artist;

import org.mockito.Mockito;

import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class PresenterTestModule {

    @Provides
    @Singleton
    DataManager provideDataManager() {
        return Mockito.mock(DataManager.class);
    }

    @Provides
    @Singleton
    ArtistListView provideArtistListView() {
        return Mockito.mock(ArtistListView.class);
    }

    @Provides
    @Singleton
    List<Artist> provideArtistList() {
        return TestUtils.getTestArtistList();
    }

    @Provides
    @Singleton
    ArtistDetailsView provideArtistDetailsView() {
        return Mockito.mock(ArtistDetailsView.class);
    }

}
