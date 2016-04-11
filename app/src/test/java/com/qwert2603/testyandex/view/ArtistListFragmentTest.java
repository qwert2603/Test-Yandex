package com.qwert2603.testyandex.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.qwert2603.testyandex.BuildConfig;
import com.qwert2603.testyandex.R;
import com.qwert2603.testyandex.TestApplication;
import com.qwert2603.testyandex.artist_list.ArtistListActivity;
import com.qwert2603.testyandex.artist_list.ArtistListFragment;
import com.qwert2603.testyandex.artist_list.ArtistListPresenter;

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
        TestApplication.getTestComponent().inject(ArtistListFragmentTest.this);

        mArtistListActivity = Robolectric.setupActivity(ArtistListActivity.class);
        mArtistListFragment = (ArtistListFragment) mArtistListActivity.getFragmentManager()
                .findFragmentById(R.id.fragment_container);
        mArtistListFragment.setAppComponent(TestApplication.getTestComponent());

        mArtistListFragment.onCreate(null);

        assertEquals(mArtistListFragment.getPresenter(), mArtistListPresenter);
    }

    @Test
    public void testBindView() {
        mArtistListFragment.onCreateView(LayoutInflater.from(mArtistListActivity),
                (ViewGroup) mArtistListActivity.findViewById(R.id.fragment_container), null);
        verify(mArtistListPresenter).bindView(mArtistListFragment);
    }

    @Test
    public void testOnViewReady() {
        mArtistListFragment.onResume();
        verify(mArtistListPresenter).onViewReady();
        verify(mArtistListPresenter).onUpdateView(mArtistListFragment);
    }


}
