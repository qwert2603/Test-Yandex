package com.qwert2603.testyandex.model;

import com.qwert2603.testyandex.BuildConfig;
import com.qwert2603.testyandex.TestApplication;
import com.qwert2603.testyandex.TestConst;
import com.qwert2603.testyandex.TestUtils;
import com.qwert2603.testyandex.di.TestComponent;
import com.qwert2603.testyandex.model.entity.Artist;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Тест для {@link DataManager}.
 * Тестируется корректность методов:
 * {@link DataManager#getArtistList(boolean)},
 * {@link DataManager#getArtistById(int, boolean)}.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 19, application = TestApplication.class)
public class DataManagerTest {

    @Inject
    ArtistService mArtistService;

    private DataManager mDataManager;

    @Before
    public void setUp() throws Exception {
        TestComponent testComponent = (TestComponent) TestApplication.getAppComponent();
        testComponent.inject(DataManagerTest.this);

        mDataManager = DataManager.get();

        Observable<List<Artist>> observable = Observable.just(TestUtils.readJson(TestConst.ARTISTS_JSON, Artist[].class))
                .flatMap(Observable::from)
                .toList();

        Mockito.when(mArtistService.getArtistList()).thenReturn(observable);
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
        Mockito.verify(mArtistService, Mockito.times(1)).getArtistList();
    }

}
