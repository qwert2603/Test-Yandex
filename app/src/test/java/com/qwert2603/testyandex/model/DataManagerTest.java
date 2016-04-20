package com.qwert2603.testyandex.model;

import com.qwert2603.testyandex.BaseTest;
import com.qwert2603.testyandex.model.entity.Artist;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Тест для {@link DataManager}.
 * Тестируется корректность методов:
 * {@link DataManager#getArtistList(boolean)},
 * {@link DataManager#getArtistById(int, boolean)}.
 */
public class DataManagerTest extends BaseTest {

    @Inject
    ArtistService mArtistServiceMock;

    @Inject
    List<Artist> mArtistList;

    private DataManager mDataManager;

    @Before
    public void setUp() throws Exception {
        getTestComponent().inject(DataManagerTest.this);

        mDataManager = new DataManager();

        Observable<List<Artist>> observable = Observable.just(mArtistList);
        Mockito.when(mArtistServiceMock.getArtistList()).thenReturn(observable);
    }

    @Test
    public void testGetArtistList() {
        Observable.zip(
                mDataManager.getArtistList(true)
                        .flatMap(Observable::from)
                        .map(Artist::getAlbums),
                Observable.just(22, 152),
                Integer::equals
        ).subscribe(Assert::assertTrue);

        Observable.zip(
                mDataManager.getArtistList(false)
                        .flatMap(Observable::from)
                        .map(Artist::getAlbums),
                Observable.just(22, 152),
                Integer::equals
        ).subscribe(Assert::assertTrue);
    }

    @Test
    public void testGetArtistById() {
        Observable.zip(
                mDataManager.getArtistById(1080505, true)
                        .map(Artist::getTracks),
                Observable.just(81),
                Integer::equals
        ).subscribe(Assert::assertTrue);

        Observable.zip(
                mDataManager.getArtistById(2915, false)
                        .map(Artist::getTracks),
                Observable.just(256),
                Integer::equals
        ).subscribe(Assert::assertTrue);

        // DataManager#.getArtistList() должен быть вызван только 1 раз, несмотря на 2 вызова #getArtistById()
        // так как в DataManager реализовано кеширование.
        Mockito.verify(mArtistServiceMock, Mockito.times(1)).getArtistList();
    }

}
