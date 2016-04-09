package com.qwert2603.testyandex;

import android.app.Application;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.qwert2603.testyandex.di.AppComponent;
import com.qwert2603.testyandex.di.DaggerAppComponent;
import com.qwert2603.testyandex.util.InternalStorageViewer;

import java.io.File;

import dagger.Module;

@Module
public class TestYandexApplication extends Application {

    private static TestYandexApplication sTestYandexApplication;
    private static AppComponent sAppComponent;

    public static TestYandexApplication getTestYandexApplication() {
        return sTestYandexApplication;
    }

    public static AppComponent getAppComponent() {
        return sAppComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        sTestYandexApplication = TestYandexApplication.this;
        sAppComponent = DaggerAppComponent.builder().build();

        InternalStorageViewer.print(TestYandexApplication.this);

        // Инициируем загрузчик изображений.

        // Папка изображений, кешированных в памяти устройства.
        File cacheDir = new File(TestYandexApplication.this.getFilesDir(), "covers");

        DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true) // сохранять изображения в памяти.
                .cacheOnDisk(true) // сохранять изображения на диске.
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(TestYandexApplication.this)
                .threadPriority(Thread.NORM_PRIORITY - 3) // приритет потока загрузки изображений.
                .tasksProcessingOrder(QueueProcessingType.LIFO) // порядок загрузки изображений.
                .memoryCache(new LruMemoryCache(12 * 1024 * 1024))
                .memoryCacheSize(12 * 1024 * 1024) // размер кеша в памяти. (12 Мб)
                .diskCache(new UnlimitedDiskCache(cacheDir))
                .diskCacheSize(50 * 1024 * 1024) // размер кеша на диске. (50 Мб)
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator()) // генератор имен файлов
                .defaultDisplayImageOptions(displayImageOptions)
                .build();
        ImageLoader.getInstance().init(config);
    }

}
