/*
Date: 04/13,2019, 10:46
*/
package com.fq.util;

public class RedisKeyUtil {
    private static final String KEY_EVENTQUEUE = "EVENT_QUEUE";

    public static String getEventQueueKey() {
        return KEY_EVENTQUEUE;
    }
}
