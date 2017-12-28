package tp.skt.simple.common;

import android.util.Log;

/**
 * Simple.java
 * <p>
 * Copyright (C) 2017. SK Telecom, All Rights Reserved.
 * Written 2017, by SK Telecom
 */
public class Util {
    /**
     * log enable flag
     **/
    private static boolean logEnabled = false;

    /**
     * print log
     *
     * @param message
     */
    public static void log(String message) {
        if (Util.logEnabled == true) {
            Log.i("TP_SIMPLE_SDK", message);
        }
    }

    /**
     * check object is null
     *
     * @param object
     * @param message
     * @param <T>
     * @return
     */
    public static <T> T checkNull(T object, String message) {
        if (object == null) {
            throw new NullPointerException(message);
        }
        return object;
    }

    /**
     * set log enable
     *
     * @param logEnabled
     */
    public static void setLogEnabled(boolean logEnabled) {
        Util.logEnabled = logEnabled;
    }
}
