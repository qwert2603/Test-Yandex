package com.qwert2603.testyandex.artist_list;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qwert2603.testyandex.R;
import com.qwert2603.testyandex.artist_details.ArtistDetailsPresenter;
import com.qwert2603.testyandex.artist_details.ArtistDetailsView;
import com.qwert2603.testyandex.base.BaseRecyclerViewAdapter;
import com.qwert2603.testyandex.model.entity.Artist;

import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Адаптер для отображения списка исполнителей в {@link RecyclerView} для шаблона MVP.
 */
public class ArtistListAdapter
        extends BaseRecyclerViewAdapter<Artist, ArtistListAdapter.ArtistDetailsViewHolder, ArtistDetailsPresenter> {

    public ArtistListAdapter(List<Artist> modelList) {
        super(modelList);
    }

    @Override
    protected ArtistDetailsPresenter createPresenter(Artist artist) {
        // в элементе списка отображается маленькая версия изображения-обложки исполнителя.
        return new ArtistDetailsPresenter(artist, ArtistDetailsPresenter.CoverType.SMALL);
    }

    @Override
    public ArtistDetailsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_artist, parent, false);
        return new ArtistDetailsViewHolder(view);
    }

    /**
     * Класс ViewHolder, отвечающий за отображение данных об отдельном исполнителе.
     */
    public class ArtistDetailsViewHolder
            extends BaseRecyclerViewAdapter<?, ?, ArtistDetailsPresenter>.RecyclerViewHolder
            implements ArtistDetailsView {

        @Bind(R.id.cover)
        ImageView mCover;

        @Bind(R.id.name)
        TextView mName;

        @Bind(R.id.genres)
        TextView mGenres;

        @Bind(R.id.tracks_and_albums)
        TextView mTracksAndAlbums;

        public ArtistDetailsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(ArtistDetailsViewHolder.this, itemView);
        }

        @Override
        public void showCover(Bitmap cover) {
            mCover.setImageBitmap(cover);
        }

        @Override
        public void showName(String name) {
            mName.setText(name);
        }

        @Override
        public void showGenres(String genres) {
            mGenres.setText(genres);
        }

        @Override
        public void showTracksAndAlbums(int tracks, int albums) {
            String t = mItemView.getResources().getQuantityString(R.plurals.tracks, tracks);
            String a = mItemView.getResources().getQuantityString(R.plurals.albums, albums);
            mTracksAndAlbums.setText(String.format(Locale.ROOT, "%d %s, %d %s", tracks, t, albums, a));
        }

        @Override
        public void showDescription(String description) {
            // в элементе списка не нужно отображать подробное описание исполнителя.
        }

        @Override
        public void showLoading() {
            mName.setText(R.string.loading);
            mGenres.setText(R.string.loading);
            mTracksAndAlbums.setText(R.string.loading);
        }

        @Override
        public void moveOnAddress(String url) {
            // из элемента списка нельзя переходить по адресу связанному с исплнителем.
        }

        @Override
        public void setFabVisibility(boolean visibility) {
            // у элемента списка нет кнопки, и поэтому не надо настраивать ее видимость.
        }
    }
}
