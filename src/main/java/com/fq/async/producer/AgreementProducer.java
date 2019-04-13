/*
Date: 04/13,2019, 15:26
*/
package com.fq.async.producer;

import com.fq.async.EventType;
import com.fq.model.EntityType;
import com.fq.service.EventProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;


@Component
public class AgreementProducer {
    private final Logger logger = LoggerFactory.getLogger(AgreementProducer.class);
    @Autowired
    private EventProducerService eventProducerService;

    public boolean buildEventModel(int userId, int toUserId, String userName, int qid) {
        HashMap<String, String> map = new HashMap<>();

        map.put("toUser", String.valueOf(toUserId));
        map.put("userName", userName);
        map.put("qusetionId", String.valueOf(qid));

        return eventProducerService.creatEventModel(EventType.AGREEMENT, userId, EntityType.ENTITY_USER, map);
    }
}
