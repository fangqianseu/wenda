/*
Date: 04/11,2019, 15:05
*/
package com.fq.util;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

public class WendaUtil {
    // 未登录用户的user id
    public static final int ANONYMOUS_USERID = 0;
    // 系统用户
    public static final int SYSTEM_USERID = 1;

    // ajax 请求 的返回码
    public static String getJSONString(int code) {
        JSONObject json = new JSONObject();
        json.put("code", code);
        return json.toJSONString();
    }

    public static String getJSONString(int code, String msg) {
        JSONObject json = new JSONObject();
        json.put("code", code);
        json.put("msg", msg);
        return json.toJSONString();
    }

    public static String getJSONString(int code, Map<String, Object> map) {
        JSONObject json = new JSONObject();
        json.put("code", code);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            json.put(entry.getKey(), entry.getValue());
        }
        return json.toJSONString();
    }
}
