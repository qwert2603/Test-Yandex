package com.qwert2603.testyandex.presenter;

import com.qwert2603.testyandex.BaseTest;
import com.qwert2603.testyandex.artist_list.ArtistListPresenter;
import com.qwert2603.testyandex.artist_list.ArtistListView;
import com.qwert2603.testyandex.model.DataManager;
import com.qwert2603.testyandex.model.entity.Artist;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Тест для {@link ArtistListPresenter}.
 * Тестируется корректность методов:
 * {@link ArtistListPresenter#onReload()} (с включенным и выключенным интернетом).
 * {@link ArtistListPresenter#onArtistAtPositionClicked(int)}
 * {@link ArtistListPresenter#onSearchQueryChanged(String)} (с различными запросами).
 * А также проверяется правильность передачи данных для ArtistListView, если был:
 * - загружен непустой список
 * - загружен пустой список
 * - произошла ошибка загрузки
 * - не было интернета.
 */
public class ArtistListPresenterTest extends BaseTest {

    @Inject
    DataManager mDataManageMock;

    @Inject
    ArtistListView mArtistListViewMock;

    @Inject
    List<Artist> mArtistList;

    @Before
    public void setUp() {
        getTestComponent().inject(ArtistListPresenterTest.this);
    }

    @Test
    public void testShowList() {
        Mockito.when(mDataManageMock.getArtistList(true)).thenReturn(Observable.just(mArtistList));

        ArtistListPresenter artistListPresenter = new ArtistListPresenter();

        artistListPresenter.bindView(mArtistListViewMock);
        artistListPresenter.onViewReady();

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
        Mockito.when(mDataManageMock.getArtistList(true)).thenReturn(Observable.just(Collections.emptyList()));

        ArtistListPresenter artistListPresenter = new ArtistListPresenter();

        artistListPresenter.bindView(mArtistListViewMock);
        artistListPresenter.onViewReady();

        Mockito.verify(mArtistListViewMock, Mockito.times(1)).showEmpty();
        Mockito.verify(mArtistListViewMock, Mockito.times(1)).setRefreshingConfig(true, false);

        Mockito.verify(mArtistListViewMock, Mockito.never()).showList(mArtistList);
        Mockito.verify(mArtistListViewMock, Mockito.never()).showNoInternet(false);
        Mockito.verify(mArtistListViewMock, Mockito.never()).showNoInternet(true);
        Mockito.verify(mArtistListViewMock, Mockito.never()).showNothingFound();
        Mockito.verify(mArtistListViewMock, Mockito.never()).showError();
    }

    @Test
    public void testShowError() {
        Mockito.when(mDataManageMock.getArtistList(true)).thenReturn(Observable.error(new RuntimeException()));

        ArtistListPresenter artistListPresenter = new ArtistListPresenter();

        artistListPresenter.bindView(mArtistListViewMock);
        artistListPresenter.onViewReady();

        Mockito.verify(mArtistListViewMock, Mockito.times(1)).showError();
        Mockito.verify(mArtistListViewMock, Mockito.times(1)).setRefreshingConfig(false, false);

        Mockito.verify(mArtistListViewMock, Mockito.never()).showList(mArtistList);
        Mockito.verify(mArtistListViewMock, Mockito.never()).showNoInternet(false);
        Mockito.verify(mArtistListViewMock, Mockito.never()).showNoInternet(true);
        Mockito.verify(mArtistListViewMock, Mockito.never()).showNothingFound();
        Mockito.verify(mArtistListViewMock, Mockito.never()).showEmpty();
    }

    @Test
    public void testShowNoInternet() {
        Mockito.when(mDataManageMock.getArtistList(true)).thenReturn(Observable.error(new DataManager.NoInternetException()));

        ArtistListPresenter artistListPresenter = new ArtistListPresenter();

        artistListPresenter.bindView(mArtistListViewMock);
        artistListPresenter.onViewReady();

        Mockito.verify(mArtistListViewMock, Mockito.times(1)).showNoInternet(false);
        Mockito.verify(mArtistListViewMock, Mockito.times(1)).setRefreshingConfig(false, false);

        Mockito.verify(mArtistListViewMock, Mockito.never()).showList(mArtistList);
        Mockito.verify(mArtistListViewMock, Mockito.never()).showError();
        Mockito.verify(mArtistListViewMock, Mockito.never()).showNoInternet(true);
        Mockito.verify(mArtistListViewMock, Mockito.never()).showNothingFound();
        Mockito.verify(mArtistListViewMock, Mockito.never()).showEmpty();
    }

    @Test
    public void testRefreshWithInternet() {
        Mockito.when(mDataManageMock.getArtistList(true)).thenReturn(Observable.just(mArtistList));
        Mockito.when(mDataManageMock.isInternetConnected()).thenReturn(true);

        ArtistListPresenter artistListPresenter = new ArtistListPresenter();

        artistListPresenter.bindView(mArtistListViewMock);
        artistListPresenter.onViewReady();
        artistListPresenter.onReload();

        Mockito.verify(mArtistListViewMock, Mockito.atLeast(2)).showList(mArtistList);

        Mockito.verify(mArtistListViewMock, Mockito.never()).showNoInternet(false);
        Mockito.verify(mArtistListViewMock, Mockito.never()).showNoInternet(true);
        Mockito.verify(mArtistListViewMock, Mockito.never()).showNothingFound();
        Mockito.verify(mArtistListViewMock, Mockito.never()).showEmpty();
        Mockito.verify(mArtistListViewMock, Mockito.never()).showError();
    }

    @Test
    public void testRefreshWithoutInternet() {
        Mockito.when(mDataManageMock.getArtistList(true)).thenReturn(Observable.just(mArtistList));
        Mockito.when(mDataManageMock.isInternetConnected()).thenReturn(false);

        ArtistListPresenter artistListPresenter = new ArtistListPresenter();
        artistListPresenter.bindView(mArtistListViewMock);
        artistListPresenter.onViewReady();

        artistListPresenter.onReload();

        Mockito.verify(mArtistListViewMock, Mockito.times(1)).showList(mArtistList);
        Mockito.verify(mArtistListViewMock, Mockito.times(1)).showNoInternet(true);

        Mockito.verify(mArtistListViewMock, Mockito.never()).showNoInternet(false);
        Mockito.verify(mArtistListViewMock, Mockito.never()).showNothingFound();
        Mockito.verify(mArtistListViewMock, Mockito.never()).showEmpty();
        Mockito.verify(mArtistListViewMock, Mockito.never()).showError();
    }

    @Test
    public void testOnArtistAtPositionClicked() {
        Mockito.when(mDataManageMock.getArtistList(true)).thenReturn(Observable.just(mArtistList));

        ArtistListPresenter artistListPresenter = new ArtistListPresenter();
        artistListPresenter.bindView(mArtistListViewMock);
        artistListPresenter.onViewReady();

        artistListPresenter.onArtistAtPositionClicked(1);

        Mockito.verify(mArtistListViewMock, Mockito.times(1)).moveToArtistDetails(2915);
    }

    @Test
    public void testOnSearchQueryChanged_ToveLo() {
        Mockito.when(mDataManageMock.getArtistList(true)).thenReturn(Observable.just(mArtistList));

        ArtistListPresenter artistListPresenter = new ArtistListPresenter();
        artistListPresenter.bindView(mArtistListViewMock);
        artistListPresenter.onViewReady();

        Mockito.reset(mArtistListViewMock);

        artistListPresenter.onSearchQueryChanged("ToVe"); // поиск не завист от регистра.

        // будет найден только исполнитель "Tove Lo".
        Mockito.verify(mArtistListViewMock, Mockito.times(1)).showList(Collections.singletonList(mArtistList.get(0)));
    }

    @Test
    public void testOnSearchQueryChanged_NeYo() {
        Mockito.when(mDataManageMock.getArtistList(true)).thenReturn(Observable.just(mArtistList));

        ArtistListPresenter artistListPresenter = new ArtistListPresenter();
        artistListPresenter.bindView(mArtistListViewMock);
        artistListPresenter.onViewReady();

        Mockito.reset(mArtistListViewMock);

        artistListPresenter.onSearchQueryChanged("yO"); // поиск не завист от регистра.

        // будет найден только исполнитель "Ne-Yo".
        Mockito.verify(mArtistListViewMock, Mockito.times(1)).showList(Collections.singletonList(mArtistList.get(1)));
    }

    @Test
    public void testOnSearchQueryChanged_All() {
        Mockito.when(mDataManageMock.getArtistList(true)).thenReturn(Observable.just(mArtistList));

        ArtistListPresenter artistListPresenter = new ArtistListPresenter();
        artistListPresenter.bindView(mArtistListViewMock);
        artistListPresenter.onViewReady();

        artistListPresenter.onSearchQueryChanged("o");

        // Будут найден все исполнители.
        // Метод будет вызван с этим аргументом 2 раза:
        // при загрузен первоначального списка и списка результатов поиска.
        Mockito.verify(mArtistListViewMock, Mockito.times(2)).showList(mArtistList);
    }

    @Test
    public void testOnSearchQueryChanged_Nothing() {
        Mockito.when(mDataManageMock.getArtistList(true)).thenReturn(Observable.just(mArtistList));

        ArtistListPresenter artistListPresenter = new ArtistListPresenter();
        artistListPresenter.bindView(mArtistListViewMock);
        artistListPresenter.onViewReady();

        artistListPresenter.onSearchQueryChanged("anth query");

        // Ничего не будет найдено.
        Mockito.verify(mArtistListViewMock, Mockito.times(1)).showNothingFound();
    }

}
