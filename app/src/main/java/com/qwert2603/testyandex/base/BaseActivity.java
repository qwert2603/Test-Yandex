package com.qwert2603.testyandex.base;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.qwert2603.testyandex.R;

/**
 * Базовая Activity, содержащая 1 фрагмент.
 */
public abstract class BaseActivity extends AppCompatActivity {

    /**
     * @return фрагмент для отображения в этой Activity.
     */
    protected abstract Fragment createFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        // если фрагмента нет, создаем его.
        Fragment fragment = getFragmentManager().findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            fragment = createFragment();
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commitAllowingStateLoss();
        }
    }

}

