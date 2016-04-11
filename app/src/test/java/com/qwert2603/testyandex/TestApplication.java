package com.qwert2603.testyandex;

import com.qwert2603.testyandex.di.DaggerTestComponent;
import com.qwert2603.testyandex.di.TestComponent;
import com.qwert2603.testyandex.di.ViewTestModule;

public class TestApplication extends TestYandexApplication {

    private static TestComponent sTestComponent;

    public static TestComponent getTestComponent() {
        return sTestComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sTestComponent = DaggerTestComponent.builder()
                .viewTestModule(new ViewTestModule())
                .build();
    }

}
