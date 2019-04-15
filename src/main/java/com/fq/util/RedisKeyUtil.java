/*
Date: 04/13,2019, 10:46
*/
package com.fq.util;

public class RedisKeyUtil {
    public static final int EXPRIE_TIME = 3600 * 24 * 7;
    private static final String KEY_EVENTQUEUE = "EVENT_QUEUE";
    private static final String KEY_LOGIN = "CACHE_LOGIN";
    private static final String KEY_TICKET = "CACHE_TICKET";

    public static String getEventQueueKey() {
        return KEY_EVENTQUEUE;
    }

    public static String getLoginKey(String ticket) {
        return KEY_LOGIN + ":" + ticket;
    }

    public static String getTICKETKey(String ticket) {
        return KEY_TICKET + ":" + ticket;
    }
}
