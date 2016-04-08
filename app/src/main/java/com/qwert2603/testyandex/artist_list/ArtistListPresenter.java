package com.qwert2603.testyandex.artist_list;

import android.support.annotation.NonNull;

import com.qwert2603.testyandex.base.BasePresenter;
import com.qwert2603.testyandex.model.entity.Artist;
import com.qwert2603.testyandex.model.DataManager;
import com.qwert2603.testyandex.util.LogUtils;

import java.util.List;

import rx.Subscription;

/**
 * Презентер для управления представлением со списком исполнителей для шаблона MVP.
 */
public class ArtistListPresenter extends BasePresenter<List<Artist>, ArtistListView> {

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

    public ArtistListPresenter() {
        loadArtistList(false);
    }

    @Override
    protected void onUpdateView(@NonNull ArtistListView view) {
        List<Artist> artistList = getModel();
        if (artistList == null) {
            // список нельзя обновлять пока он не будет загружен.
            // поэтому отключаем RefreshLayout.
            view.setRefreshingConfig(false, false);
            if (mSubscription == null) {
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
            if (artistList.isEmpty()) {
                view.showEmpty();
            } else {
                view.showList(artistList);
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

    /**
     * Загрузить список исполнителей заново.
     */
    public void onReload() {
        if (!DataManager.get().isInternetConnected()) {
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
        getView().moveToArtistDetails(getModel().get(position).getId());
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
        mSubscription = DataManager.get()
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
                                mSubscription = null;
                            }
                            setModel(null);
                            LogUtils.e(throwable);
                        }
                );
    }
}
