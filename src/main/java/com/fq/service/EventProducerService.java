/*
Date: 04/13,2019, 10:38
*/
package com.fq.service;

import com.alibaba.fastjson.JSONObject;
import com.fq.async.EventModel;
import com.fq.util.JedisAdapter;
import com.fq.util.RedisKeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventProducerService {
    private static final Logger logger = LoggerFactory.getLogger(EventProducerService.class);

    @Autowired
    private JedisAdapter jedisAdapter;

    public boolean commitEventModel(EventModel eventModel) {
        try {
            String key = RedisKeyUtil.getEventQueueKey();
            String value = JSONObject.toJSONString(eventModel);
            jedisAdapter.lpush(key, value);
            return true;
        } catch (Exception e) {
            logger.error(e.toString());
            return false;
        }
    }
}
