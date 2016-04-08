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

        private ImageView mCover;
        private TextView mName;
        private TextView mGenres;
        private TextView mTracksAndAlbums;

        public ArtistDetailsViewHolder(View itemView) {
            super(itemView);
            mCover = (ImageView) itemView.findViewById(R.id.cover);
            mName = (TextView) itemView.findViewById(R.id.name);
            mGenres = (TextView) itemView.findViewById(R.id.genres);
            mTracksAndAlbums = (TextView) itemView.findViewById(R.id.tracks_and_albums);
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
        public void showTracksAndAlbums(String tracksAndAlbums) {
            mTracksAndAlbums.setText(tracksAndAlbums);
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
    }
}
