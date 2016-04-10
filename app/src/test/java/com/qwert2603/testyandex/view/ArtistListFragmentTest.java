package com.qwert2603.testyandex.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.qwert2603.testyandex.R;
import com.qwert2603.testyandex.TestApplication;
import com.qwert2603.testyandex.artist_list.ArtistListActivity;
import com.qwert2603.testyandex.artist_list.ArtistListFragment;
import com.qwert2603.testyandex.artist_list.ArtistListPresenter;
import com.qwert2603.testyandex.di.TestComponent;

import org.junit.Before;
import org.junit.Test;
import org.robolectric.Robolectric;

import javax.inject.Inject;

import static org.mockito.Mockito.verify;

public class ArtistListFragmentTest {

    @Inject
    ArtistListPresenter mArtistListPresenter;

    ArtistListActivity mArtistListActivity;
    ArtistListFragment mArtistListFragment;

    @Before
    void setUp() {
        ((TestComponent) TestApplication.getAppComponent()).inject(ArtistListFragmentTest.this);

        mArtistListActivity = Robolectric.setupActivity(ArtistListActivity.class);
        mArtistListFragment = ArtistListFragment.newInstance();

        mArtistListFragment.onCreate(null);
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
