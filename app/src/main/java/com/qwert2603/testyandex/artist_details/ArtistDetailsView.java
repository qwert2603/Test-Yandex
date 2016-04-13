package com.qwert2603.testyandex.artist_details;

import android.widget.ImageView;

import com.qwert2603.testyandex.base.BaseView;

/**
 * Представления для подробностей об исполнителе для шаблона MVP.
 */
public interface ArtistDetailsView extends BaseView {

    /**
     * Получить ImageView, отобрадающий обложку.
     * <p>
     * Конечно, этот метод нарушает структуру шаблона MVP, передавая элемент предствления в презентер.
     * Но на это есть причина.
     * В этом случае презентер может эффективно и корректно отображать
     * изображения в ImageView с помощью библиотеки com.nostra13.universalimageloader,
     * которая позволяет отображать и заменять Bitmap'ы в ImageView.
     * В этом случае вся работа по отображению изображений ложится на эту библиотеку.
     *
     * @return ImageView, отобрадающий обложку.
     */
    ImageView getCoverImageView();

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
     * @param tracks кол-во песен.
     * @param albums кол-во альбомов.
     */
    void showTracksAndAlbums(int tracks, int albums);

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
     *
     * @param url адрес
     */
    void moveToAddress(String url);

    /**
     * Настроить видимость плавающей кнопки.
     *
     * @param visibility видимость.
     */
    void setFabVisibility(boolean visibility);
}
