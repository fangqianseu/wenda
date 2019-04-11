/*
Date: 04/11,2019, 15:27
*/
package com.fq.model;

import java.util.HashMap;
import java.util.Map;

public class ViewObject {
    private Map<String, Object> objs = new HashMap<String, Object>();

    public void set(String key, Object value) {
        objs.put(key, value);
    }

    public Object get(String key) {
        return objs.get(key);
    }
}
