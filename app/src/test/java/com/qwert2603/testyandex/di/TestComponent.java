package com.qwert2603.testyandex.di;

import com.qwert2603.testyandex.model.ArtistServiceTest;
import com.qwert2603.testyandex.model.DataManagerTest;
import com.qwert2603.testyandex.view.ArtistListFragmentTest;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppTestModule.class, ModelTestModule.class, PresenterTestModule.class, ViewTestModule.class})
public interface TestComponent extends AppComponent {

    void inject(ArtistListFragmentTest artistListFragmentTest);

    void inject(ArtistServiceTest artistServiceTest);

    void inject(DataManagerTest dataManagerTest);
}
