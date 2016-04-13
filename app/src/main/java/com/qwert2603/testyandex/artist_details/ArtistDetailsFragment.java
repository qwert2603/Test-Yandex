package com.qwert2603.testyandex.artist_details;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qwert2603.testyandex.R;
import com.qwert2603.testyandex.TestYandexApplication;
import com.qwert2603.testyandex.base.BaseActivity;
import com.qwert2603.testyandex.base.BaseFragment;
import com.qwert2603.testyandex.util.TextUtils;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Фрагмент, отображающий поднобности об исполнителе.
 */
public class ArtistDetailsFragment extends BaseFragment<ArtistDetailsPresenter> implements ArtistDetailsView {

    private static final String artistIdKey = "artistId";

    public static ArtistDetailsFragment newInstance(int artistId) {
        ArtistDetailsFragment fragment = new ArtistDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(artistIdKey, artistId);
        fragment.setArguments(args);
        return fragment;
    }

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.cover)
    ImageView mCover;

    @Bind(R.id.genres)
    TextView mGenres;

    @Bind(R.id.tracks_and_albums)
    TextView mTracksAndAlbums;

    @Bind(R.id.description)
    TextView mDescription;

    @Bind(R.id.fab)
    FloatingActionButton mFab;

    @Inject
    ArtistDetailsPresenter mArtistDetailsPresenter;

    @Override
    protected ArtistDetailsPresenter getPresenter() {
        // в фрагменте с подробностями об исполнителе отображается большая версия изображения-обложки.
        //return new ArtistDetailsPresenter(getArguments().getInt(artistIdKey), ArtistDetailsPresenter.CoverType.BIG);
        return mArtistDetailsPresenter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        TestYandexApplication.getAppComponent().inject(ArtistDetailsFragment.this);

        mArtistDetailsPresenter.setArtistId(getArguments().getInt(artistIdKey));

        // в фрагменте с подробностями об исполнителе отображается большая версия изображения-обложки.
        mArtistDetailsPresenter.setCoverType(ArtistDetailsPresenter.CoverType.BIG);

        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_artist_details, container, false);

        ButterKnife.bind(ArtistDetailsFragment.this, view);

        ((BaseActivity) getActivity()).setSupportActionBar(mToolbar);
        ActionBar supportActionBar = ((BaseActivity) getActivity()).getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }

        mFab.setOnClickListener(v -> mArtistDetailsPresenter.onFabClicked());

        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public ImageView getCoverImageView() {
        return mCover;
    }

    @Override
    public void showName(String name) {
        ActionBar supportActionBar = ((BaseActivity) getActivity()).getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setTitle(name);
        }
    }

    @Override
    public void showGenres(String genres) {
        mGenres.setText(genres);
    }

    @Override
    public void showTracksAndAlbums(int tracks, int albums) {
        mTracksAndAlbums.setText(TextUtils.getTracksAndAlbumsString(getActivity(), tracks, albums));
    }

    @Override
    public void showDescription(String description) {
        mDescription.setText(description);
    }

    @Override
    public void showLoading() {
        mGenres.setText(R.string.loading);
        mTracksAndAlbums.setText(R.string.loading);
        mDescription.setText(R.string.loading);
    }

    @Override
    public void moveToAddress(String url) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }

    @Override
    public void setFabVisibility(boolean visibility) {
        mFab.setVisibility(visibility ? View.VISIBLE : View.INVISIBLE);
    }
}
