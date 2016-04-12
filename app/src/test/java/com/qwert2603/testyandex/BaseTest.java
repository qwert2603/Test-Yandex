package com.qwert2603.testyandex;

import com.qwert2603.testyandex.di.TestComponent;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 19, application = TestApplication.class)
@Ignore
public abstract class BaseTest {

    protected TestComponent getTestComponent() {
        return (TestComponent) TestApplication.getAppComponent();
    }

}
