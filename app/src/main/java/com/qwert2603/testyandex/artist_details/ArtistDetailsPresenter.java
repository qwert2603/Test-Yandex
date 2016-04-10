package com.qwert2603.testyandex.artist_details;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.view.View;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.qwert2603.testyandex.TestYandexApplication;
import com.qwert2603.testyandex.base.BasePresenter;
import com.qwert2603.testyandex.model.entity.Artist;
import com.qwert2603.testyandex.model.DataManager;
import com.qwert2603.testyandex.util.LogUtils;

import javax.inject.Inject;

import rx.Subscription;

/**
 * Презентер для управления представлением с подробностями об исполнителе для шаблона MVP.
 */
public class ArtistDetailsPresenter extends BasePresenter<Artist, ArtistDetailsView> {

    @Inject
    DataManager mDataManager;

    /**
     * Тип изображения.
     */
    public enum CoverType {
        SMALL,
        BIG
    }

    /**
     * Подписка на получение подробностей об исполнителе.
     */
    private Subscription mSubscription;

    /**
     * Тип изображения, которое надо будет отображать в представлении.
     */
    private CoverType mCoverType;

    public ArtistDetailsPresenter() {
        TestYandexApplication.getAppComponent().inject(ArtistDetailsPresenter.this);
    }

    /**
     * Назначить id исполнителя, для которого рпедназначается этот презентер.
     *
     * @param id id исполнителя.
     */
    public void setArtistId(int id) {
        setModel(null);
        mSubscription = mDataManager
                .getArtistById(id, false)
                .subscribe(
                        model -> ArtistDetailsPresenter.this.setModel(model),
                        throwable -> {
                            if (mSubscription != null) {
                                // в случае ошибки отписывается от подписки на получение списка исполнителей.
                                mSubscription.unsubscribe();
                                mSubscription = null;
                            }
                            LogUtils.e(throwable);
                        }
                );
    }

    /**
     * Назначить объект исполнителя, для которого рпедназначается этот презентер.
     *
     * @param artist объект исполнителя.
     */
    public void setArtist(Artist artist) {
        setModel(artist);
    }

    /**
     * Назначить тип изображения, которое передает во View этот презентер.
     *
     * @param coverType тип изображения для отображения.
     */
    public void setCoverType(CoverType coverType) {
        mCoverType = coverType;
    }

    @Override
    protected void onUpdateView(@NonNull ArtistDetailsView view) {
        Artist artist = getModel();
        if (artist == null) {
            // исполнитель еще загружается.
            view.showLoading();
            view.setFabVisibility(false);
            return;
        }

        view.showCover(null);
        ImageLoader.getInstance().loadImage(getCoverUrl(), new ImageLoaderCompletedListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                ArtistDetailsView artistDetailsView = getView();
                if (artistDetailsView != null) {
                    artistDetailsView.showCover(loadedImage);
                }
            }
        });

        view.showName(artist.getName());
        String genresList = artist.getGenres().toString();
        view.showGenres(genresList.substring(1, genresList.length() - 1));
        view.showTracksAndAlbums(artist.getAlbums(), artist.getTracks());
        view.showDescription(artist.getDescription());
        view.setFabVisibility(artist.getLink() != null);
    }

    @Override
    public void unbindView() {
        if (mSubscription != null) {
            // когда представление отвязывается, больше не потребуется подписка на получение объекта исполнителя.
            mSubscription.unsubscribe();
        }
        super.unbindView();
    }

    /**
     * Действие по случаю нажатия на плавающую кнопку.
     */
    public void onFabClicked() {
        if (getModel() != null) {
            getView().moveOnAddress(getModel().getLink());
        }
    }

    /**
     * @return ссылка на изображение-обложку данного исполнителя в зависимости от {@link #mCoverType}.
     */
    private String getCoverUrl() {
        Artist artist = getModel();
        if (artist == null) {
            return "";
        }
        switch (mCoverType) {
            case SMALL:
                return artist.getCover().getSmall();
            case BIG:
                return artist.getCover().getBig();
            default:
                return "";
        }
    }

    private static abstract class ImageLoaderCompletedListener implements ImageLoadingListener {
        @Override
        public void onLoadingStarted(String imageUri, View view) {
        }

        @Override
        public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
        }

        @Override
        public void onLoadingCancelled(String imageUri, View view) {
        }
    }
}
