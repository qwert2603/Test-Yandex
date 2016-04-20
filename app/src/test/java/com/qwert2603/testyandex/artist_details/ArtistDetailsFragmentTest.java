package com.qwert2603.testyandex.artist_details;

import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.qwert2603.testyandex.BaseTest;
import com.qwert2603.testyandex.R;
import com.qwert2603.testyandex.model.entity.Artist;
import com.qwert2603.testyandex.util.TextUtils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.Shadows;

import java.util.List;

import javax.inject.Inject;

/**
 * Тесты для {@link ArtistDetailsFragment}.
 * Тестируется корректность методов:
 * {@link ArtistDetailsFragment#showName(String)}
 * {@link ArtistDetailsFragment#showGenres(String)}
 * {@link ArtistDetailsFragment#showTracksAndAlbums(int, int)}
 * {@link ArtistDetailsFragment#showDescription(String)}
 * {@link ArtistDetailsFragment#getCoverImageView()}
 * {@link ArtistDetailsFragment#setFabVisibility(boolean)}
 * {@link ArtistDetailsFragment#moveToAddress(String)}
 * {@link ArtistDetailsFragment#showLoading()}
 * а также {@link android.view.View.OnClickListener} у {@link ArtistDetailsFragment#mFab}
 */
public class ArtistDetailsFragmentTest extends BaseTest {

    private ArtistDetailsActivity mArtistDetailsActivity;
    private ArtistDetailsFragment mArtistDetailsFragment;

    private Artist mArtist;

    @Inject
    ArtistDetailsPresenter mArtistDetailsPresenter;

    @Inject
    List<Artist> mArtistList;

    @Before
    public void setUp() {
        getTestComponent().inject(ArtistDetailsFragmentTest.this);

        mArtistDetailsActivity = Robolectric.setupActivity(ArtistDetailsActivity.class);
        mArtistDetailsFragment = (ArtistDetailsFragment) mArtistDetailsActivity.getFragmentManager()
                .findFragmentById(R.id.fragment_container);

        mArtist = mArtistList.get(0);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void testShowName() {
        mArtistDetailsFragment.showName(mArtist.getName());
        Assert.assertEquals(mArtistDetailsActivity.getSupportActionBar().getTitle(), mArtist.getName());
    }

    @Test
    public void testShowTracksAndAlbums() {
        mArtistDetailsFragment.showTracksAndAlbums(mArtist.getTracks(), mArtist.getAlbums());
        String string = TextUtils.getTracksAndAlbumsString(mArtistDetailsActivity, mArtist.getTracks(), mArtist.getAlbums());
        Assert.assertEquals(mArtistDetailsFragment.mTracksAndAlbums.getText(), string);
    }

    @Test
    public void testShowDescription() {
        mArtistDetailsFragment.showDescription(mArtist.getDescription());
        Assert.assertEquals(mArtistDetailsFragment.mDescription.getText(), mArtist.getDescription());
    }

    @Test
    public void testShowGenres() {
        String genresString = TextUtils.getGenresString(mArtist.getGenres());
        mArtistDetailsFragment.showGenres(genresString);
        Assert.assertEquals(mArtistDetailsFragment.mGenres.getText(), genresString);
    }

    @Test
    public void testGetCoverImageView() {
        Assert.assertEquals(mArtistDetailsFragment.getCoverImageView(), mArtistDetailsFragment.mCover);
    }

    @Test
    public void testShowLoading() {
        String string = mArtistDetailsActivity.getString(R.string.loading);
        mArtistDetailsFragment.showLoading();
        Assert.assertEquals(mArtistDetailsFragment.mGenres.getText(), string);
        Assert.assertEquals(mArtistDetailsFragment.mTracksAndAlbums.getText(), string);
        Assert.assertEquals(mArtistDetailsFragment.mDescription.getText(), string);
    }

    @Test
    public void testSetFabVisibility() {
        mArtistDetailsFragment.setFabVisibility(true);
        Assert.assertEquals(mArtistDetailsFragment.mFab.getVisibility(), View.VISIBLE);

        mArtistDetailsFragment.setFabVisibility(false);
        Assert.assertEquals(mArtistDetailsFragment.mFab.getVisibility(), View.INVISIBLE);
    }

    @Test
    public void testMoveToAddress() {
        mArtistDetailsFragment.moveToAddress(mArtist.getLink());

        Intent expectedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mArtist.getLink()));
        Intent actualIntent = Shadows.shadowOf(mArtistDetailsActivity).getNextStartedActivity();

        Assert.assertEquals(expectedIntent, actualIntent);
    }

    @Test
    public void testOnFabClicked() {
        mArtistDetailsFragment.mFab.performClick();
        Mockito.verify(mArtistDetailsPresenter, Mockito.times(1)).onFabClicked();
    }

}
