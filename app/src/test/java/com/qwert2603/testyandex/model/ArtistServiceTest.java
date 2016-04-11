package com.qwert2603.testyandex.model;


import com.qwert2603.testyandex.BuildConfig;
import com.qwert2603.testyandex.TestApplication;
import com.qwert2603.testyandex.TestConst;
import com.qwert2603.testyandex.TestUtils;
import com.qwert2603.testyandex.di.TestComponent;
import com.qwert2603.testyandex.model.entity.Artist;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.mockwebserver.Dispatcher;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;
import com.squareup.okhttp.mockwebserver.RecordedRequest;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.Arrays;
import java.util.List;

import rx.Observable;

/**
 * Тесты для {@link ArtistService}.
 * Тестируется корректность метода {@link ArtistService#getArtistList()}.
 * Для имитации работы с сетью используется {@link MockWebServer}.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 19, application = TestApplication.class)
public class ArtistServiceTest {

    private MockWebServer mMockWebServer;

    private Observable<Artist> mArtistObservable;

    @Before
    public void setUp() throws Exception {
        TestComponent testComponent = (TestComponent) TestApplication.getAppComponent();
        testComponent.inject(ArtistServiceTest.this);

        mMockWebServer = new MockWebServer();
        mMockWebServer.start();
        mMockWebServer.setDispatcher(new Dispatcher() {
            @SuppressWarnings("ConstantConditions")
            @Override
            public MockResponse dispatch(RecordedRequest request) throws InterruptedException {
                return new MockResponse()
                        .setResponseCode(200)
                        .setBody(TestUtils.readString(TestConst.ARTISTS_JSON));
            }
        });

        HttpUrl baseUrl = mMockWebServer.url("/");
        ArtistService artistService = ArtistServiceHelper.get().getArtistService(baseUrl.toString());

        mArtistObservable = artistService.getArtistList().flatMap(Observable::from).cache();
    }

    @Test
    public void testAlbums() {
        Observable.zip(
                mArtistObservable.map(Artist::getAlbums),
                Observable.just(22, 152),
                Integer::equals
        ).subscribe(Assert::assertTrue);
    }

    @Test
    public void testGenres() {
        Observable.zip(
                mArtistObservable.map(Artist::getGenres),
                Observable.just(Arrays.asList("pop", "dance", "electronics"), Arrays.asList("rnb", "pop", "rap")),
                List::equals
        ).subscribe(Assert::assertTrue);
    }

    @After
    public void shutdownMockWebServer() throws Exception {
        mMockWebServer.shutdown();
    }

}
