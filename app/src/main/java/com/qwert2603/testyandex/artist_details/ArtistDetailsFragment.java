package com.qwert2603.testyandex.artist_details;

import android.content.Intent;
import android.graphics.Bitmap;
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
import com.qwert2603.testyandex.base.BaseActivity;
import com.qwert2603.testyandex.base.BaseFragment;

import java.util.Locale;

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

    private ImageView mCover;
    private TextView mGenres;
    private TextView mTracksAndAlbums;
    private TextView mDescription;
    private FloatingActionButton mFab;

    @Override
    protected ArtistDetailsPresenter createPresenter() {
        // в фрагменте с подробностями об исполнителе отображается большая версия изображения-обложки.
        return new ArtistDetailsPresenter(getArguments().getInt(artistIdKey), ArtistDetailsPresenter.CoverType.BIG);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_artist_details, container, false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((BaseActivity) getActivity()).setSupportActionBar(toolbar);
        ActionBar supportActionBar = ((BaseActivity) getActivity()).getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }

        mCover = (ImageView) view.findViewById(R.id.cover);
        mGenres = (TextView) view.findViewById(R.id.genres);
        mTracksAndAlbums = (TextView) view.findViewById(R.id.tracks_and_albums);
        mDescription = (TextView) view.findViewById(R.id.description);

        mFab = (FloatingActionButton) view.findViewById(R.id.fab);
        mFab.setOnClickListener(v -> getPresenter().onFabClicked());

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
    public void showCover(Bitmap cover) {
        mCover.setImageBitmap(cover);
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
        String t = getResources().getQuantityString(R.plurals.tracks, tracks);
        String a = getResources().getQuantityString(R.plurals.albums, albums);
        mTracksAndAlbums.setText(String.format(Locale.ROOT, "%d %s, %d %s", tracks, t, albums, a));
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
    public void moveOnAddress(String url) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }

    @Override
    public void setFabVisibility(boolean visibility) {
        mFab.setVisibility(visibility ? View.VISIBLE : View.INVISIBLE);
    }
}
