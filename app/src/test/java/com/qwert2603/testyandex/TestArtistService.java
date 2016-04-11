package com.qwert2603.testyandex;

import com.qwert2603.testyandex.model.ArtistService;
import com.qwert2603.testyandex.model.entity.Artist;

import java.util.List;

import rx.Observable;

public class TestArtistService implements ArtistService {
    @Override
    public Observable<List<Artist>> getArtistList() {
        TestUtils.log("W_19");
        return Observable.just(TestUtils.readJson(TestConst.ARTISTS_JSON, Artist[].class))
                .flatMap(Observable::from)
                .toList();
    }
}
