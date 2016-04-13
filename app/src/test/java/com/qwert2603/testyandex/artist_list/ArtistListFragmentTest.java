package com.qwert2603.testyandex.artist_list;

import android.content.Intent;

import com.qwert2603.testyandex.BaseTest;
import com.qwert2603.testyandex.R;
import com.qwert2603.testyandex.TestUtils;
import com.qwert2603.testyandex.artist_details.ArtistDetailsActivity;
import com.qwert2603.testyandex.model.entity.Artist;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.robolectric.Robolectric;
import org.robolectric.Shadows;

import java.util.List;

/**
 * Тесты для {@link ArtistListFragment}.
 * Тестируется корректность методов:
 * {@link ArtistListFragment#showList(List)}
 * {@link ArtistListFragment#showEmpty()}
 * {@link ArtistListFragment#showError()}
 * {@link ArtistListFragment#showLoading()}
 * {@link ArtistListFragment#showNoInternet(boolean)}
 * {@link ArtistListFragment#showNothingFound()}
 * {@link ArtistListFragment#setRefreshingConfig(boolean, boolean)}
 * {@link ArtistListFragment#moveToArtistDetails(int)}
 */
public class ArtistListFragmentTest extends BaseTest {

    private ArtistListActivity mArtistListActivity;
    private ArtistListFragment mArtistListFragment;

    @Before
    public void setUp() {
        mArtistListActivity = Robolectric.setupActivity(ArtistListActivity.class);
        mArtistListFragment = (ArtistListFragment) mArtistListActivity.getFragmentManager()
                .findFragmentById(R.id.fragment_container);
    }

    @Test
    public void testShowList() {
        List<Artist> artistList = TestUtils.getTestArtistList();

        mArtistListFragment.showList(artistList);
        Assert.assertEquals(getViewAnimatorDisplayedChild(), ArtistListFragment.POSITION_REFRESH_LAYOUT);

        ArtistListAdapter artistListAdapter = (ArtistListAdapter) mArtistListFragment.mRecyclerView.getAdapter();
        Assert.assertTrue(artistListAdapter.isShowingList(artistList));
    }

    @Test
    public void testShowEmpty() {
        mArtistListFragment.showEmpty();
        Assert.assertEquals(getViewAnimatorDisplayedChild(), ArtistListFragment.POSITION_EMPTY_TEXT_VIEW);
    }

    @Test
    public void testShowError() {
        mArtistListFragment.showError();
        Assert.assertEquals(getViewAnimatorDisplayedChild(), ArtistListFragment.POSITION_ERROR_TEXT_VIEW);
    }

    @Test
    public void testShowLoading() {
        mArtistListFragment.showLoading();
        Assert.assertEquals(getViewAnimatorDisplayedChild(), ArtistListFragment.POSITION_LOADING_TEXT_VIEW);
    }

    @Test
    public void testShowNoInternetTextView() {
        mArtistListFragment.showNoInternet(false);
        Assert.assertEquals(getViewAnimatorDisplayedChild(), ArtistListFragment.POSITION_NO_INTERNET_TEXT_VIEW);
    }

    @Test
    public void testShowNothingFound() {
        mArtistListFragment.showNothingFound();
        Assert.assertEquals(getViewAnimatorDisplayedChild(), ArtistListFragment.POSITION_NOTHING_FOUND);
    }

    @Test
    public void testSetRefreshingConfig() {
        mArtistListFragment.setRefreshingConfig(true, true);
        Assert.assertTrue(mArtistListFragment.mRefreshLayout.isEnabled());
        Assert.assertTrue(mArtistListFragment.mRefreshLayout.isRefreshing());

        mArtistListFragment.setRefreshingConfig(false, false);
        Assert.assertTrue(! mArtistListFragment.mRefreshLayout.isEnabled());
        Assert.assertTrue(! mArtistListFragment.mRefreshLayout.isRefreshing());
    }

    @Test
    public void testMoveToArtistDetails() {
        mArtistListFragment.moveToArtistDetails(2915);

        Intent expectedIntent = new Intent(mArtistListActivity, ArtistDetailsActivity.class);
        expectedIntent.putExtra(ArtistDetailsActivity.EXTRA_ARTIST_ID, 2915);
        Intent actualIntent = Shadows.shadowOf(mArtistListActivity).getNextStartedActivity();

        Assert.assertEquals(expectedIntent, actualIntent);
    }

    private int getViewAnimatorDisplayedChild() {
        return mArtistListFragment.mViewAnimator.getDisplayedChild();
    }

}
