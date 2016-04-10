package com.qwert2603.testyandex.artist_list;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewAnimator;

import com.qwert2603.testyandex.R;
import com.qwert2603.testyandex.TestYandexApplication;
import com.qwert2603.testyandex.artist_details.ArtistDetailsActivity;
import com.qwert2603.testyandex.base.BaseActivity;
import com.qwert2603.testyandex.base.BaseFragment;
import com.qwert2603.testyandex.model.entity.Artist;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Фрагмент, отображающий список исполнителей.
 */
public class ArtistListFragment extends BaseFragment<ArtistListPresenter> implements ArtistListView {

    public static ArtistListFragment newInstance() {
        return new ArtistListFragment();
    }

    /**
     * Позиции дочерних элементов ViewAnimator'a.
     */
    private static final int POSITION_REFRESH_LAYOUT = 0;
    private static final int POSITION_LOADING_TEXT_VIEW = 1;
    private static final int POSITION_ERROR_TEXT_VIEW = 2;
    private static final int POSITION_EMPTY_TEXT_VIEW = 3;
    private static final int POSITION_NO_INTERNET_TEXT_VIEW = 4;
    private static final int POSITION_NOTHING_FOUND = 5;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.coordinator)
    CoordinatorLayout mCoordinatorLayout;

    @Bind(R.id.view_animator)
    ViewAnimator mViewAnimator;

    @Bind(R.id.refresh_layout)
    SwipeRefreshLayout mRefreshLayout;

    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @Inject
    ArtistListPresenter mArtistListPresenter;

    @Override
    protected ArtistListPresenter getPresenter() {
        return mArtistListPresenter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        TestYandexApplication.getAppComponent().inject(ArtistListFragment.this);
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_artist_list, container, false);

        ButterKnife.bind(ArtistListFragment.this, view);

        ((BaseActivity) getActivity()).setSupportActionBar(mToolbar);

        mRefreshLayout.setOnRefreshListener(mArtistListPresenter::onReload);
        mRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mViewAnimator.getChildAt(POSITION_ERROR_TEXT_VIEW).setOnClickListener(v -> mArtistListPresenter.onReload());
        mViewAnimator.getChildAt(POSITION_NO_INTERNET_TEXT_VIEW).setOnClickListener(v -> mArtistListPresenter.onReload());

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.artist_list, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint(getString(R.string.search));
        String query = mArtistListPresenter.getCurrentQuery();
        if (query != null && !query.isEmpty()) {
            searchItem.expandActionView();
            searchView.setQuery(query, true);
        }
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mArtistListPresenter.onSearchQueryChanged(newText);
                return true;
            }
        });
    }

    @Override
    public void showNoInternet(boolean snackbar) {
        if (snackbar) {
            Snackbar.make(mCoordinatorLayout, R.string.text_no_internet, Snackbar.LENGTH_SHORT).show();
        } else {
            setViewAnimatorDisplayedChild(POSITION_NO_INTERNET_TEXT_VIEW);
        }
    }

    @Override
    public void setRefreshingConfig(boolean enable, boolean refreshing) {
        mRefreshLayout.setEnabled(enable);
        mRefreshLayout.post(() -> mRefreshLayout.setRefreshing(refreshing));
    }

    @Override
    public void moveToArtistDetails(int artistId) {
        Intent intent = new Intent(getActivity(), ArtistDetailsActivity.class);
        intent.putExtra(ArtistDetailsActivity.EXTRA_ARTIST_ID, artistId);
        startActivity(intent);
    }

    @Override
    public void showNothingFound() {
        setViewAnimatorDisplayedChild(POSITION_NOTHING_FOUND);
    }

    @Override
    public void showLoading() {
        setViewAnimatorDisplayedChild(POSITION_LOADING_TEXT_VIEW);
    }

    @Override
    public void showError() {
        setViewAnimatorDisplayedChild(POSITION_ERROR_TEXT_VIEW);
    }

    @Override
    public void showEmpty() {
        setViewAnimatorDisplayedChild(POSITION_EMPTY_TEXT_VIEW);
    }

    @Override
    public void showList(List<Artist> list) {
        setViewAnimatorDisplayedChild(POSITION_REFRESH_LAYOUT);
        ArtistListAdapter adapter = (ArtistListAdapter) mRecyclerView.getAdapter();
        if (adapter != null && adapter.isShowingList(list)) {
            // если адаптер уже создан и отображает требуемый список, просто обновляем представление.
            adapter.notifyDataSetChanged();
        } else {
            // иначе - создаем новый адаптер.
            adapter = new ArtistListAdapter(list);
            adapter.setClickCallbacks(position -> mArtistListPresenter.onArtistAtPositionClicked(position));
            mRecyclerView.setAdapter(adapter);
        }
    }

    /**
     * Отобразить дочерний элемент ViewAnimator'а.
     *
     * @param position позиция дочернего элемента.
     */
    private void setViewAnimatorDisplayedChild(int position) {
        if (mViewAnimator.getDisplayedChild() != position) {
            mViewAnimator.setDisplayedChild(position);
        }
    }

}
