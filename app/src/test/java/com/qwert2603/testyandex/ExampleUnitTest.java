package com.qwert2603.testyandex;

import android.app.Fragment;

import com.qwert2603.testyandex.artist_list.ArtistListActivity;
import com.qwert2603.testyandex.artist_list.ArtistListFragment;
import com.qwert2603.testyandex.model.entity.Artist;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 19)
public class ExampleUnitTest {

    @Test
    public void f0() throws Exception {
        assertEquals(42, 6 * 7);
    }

    @Test
    public void f1() {
        ArtistListActivity artistListActivity = Robolectric.setupActivity(ArtistListActivity.class);
        Fragment fragment = artistListActivity.getFragmentManager().findFragmentById(R.id.fragment_container);
        assertEquals(fragment.getClass(), ArtistListFragment.class);
    }

    @Test
    public void f2() {
        Artist[] artists = TestUtils.readJson(TestConst.ARTISTS_JSON, Artist[].class);
        assertEquals(artists.length, 2);
        assertEquals(artists[0].getTracks(), 81);
    }

}