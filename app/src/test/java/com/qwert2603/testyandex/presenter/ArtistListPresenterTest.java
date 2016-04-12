package com.qwert2603.testyandex.presenter;

import com.qwert2603.testyandex.BuildConfig;
import com.qwert2603.testyandex.TestApplication;
import com.qwert2603.testyandex.artist_list.ArtistListPresenter;
import com.qwert2603.testyandex.artist_list.ArtistListView;
import com.qwert2603.testyandex.di.TestComponent;
import com.qwert2603.testyandex.model.DataManager;
import com.qwert2603.testyandex.model.entity.Artist;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 19, application = TestApplication.class)
public class ArtistListPresenterTest {

    private ArtistListPresenter mArtistListPresenter;

    @Inject
    DataManager mDataManageMock;

    @Inject
    ArtistListView mArtistListViewMock;

    @Inject
    List<Artist> mArtistList;

    @Before
    public void setUp() {
        TestComponent testComponent = (TestComponent) TestApplication.getAppComponent();
        testComponent.inject(ArtistListPresenterTest.this);
    }

    @Test
    public void testShowList() {
        Mockito.doAnswer(invocation -> Observable.just(mArtistList))
                .when(mDataManageMock).getArtistList(true);

        mArtistListPresenter = new ArtistListPresenter();

        mArtistListPresenter.bindView(mArtistListViewMock);
        mArtistListPresenter.onViewReady();

        Mockito.verify(mArtistListViewMock, Mockito.times(1)).showList(mArtistList);
        Mockito.verify(mArtistListViewMock, Mockito.times(1)).setRefreshingConfig(true, false);

        Mockito.verify(mArtistListViewMock, Mockito.never()).showNoInternet(false);
        Mockito.verify(mArtistListViewMock, Mockito.never()).showNoInternet(true);
        Mockito.verify(mArtistListViewMock, Mockito.never()).showNothingFound();
        Mockito.verify(mArtistListViewMock, Mockito.never()).showEmpty();
        Mockito.verify(mArtistListViewMock, Mockito.never()).showError();
    }

    @Test
    public void testShowEmpty() {
        Mockito.doAnswer(invocation -> Observable.just(Collections.emptyList()))
                .when(mDataManageMock).getArtistList(true);

        mArtistListPresenter = new ArtistListPresenter();

        mArtistListPresenter.bindView(mArtistListViewMock);
        mArtistListPresenter.onViewReady();

        Mockito.verify(mArtistListViewMock, Mockito.times(1)).showEmpty();

        Mockito.verify(mArtistListViewMock, Mockito.never()).showList(mArtistList);
        Mockito.verify(mArtistListViewMock, Mockito.never()).showNoInternet(false);
        Mockito.verify(mArtistListViewMock, Mockito.never()).showNoInternet(true);
        Mockito.verify(mArtistListViewMock, Mockito.never()).showNothingFound();
        Mockito.verify(mArtistListViewMock, Mockito.never()).showError();
    }

    @Test
    public void testShowError() {
        Mockito.doAnswer(invocation -> Observable.error(new NullPointerException()))
                .when(mDataManageMock).getArtistList(true);

        mArtistListPresenter = new ArtistListPresenter();

        mArtistListPresenter.bindView(mArtistListViewMock);
        mArtistListPresenter.onViewReady();

        Mockito.verify(mArtistListViewMock, Mockito.times(1)).showError();

        Mockito.verify(mArtistListViewMock, Mockito.never()).showList(mArtistList);
        Mockito.verify(mArtistListViewMock, Mockito.never()).showNoInternet(false);
        Mockito.verify(mArtistListViewMock, Mockito.never()).showNoInternet(true);
        Mockito.verify(mArtistListViewMock, Mockito.never()).showNothingFound();
        Mockito.verify(mArtistListViewMock, Mockito.never()).showEmpty();
    }

    @Test
    public void testShowNoInternet() {
        Mockito.doAnswer(invocation -> Observable.error(new DataManager.NoInternetException()))
                .when(mDataManageMock).getArtistList(true);

        mArtistListPresenter = new ArtistListPresenter();

        mArtistListPresenter.bindView(mArtistListViewMock);
        mArtistListPresenter.onViewReady();

        Mockito.verify(mArtistListViewMock, Mockito.times(1)).showNoInternet(false);

        Mockito.verify(mArtistListViewMock, Mockito.never()).showList(mArtistList);
        Mockito.verify(mArtistListViewMock, Mockito.never()).showError();
        Mockito.verify(mArtistListViewMock, Mockito.never()).showNoInternet(true);
        Mockito.verify(mArtistListViewMock, Mockito.never()).showNothingFound();
        Mockito.verify(mArtistListViewMock, Mockito.never()).showEmpty();
    }

}
