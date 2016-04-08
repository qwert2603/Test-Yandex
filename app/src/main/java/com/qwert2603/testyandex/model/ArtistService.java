package com.qwert2603.testyandex.model;

import com.qwert2603.testyandex.model.entity.Artist;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Сервис для получения списка исполнителей.
 */
public interface ArtistService {

    /**
     * @return Observable списка исполнителей.
     */
    @GET("artists.json")
    Observable<List<Artist>> getArtistList();

}
