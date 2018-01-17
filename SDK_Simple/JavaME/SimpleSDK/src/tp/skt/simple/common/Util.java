package tp.skt.simple.common;

/**
 * Util.java
 * <p>
 * Copyright (C) 2017. SK Telecom, All Rights Reserved.
 * Written 2017, by SK Telecom
 */
public class Util {
    /** log enable flag **/
    private static boolean logEnabled = false;

    /**
     * print log
     *
     * @param message
     */
    public static void log(String message) {
        if(Util.logEnabled == true) {
            System.out.println("TP_SIMPLE_SDK, [INFO] : "+ message);
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
    public static String  checkNull(String target, String message) {
        if (target == null) {
                throw new NullPointerException(message);
        }
        return target;
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
