package com.qwert2603.testyandex.artist_list;

import com.qwert2603.testyandex.base.ListView;
import com.qwert2603.testyandex.model.entity.Artist;

/**
 * Представления для списка исполнителей для шаблона MVP.
 */
public interface ArtistListView extends ListView<Artist> {

    /**
     * Отобразить сообщение об отсутствии соединения с интернетом.
     *
     * @param snackBar показать в виде Snackbar'а.
     */
    void showNoInternet(boolean snackBar);

    /**
     * Уствновить настройки обновления (RefreshLayout).
     *
     * @param enable     активировать/деактивировать RefreshLayout
     * @param refreshing отобразить происходит ли обновление в текущий момент.
     */
    void setRefreshingConfig(boolean enable, boolean refreshing);

    /**
     * Перейти к подробной информации об исполнителе.
     *
     * @param artistId id исполнителя.
     */
    void moveToArtistDetails(int artistId);
}
