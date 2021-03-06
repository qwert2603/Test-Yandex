package com.qwert2603.testyandex;

import com.qwert2603.testyandex.di.AppComponent;
import com.qwert2603.testyandex.di.AppTestModule;
import com.qwert2603.testyandex.di.DaggerTestComponent;

/**
 * Приложение для тестов.
 * Позволяет создавать тестовый компонет для Dagger.
 */
public class TestApplication extends TestYandexApplication {

    @Override
    protected AppComponent buildAppComponent() {
        return DaggerTestComponent.builder()
                .appTestModule(new AppTestModule(TestApplication.this))
                .build();
    }
}
