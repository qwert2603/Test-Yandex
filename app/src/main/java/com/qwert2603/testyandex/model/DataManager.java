package com.qwert2603.testyandex.model;

import android.content.Context;

import com.qwert2603.testyandex.Const;
import com.qwert2603.testyandex.TestYandexApplication;
import com.qwert2603.testyandex.model.entity.Artist;
import com.qwert2603.testyandex.util.InternetUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Scheduler;

/**
 * Главный класс для работы с данными.
 * Для получения объекта класса DataManager используется {@link #get()}.
 * Класс позволяет загружать список исполнителей и кешировать его в оперативной памяти.
 */
public final class DataManager {

    private static DataManager sDataManager;

    private DataManager() {
        TestYandexApplication.getAppComponent().inject(DataManager.this);
    }

    /**
     * @return объект-синглтон класса DataManager.
     */
    public static DataManager get() {
            if (sDataManager == null) {
                synchronized (DataManager.class) {
                    if (sDataManager == null) {
                        sDataManager = new DataManager();
                    }
                }
        }
        return sDataManager;
    }

    @Inject
    Context mAppContext;

    @Inject
    ArtistService mArtistService;

    @Inject
    @Named(Const.UI_THREAD)
    Scheduler mUiScheduler;

    @Inject
    @Named(Const.IO_THREAD)
    Scheduler mIoScheduler;

    /**
     * Кешированная версия списка исполнителей.
     */
    private volatile List<Artist> mArtistList = null;

    /**
     * Карта <"id исполнителя", "исполнитель"> для быстрого поиска исполнителя по его id.
     */
    private volatile Map<Integer, Artist> mArtistMap = null;

    /**
     * Получить список исполнителей.
     *
     * @param refresh нужно ли загружать заново, даже если есть кешированная копия.
     * @return Observable списка исполнителей
     */
    public Observable<List<Artist>> getArtistList(boolean refresh) {
        Observable<List<Artist>> observable;
        if (!refresh && mArtistList != null) {
            // если не нужно загружать данные заново, и они есть в памяти, возвращаем их.
            observable = Observable.just(mArtistList);
        } else {
            // иначе загружаем данные.
            observable = getArtistListFromInternetObservable();
        }
        return observable
                .subscribeOn(mIoScheduler)
                .observeOn(mUiScheduler);
    }

    /**
     * Observable для получения списка исполнителей.
     */
    private Observable<List<Artist>> getArtistListFromInternetObservable() {
        return mArtistService
                .getArtistList()
                .doOnNext((List<Artist> artistList) -> {
                    mArtistList = artistList;
                    mArtistMap = new HashMap<>();
                    for (Artist artist : artistList) {
                        mArtistMap.put(artist.getId(), artist);
                    }
                });
    }

    /**
     * Получить исполнителя по id.
     *
     * @param id      id исполнителя
     * @param refresh нужно ли загружать заново, даже если есть кешированная копия.
     * @return Observable исполнителя.
     */
    public Observable<Artist> getArtistById(int id, boolean refresh) {
        Artist artist = mArtistMap == null ? null : mArtistMap.get(id);
        // если исполнитель уже кеширован в памяти, и не надо обновлять данные, просто возвращаем его.
        if (!refresh && artist != null) {
            return Observable.just(artist)
                    .subscribeOn(mIoScheduler)
                    .observeOn(mUiScheduler);
        }
        // иначе загружаем список исполнителей и выбираем нужного.
        return getArtistList(refresh)
                .flatMap(Observable::from)
                .filter(artist1 -> artist1.getId() == id)
                .limit(1)
                .subscribeOn(mIoScheduler)
                .observeOn(mUiScheduler);
    }

    /**
     * @return есть ли подключение к интернету.
     */
    public boolean isInternetConnected() {
        return InternetUtils.isInternetConnected(mAppContext);
    }

    /**
     * Класс-исключение, сигнализирующий об отсутствии соединения с интернетом.
     */
    public static class NoInternetException extends Exception {
    }
}
