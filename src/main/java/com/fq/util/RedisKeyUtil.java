/*
Date: 04/13,2019, 10:46
*/
package com.fq.util;

public class RedisKeyUtil {
    private static final String KEY_EVENTQUEUE = "EVENT_QUEUE";
    private static final String KEY_LOGIN = "CACHE_LOGIN";

    public static String getEventQueueKey() {
        return KEY_EVENTQUEUE;
    }

    public static String getLoginKey(String ticket) {
        return KEY_LOGIN + ":" + ticket;
    }
}
