package com.qwert2603.testyandex.model;

import android.content.Context;

import com.qwert2603.testyandex.model.entity.Artist;
import com.qwert2603.testyandex.util.InternetUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Главный класс для работы с данными.
 * Перед началом работы с ним нужно вызвать {@link #initWithAppContext(Context)}.
 * Для получения объекта класса DataManager используется {@link #get()}.
 * Класс позволяет загружать список исполнителей и кешировать его в оперативной памяти.
 */
public final class DataManager {

    private static DataManager sDataManager;

    private static final String BASE_URL = "http://download.cdn.yandex.net/mobilization-2016/";

    private DataManager(Context appContext) {
        mAppContext = appContext;

        // создаем OkHttpClient, способный кешировать данные.
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.cache(new Cache(mAppContext.getFilesDir(), 10 * 1024 * 1024));   // 10 Мб
        client.addInterceptor(new CacheInterceptor(appContext));
        client.addNetworkInterceptor(new CacheInterceptor(appContext));

        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .client(client.build())
                .build();

        mArtistService = retrofit.create(ArtistService.class);
    }

    /**
     * Инициализировать DataManager объектом Context приложения.
     *
     * @param appContext - объект Context приложения.
     */
    public static void initWithAppContext(Context appContext) {
        if (sDataManager == null) {
            sDataManager = new DataManager(appContext);
        }
    }

    /**
     * @return объект-синглтон класса DataManager.
     */
    public static DataManager get() {
        return sDataManager;
    }

    private Context mAppContext;
    private ArtistService mArtistService;

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
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
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
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }
        // иначе загружаем список исполнителей и выбираем нужного.
        return getArtistList(refresh)
                .flatMap(Observable::from)
                .filter(artist1 -> artist1.getId() == id)
                .limit(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
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
