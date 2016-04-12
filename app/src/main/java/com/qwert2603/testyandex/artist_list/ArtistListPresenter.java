package com.qwert2603.testyandex.artist_list;

import android.support.annotation.NonNull;

import com.qwert2603.testyandex.TestYandexApplication;
import com.qwert2603.testyandex.base.BasePresenter;
import com.qwert2603.testyandex.model.entity.Artist;
import com.qwert2603.testyandex.model.DataManager;
import com.qwert2603.testyandex.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Subscription;

/**
 * Презентер для управления представлением со списком исполнителей для шаблона MVP.
 */
public class ArtistListPresenter extends BasePresenter<List<Artist>, ArtistListView> {

    @Inject
    DataManager mDataManager;

    /**
     * Подписка на получение списка исполнителей.
     */
    private Subscription mSubscription;

    /**
     * Происходит ли загрузка.
     */
    private boolean mIsLoading;

    /**
     * Была ли ошибка подключения к интернету.
     */
    private boolean mIsNoInternet;

    /**
     * Текущий поисковый запрос.
     */
    private String mQuery;

    /**
     * Отображаемый список исполнителей. (С учетом поискового запроса).
     */
    private List<Artist> mShowingArtistList;

    public ArtistListPresenter() {
        TestYandexApplication.getAppComponent().inject(ArtistListPresenter.this);
        loadArtistList(true);
    }

    @Override
    public void onUpdateView(@NonNull ArtistListView view) {
        if (mShowingArtistList == null) {
            // список нельзя обновлять пока он не будет загружен.
            // поэтому отключаем RefreshLayout.
            view.setRefreshingConfig(false, false);
            if (mSubscription.isUnsubscribed()) {
                // если mSubscription == null, то произшла ошибка.
                if (mIsNoInternet) {
                    // ошибка связанная с отсутствием интернета.
                    view.showNoInternet(false);
                } else {
                    // какая-то другая ошибка.
                    view.showError();
                }
            } else {
                // если список еще не загружен и mSubscription == null, то ошибок не было, загрузка продолжается.
                // отображаем это.
                view.showLoading();
            }
        } else {
            view.setRefreshingConfig(true, mIsLoading);
            if (mShowingArtistList.isEmpty()) {
                if (mQuery == null || mQuery.isEmpty()) {
                    view.showEmpty();
                } else {
                    view.showNothingFound();
                }
            } else {
                view.showList(mShowingArtistList);
            }
        }
    }

    @Override
    public void unbindView() {
        // когда представление отвязывается, больше не потребуется подписка на получение списка исполнителей.
        if (mSubscription != null) {
            mSubscription.unsubscribe();
        }
        super.unbindView();
    }

    @Override
    protected void setModel(List<Artist> artistList) {
        super.setModel(artistList);
        doSearch();
        updateView();
    }

    public String getCurrentQuery() {
        return mQuery;
    }

    /**
     * Загрузить список исполнителей заново.
     */
    public void onReload() {
        if (!mDataManager.isInternetConnected()) {
            // если нет подключения к интернету, отменяем процесс обновления и выводим сообщение о том, что нет интернета.
            getView().setRefreshingConfig(true, false);
            getView().showNoInternet(getModel() != null);
            return;
        }
        loadArtistList(true);
        updateView();
    }

    /**
     * Действие по случаю нажатия на исполнителя.
     *
     * @param position позиция нажатого исполнителя с списке.
     */
    public void onArtistAtPositionClicked(int position) {
        getView().moveToArtistDetails(mShowingArtistList.get(position).getId());
    }

    /**
     * Действие по случаю изменения поискового запроса.
     * Поиск не зависит от регистра.
     *
     * @param query поисковый звпрос.
     */
    public void onSearchQueryChanged(String query) {
        mQuery = query.toLowerCase();
        doSearch();
        updateView();
    }

    /**
     * Выполнить поиск в соответствии с текущим запросом.
     */
    private void doSearch() {
        List<Artist> artistList = getModel();
        mShowingArtistList = null;
        if (artistList != null) {
            if (mQuery == null || mQuery.isEmpty()) {
                mShowingArtistList = artistList;
            } else {
                mShowingArtistList = new ArrayList<>();
                for (Artist artist : artistList) {
                    if (artist.getName().toLowerCase().contains(mQuery)) {
                        mShowingArtistList.add(artist);
                    }
                }
            }
        }
    }

    /**
     * Загрузить список исполнителей.
     *
     * @param refresh нужно ли загружать заново, даже если есть кешированная копия.
     */
    private void loadArtistList(boolean refresh) {
        if (mSubscription != null) {
            // отписываемся от прежней подписки на получение списка исполнителей.
            mSubscription.unsubscribe();
        }
        mSubscription = mDataManager
                .getArtistList(refresh)
                .subscribe(
                        artistList -> {
                            mIsLoading = false;
                            setModel(artistList);
                        },
                        throwable -> {
                            mIsLoading = false;
                            mIsNoInternet = throwable instanceof DataManager.NoInternetException;
                            if (mSubscription != null) {
                                // в случае ошибки отписывается от подписки на получение списка исполнителей.
                                mSubscription.unsubscribe();
                            }
                            setModel(null);
                            LogUtils.e(throwable);
                        }
                );
    }
}
