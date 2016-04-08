package com.qwert2603.testyandex.base;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.CallSuper;

/**
 * Базовый фрагмент, построенный для работы с шаблоном MVP.
 * Содержит презентер {@link #mPresenter} и организует взаимодействие с ним:
 * - привязка/отвязка {@link BasePresenter#bindView(BaseView)}, {@link BasePresenter#unbindView()}.
 * - уведомление о готовности {@link BasePresenter#onViewReady()}, {@link BasePresenter#onViewNotReady()}.
 *
 * @param <P> Тип презентера, организующего работу фрагмента.
 */
public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements BaseView {

    private P mPresenter;

    /**
     * Создание презентера, организующего работу фрагмента.
     *
     * @return созданный презентер для этого фрагмента.
     */
    protected abstract P createPresenter();

    /**
     * @return презентер, организующий работу фрагмента, созданный с помощью {@link #createPresenter()}.
     */
    protected final P getPresenter() {
        return mPresenter;
    }

    @SuppressWarnings("unchecked")
    @CallSuper
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Сохраняем состояние, чтобы не создавать презентер заново.
        // Это позволяет не прерывать загрузку, если презентер что-то загружает.
        setRetainInstance(true);
        mPresenter = createPresenter();
        mPresenter.bindView(this);
    }

    @CallSuper
    @Override
    public void onDestroy() {
        mPresenter.unbindView();
        super.onDestroy();
    }

    @CallSuper
    @Override
    public void onResume() {
        super.onResume();
        mPresenter.onViewReady();
    }

    @CallSuper
    @Override
    public void onPause() {
        mPresenter.onViewNotReady();
        super.onPause();
    }

}
