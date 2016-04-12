package com.qwert2603.testyandex.presenter;

import android.widget.ImageView;

import com.qwert2603.testyandex.BaseTest;
import com.qwert2603.testyandex.TestUtils;
import com.qwert2603.testyandex.artist_details.ArtistDetailsPresenter;
import com.qwert2603.testyandex.artist_details.ArtistDetailsView;
import com.qwert2603.testyandex.model.DataManager;
import com.qwert2603.testyandex.model.entity.Artist;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.inject.Inject;

import rx.Observable;

/**
 * Тест для {@link ArtistDetailsPresenter}.
 * Тестируется корректность методов:
 * {@link ArtistDetailsPresenter#setArtist(Artist)}
 * {@link ArtistDetailsPresenter#setArtistId(int)}
 * {@link ArtistDetailsPresenter#onFabClicked()} (int)}
 */
public class ArtistDetailsPresenterTest extends BaseTest {

    @Inject
    ArtistDetailsView mArtistDetailsViewMock;

    @Inject
    DataManager mDataManageMock;

    @Before
    public void setUp() {
        getTestComponent().inject(ArtistDetailsPresenterTest.this);
        Mockito.when(mArtistDetailsViewMock.getCoverImageView()).thenReturn(Mockito.mock(ImageView.class));
    }

    @Test
    public void testSetArtist() {
        Artist toveLoArtist = TestUtils.getTestArtistList().get(0);

        ArtistDetailsPresenter artistDetailsPresenter = new ArtistDetailsPresenter();

        artistDetailsPresenter.setArtist(toveLoArtist);
        artistDetailsPresenter.setCoverType(ArtistDetailsPresenter.CoverType.BIG);
        artistDetailsPresenter.bindView(mArtistDetailsViewMock);
        artistDetailsPresenter.onViewReady();

        String genresList = toveLoArtist.getGenres().toString();
        Mockito.verify(mArtistDetailsViewMock).showName(toveLoArtist.getName());
        Mockito.verify(mArtistDetailsViewMock).showDescription(toveLoArtist.getDescription());
        Mockito.verify(mArtistDetailsViewMock).showGenres(genresList.substring(1, genresList.length() - 1));
        Mockito.verify(mArtistDetailsViewMock).showTracksAndAlbums(toveLoArtist.getTracks(), toveLoArtist.getAlbums());
        Mockito.verify(mArtistDetailsViewMock).getCoverImageView();
        Mockito.verify(mArtistDetailsViewMock).setFabVisibility(toveLoArtist.getLink() != null);

        artistDetailsPresenter.onFabClicked();

        Mockito.verify(mArtistDetailsViewMock).moveOnAddress(toveLoArtist.getLink());
    }

    @Test
    public void testSetArtistId() {
        Artist neYoArtist = TestUtils.getTestArtistList().get(1);

        Mockito.when(mDataManageMock.getArtistById(neYoArtist.getId(), false)).thenReturn(Observable.just(neYoArtist));

        ArtistDetailsPresenter artistDetailsPresenter = new ArtistDetailsPresenter();

        artistDetailsPresenter.setArtistId(2915);
        artistDetailsPresenter.setCoverType(ArtistDetailsPresenter.CoverType.BIG);
        artistDetailsPresenter.bindView(mArtistDetailsViewMock);
        artistDetailsPresenter.onViewReady();

        String genresList = neYoArtist.getGenres().toString();
        Mockito.verify(mArtistDetailsViewMock).showName(neYoArtist.getName());
        Mockito.verify(mArtistDetailsViewMock).showDescription(neYoArtist.getDescription());
        Mockito.verify(mArtistDetailsViewMock).showGenres(genresList.substring(1, genresList.length() - 1));
        Mockito.verify(mArtistDetailsViewMock).showTracksAndAlbums(neYoArtist.getTracks(), neYoArtist.getAlbums());
        Mockito.verify(mArtistDetailsViewMock).getCoverImageView();
        Mockito.verify(mArtistDetailsViewMock).setFabVisibility(neYoArtist.getLink() != null);
    }

}
