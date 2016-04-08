package com.qwert2603.testyandex.artist_details;

import android.app.Fragment;

import com.qwert2603.testyandex.base.BaseActivity;

/**
 * Activity, содержащая фрагмент с подробной информацией об исполнителе.
 */
public class ArtistDetailsActivity extends BaseActivity {

    /**
     * ID исполнителя о котором надо отобразить подробную информацию.
     */
    public static final String EXTRA_ARTIST_ID = "com.qwert2603.testyandex.artist_details.EXTRA_ARTIST_ID";

    @Override
    protected Fragment createFragment() {
        int artistId = getIntent().getIntExtra(EXTRA_ARTIST_ID, 0);
        return ArtistDetailsFragment.newInstance(artistId);
    }
}
