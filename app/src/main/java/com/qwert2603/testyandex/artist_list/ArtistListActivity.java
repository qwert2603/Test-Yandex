package com.qwert2603.testyandex.artist_list;

import android.app.Fragment;

import com.qwert2603.testyandex.base.BaseActivity;

/**
 * Activity, содержащая фрагмент со списком исполнителей.
 */
public class ArtistListActivity extends BaseActivity {

    @Override
    protected Fragment createFragment() {
        return ArtistListFragment.newInstance();
    }
}

