package com.thegame.util;

public class Log {

    private static final boolean LOG_LEVEL_DEBUG = true;

    public static void debug(String s) {
        if (LOG_LEVEL_DEBUG) {
            System.out.println(s);
        }
    }

    public static void error(String s) {
        System.err.println(s);
    }

    public static void error(String s, Throwable t) {
        error(s);
        t.printStackTrace();
    }
}
