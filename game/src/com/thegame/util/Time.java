package com.thegame.util;

public class Time {

    public static boolean sleep(long msec) {
        try {
            Thread.sleep(msec);
            return true;
        } catch (InterruptedException e) {
            Log.debug("Interrupted");
            return false;
        }
    }

}
