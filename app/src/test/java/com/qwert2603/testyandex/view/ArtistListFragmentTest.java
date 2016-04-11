package com.qwert2603.testyandex.view;

import com.qwert2603.testyandex.BuildConfig;
import com.qwert2603.testyandex.R;
import com.qwert2603.testyandex.TestApplication;
import com.qwert2603.testyandex.artist_list.ArtistListActivity;
import com.qwert2603.testyandex.artist_list.ArtistListFragment;
import com.qwert2603.testyandex.artist_list.ArtistListPresenter;
import com.qwert2603.testyandex.di.TestComponent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import javax.inject.Inject;

import static org.mockito.Mockito.verify;

/**
 * Тесты для {@link ArtistListFragment}.
 * Тестируется корректность привязки/отвязки презентера,
 * а также корректность вызовов у презентера:
 * {@link ArtistListPresenter#onViewReady()},
 * {@link ArtistListPresenter#onViewNotReady()}.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 19, application = TestApplication.class)
public class ArtistListFragmentTest {

    @Inject
    ArtistListPresenter mArtistListPresenter;

    private ArtistListFragment mArtistListFragment;

    @Before
    public void setUp() {
        TestComponent testComponent = (TestComponent) TestApplication.getAppComponent();
        testComponent.inject(ArtistListFragmentTest.this);

        ArtistListActivity artistListActivity = Robolectric.setupActivity(ArtistListActivity.class);
        mArtistListFragment = (ArtistListFragment) artistListActivity.getFragmentManager()
                .findFragmentById(R.id.fragment_container);
        mArtistListFragment.setAppComponent(testComponent);
    }

    @Test
    public void testBindView() {
        verify(mArtistListPresenter).bindView(mArtistListFragment);
        verify(mArtistListPresenter).onViewReady();
    }

    @Test
    public void testViewNotReady() {
        mArtistListFragment.onDestroy();
        verify(mArtistListPresenter).unbindView();
    }

    @Test
    public void testUnbindView() {
        mArtistListFragment.onPause();
        verify(mArtistListPresenter).onViewNotReady();
    }

}
