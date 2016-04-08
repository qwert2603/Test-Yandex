package com.qwert2603.testyandex.artist_details;

import android.graphics.Bitmap;

import com.qwert2603.testyandex.base.BaseView;

/**
 * Представления для подробностей об исполнителе для шаблона MVP.
 */
public interface ArtistDetailsView extends BaseView {

    /**
     * Отобразить изображение/обложку.
     *
     * @param cover изображение/обложка.
     */
    void showCover(Bitmap cover);

    /**
     * Отобразить название.
     *
     * @param name название.
     */
    void showName(String name);

    /**
     * Отобразить информацию о жанрах.
     *
     * @param genres информация о жанрах.
     */
    void showGenres(String genres);

    /**
     * Отобразить информацию о количестве песен и альбомов.
     *
     * @param tracksAndAlbums информация о количестве песен и альбомов.
     */
    void showTracksAndAlbums(String tracksAndAlbums);

    /**
     * Отобразить описание.
     *
     * @param description описание
     */
    void showDescription(String description);

    /**
     * Отобразить сообщение о загрузке.
     */
    void showLoading();

    /**
     * Перейти по адресу.
     * @param url адрес
     */
    void moveOnAddress(String url);
}
