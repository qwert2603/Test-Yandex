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

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 19, application = TestApplication.class)
public class ArtistListFragmentTest {

    @Inject
    ArtistListPresenter mArtistListPresenter;

    ArtistListActivity mArtistListActivity;
    ArtistListFragment mArtistListFragment;

    @Before
    public void setUp() {
        TestComponent testComponent = (TestComponent) TestApplication.getAppComponent();
        testComponent.inject(ArtistListFragmentTest.this);

        mArtistListActivity = Robolectric.setupActivity(ArtistListActivity.class);
        mArtistListFragment = (ArtistListFragment) mArtistListActivity.getFragmentManager()
                .findFragmentById(R.id.fragment_container);
        mArtistListFragment.setAppComponent(testComponent);
    }

    @Test
    public void testEqualPresenters() {
        assertEquals(mArtistListFragment.getPresenter(), mArtistListPresenter);
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
