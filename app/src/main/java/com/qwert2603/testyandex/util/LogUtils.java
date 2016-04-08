package com.qwert2603.testyandex.util;

import android.util.Log;

/**
 * Класс для выведения логов приложения.
 */
public final class LogUtils {

    /**
     * Метка логов приложения.
     */
    public static final String APP_TAG = "AASSDD";

    /**
     * Стандартное сообщение об ошибке.
     */
    public static final String ERROR_MSG = "ERROR!!!";

    /**
     * Вывести отладочное сообщение.
     * @param s сообщение.
     */
    public static void d(String s) {
        d(APP_TAG, s);
    }

    /**
     * Вывести отладочное сообщение.
     * @param tag метка.
     * @param s сообщение.
     */
    public static void d(String tag, String s) {
        Log.d(tag, s);
    }

    /**
     * Вывести сообщение об ошибке.
     * @param s сообщение
     */
    public static void e(String s) {
        e(APP_TAG, s);
    }

    /**
     * Вывести сообщение об ошибке.
     * @param tag метка.
     * @param s сообщение
     */
    public static void e(String tag, String s) {
        Log.e(tag, s);
    }

    /**
     * Вывести сообщение об ошибке (исключение).
     * @param t объект исклюючения.
     */
    public static void e(Throwable t) {
        Log.e(APP_TAG, ERROR_MSG, t);
    }

}
