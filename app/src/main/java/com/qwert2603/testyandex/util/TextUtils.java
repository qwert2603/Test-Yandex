package com.qwert2603.testyandex.util;


import android.content.Context;

import com.qwert2603.testyandex.R;

import java.util.List;
import java.util.Locale;

/**
 * Утилиты для получения текстового представления данных.
 */
public class TextUtils {

    /**
     * Получить строку, описывающую кол-во альбомов и треков.
     * @param context Context для плучения ресурсов.
     * @param tracks кол-во треков.
     * @param albums кол-во альбомов.
     * @return строка, описывающая кол-во альбомов и треков.
     */
    public static String getTracksAndAlbumsString(Context context, int tracks, int albums) {
        String t = context.getResources().getQuantityString(R.plurals.tracks, tracks);
        String a = context.getResources().getQuantityString(R.plurals.albums, albums);
        return String.format(Locale.ROOT, "%d %s, %d %s", tracks, t, albums, a);
    }

    /**
     * Получить строку, описывающую жанры.
     * @param genresList список жанров.
     * @return строка, описывающая жанры.
     */
    public static String getGenresString(List<String> genresList) {
        String string = genresList.toString();
        return string.substring(1, string.length() - 1);
    }

}
