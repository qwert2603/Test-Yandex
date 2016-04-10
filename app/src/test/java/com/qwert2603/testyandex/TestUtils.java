package com.qwert2603.testyandex;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;


public class TestUtils {

    static Gson gson = new Gson();

    public static void log(String log) {
        System.out.println(log);
    }

    public static  <T> T readJson(String fileName, Class<T> inClass) {
        return gson.fromJson(readString(fileName), inClass);
    }

    public static String readString(String fileName) {
        InputStream stream = TestUtils.class.getClassLoader().getResourceAsStream(fileName);
        try {
            int size = stream.available();
            byte[] buffer = new byte[size];
            int result = stream.read(buffer);
            return new String(buffer, "utf8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (stream != null) {
                    stream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
