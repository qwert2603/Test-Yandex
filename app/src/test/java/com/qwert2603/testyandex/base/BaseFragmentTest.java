package com.qwert2603.testyandex.base;

import android.os.Bundle;

import com.qwert2603.testyandex.BaseTest;
import com.qwert2603.testyandex.R;
import com.qwert2603.testyandex.artist_list.ArtistListActivity;
import com.qwert2603.testyandex.artist_list.ArtistListFragment;
import com.qwert2603.testyandex.artist_list.ArtistListPresenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.robolectric.Robolectric;

import javax.inject.Inject;

/**
 * Тесты для {@link BaseFragment}.
 * Тестируется корректность методов:
 * {@link BaseFragment#onCreate(Bundle)}
 * {@link BaseFragment#onDestroy()}
 * {@link BaseFragment#onResume()}
 * {@link BaseFragment#onPause()}
 */
public class BaseFragmentTest extends BaseTest {

    /**
     * используем ArtistListFragment, а не BaseFragment,
     * и ArtistListPresenter, а не BasePresenter.
     * так как нужно протестировать взаимодействие с презентером,
     * а BaseFragment не содержит презентера, а получает его с помощью {@link BaseFragment#getPresenter()}.
     * В итоге ArtistListPresenter управляет ArtistListFragment.
     * Но тут тестируется только базовые операции фрагмента над презентером.
     */

    @Inject
    ArtistListPresenter mArtistListPresenterMock;

    private ArtistListFragment mArtistListFragment;

    @Before
    public void setUp() {
        getTestComponent().inject(BaseFragmentTest.this);

        ArtistListActivity artistListActivity = Robolectric.setupActivity(ArtistListActivity.class);
        mArtistListFragment = (ArtistListFragment) artistListActivity.getFragmentManager()
                .findFragmentById(R.id.fragment_container);

        // так как методы mArtistListPresenterMock уже могли вызываться при привязке фрагмента к активити.
        Mockito.reset(mArtistListPresenterMock);
    }

    @Test
    public void testBindView() {
        mArtistListFragment.onCreate(null);

        Mockito.verify(mArtistListPresenterMock).bindView(mArtistListFragment);
        Mockito.verify(mArtistListPresenterMock, Mockito.never()).onViewReady();
        Mockito.verify(mArtistListPresenterMock, Mockito.never()).onViewNotReady();
        Mockito.verify(mArtistListPresenterMock, Mockito.never()).unbindView();
    }

    @Test
    public void testViewReady() {
        mArtistListFragment.onResume();

        Mockito.verify(mArtistListPresenterMock, Mockito.never()).bindView(mArtistListFragment);
        Mockito.verify(mArtistListPresenterMock).onViewReady();
        Mockito.verify(mArtistListPresenterMock, Mockito.never()).onViewNotReady();
        Mockito.verify(mArtistListPresenterMock, Mockito.never()).unbindView();
    }

    @Test
    public void testViewNotReady() {
        mArtistListFragment.onPause();

        Mockito.verify(mArtistListPresenterMock, Mockito.never()).bindView(mArtistListFragment);
        Mockito.verify(mArtistListPresenterMock, Mockito.never()).onViewReady();
        Mockito.verify(mArtistListPresenterMock).onViewNotReady();
        Mockito.verify(mArtistListPresenterMock, Mockito.never()).unbindView();
    }

    @Test
    public void testUnbindView() {
        mArtistListFragment.onDestroy();

        Mockito.verify(mArtistListPresenterMock, Mockito.never()).bindView(mArtistListFragment);
        Mockito.verify(mArtistListPresenterMock, Mockito.never()).onViewReady();
        Mockito.verify(mArtistListPresenterMock, Mockito.never()).onViewNotReady();
        Mockito.verify(mArtistListPresenterMock).unbindView();
    }

}
