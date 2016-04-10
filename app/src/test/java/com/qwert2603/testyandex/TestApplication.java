package com.qwert2603.testyandex;

import com.qwert2603.testyandex.di.AppComponent;

public class TestApplication extends TestYandexApplication {

    @Override
    protected AppComponent buildComponent() {
        return DaggerTestComponent.builder().build();
    }
}
