/*
Date: 04/13,2019, 10:38
*/
package com.fq.service;

import com.alibaba.fastjson.JSONObject;
import com.fq.async.EventModel;
import com.fq.async.EventType;
import com.fq.model.EntityType;
import com.fq.util.JedisAdapter;
import com.fq.util.RedisKeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

@Service
public class EventProducerService {
    private static final Logger logger = LoggerFactory.getLogger(EventProducerService.class);

    @Autowired
    private JedisAdapter jedisAdapter;

    @Transactional(rollbackFor = Exception.class)
    public boolean creatEventModel(EventType eventType, int entityId, int entityType, Map<String, String> details) {
        EventModel eventModel = new EventModel();

        eventModel.setType(eventType);
        eventModel.setDate(new Date());
        eventModel.setEntityId(entityId);
        eventModel.setEntityType(EntityType.ENTITY_USER);

        for (Map.Entry<String, String> entry : details.entrySet()) {
            eventModel.setEventDetial(entry.getKey(), entry.getValue());
        }

        boolean res = commitEventModel(eventModel);
        if (res) {
            logger.info("Produce Event: " + eventModel.getType() + " actorid " + eventModel.getEntityId());
            return true;
        } else
            return false;
    }


    private boolean commitEventModel(EventModel eventModel) {
        String key = RedisKeyUtil.getEventQueueKey();
        String value = JSONObject.toJSONString(eventModel);
        jedisAdapter.lpush(key, value);
        return true;
    }
}
