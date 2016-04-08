package com.qwert2603.testyandex;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.qwert2603.testyandex.model.DataManager;
import com.qwert2603.testyandex.util.InternalStorageViewer;

import java.io.File;

public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();

        InternalStorageViewer.print(Application.this);

        // Инициализируем DataManager объектом Context приложения.
        DataManager.initWithAppContext(Application.this);


        // Инициируем загрузчик изображений.

        // Папка изображений, кешированных в памяти устройства.
        File cacheDir = new File(Application.this.getFilesDir(), "covers");

        DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true) // сохранять изображения в памяти.
                .cacheOnDisk(true) // сохранять изображения на диске.
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(Application.this)
                .threadPriority(Thread.NORM_PRIORITY - 3) // приритет потока загрузки изображений.
                .tasksProcessingOrder(QueueProcessingType.LIFO) // порядок загрузки изображений.
                .memoryCache(new LruMemoryCache(8 * 1024 * 1024))
                .memoryCacheSize(8 * 1024 * 1024) // размер кеша в памяти. (8 Мб)
                .diskCache(new UnlimitedDiskCache(cacheDir))
                .diskCacheSize(50 * 1024 * 1024) // размер кеша на диске. (50 Мб)
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator()) // генератор имен файлов
                .defaultDisplayImageOptions(displayImageOptions)
                .build();
        ImageLoader.getInstance().init(config);
    }
}
