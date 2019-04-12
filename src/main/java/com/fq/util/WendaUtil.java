/*
Date: 04/11,2019, 15:05
*/
package com.fq.util;

import com.alibaba.fastjson.JSONObject;

public class WendaUtil {
    // 未登录用户的user id
    public static int ANONYMOUS_USERID = 0;

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
}