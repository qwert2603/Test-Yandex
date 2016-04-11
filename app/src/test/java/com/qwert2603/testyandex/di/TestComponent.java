package com.qwert2603.testyandex.di;

import com.qwert2603.testyandex.view.ArtistListFragmentTest;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ViewTestModule.class})
public interface TestComponent extends AppComponent {

    void inject(ArtistListFragmentTest artistListFragmentTest);

}
