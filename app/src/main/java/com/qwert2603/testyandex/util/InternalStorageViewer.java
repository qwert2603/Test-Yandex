package com.qwert2603.testyandex.util;

import android.content.Context;

import java.io.File;

/**
 * Класс для вывода в logcat содержимое внутреннего хранилища приложения.
 */
public final class InternalStorageViewer {

    /**
     * Метка вывода этого класса.
     */
    public static final String TAG = "InternalStorageViewer";

    /**
     * Вывести в logcat содержимое внутреннего хранилища приложения.
     */
    public static void print(Context context) {
        File internalStorage = context.getApplicationContext().getFilesDir();
        LogUtils.d(TAG, "## INTERNAL STORAGE START ##");
        int totalLength = printDir(internalStorage);
        LogUtils.d(TAG, "## INTERNAL STORAGE END ## total length == " + totalLength);
    }

    /**
     * Вывести в logcat содержмое папки. Рекурсивно.
     * Вернет общий размер папка с учетом размеров вложенных папок.
     *
     * @param dir папка, содержимое которой будет выведено.
     * @return размер переданной папки с учетом подпапок.
     */
    public static int printDir(File dir) {
        int length = 0;
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                length += printDir(file);
            } else {
                LogUtils.d(TAG, file.toString() + " { length = " + file.length() + " }");
                length += file.length();
            }
        }
        return length;
    }
}
