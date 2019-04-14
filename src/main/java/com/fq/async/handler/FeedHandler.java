/*
Date: 04/14,2019, 10:01
*/
package com.fq.async.handler;

import com.alibaba.fastjson.JSONObject;
import com.fq.async.EventHandler;
import com.fq.async.EventModel;
import com.fq.async.EventType;
import com.fq.model.Feed;
import com.fq.service.FeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class FeedHandler implements EventHandler {
    @Autowired
    private FeedService feedService;

    @Override
    public void doHandle(EventModel model) {
        Feed feed = new Feed();

        feed.setType(model.getType().getValue());
        feed.setCreatedDate(new Date());
        feed.setUserId(model.getEntityId());
        feed.setData(bulidDate(model.getType(), model));

        feedService.addFeed(feed);
    }

    private String bulidDate(EventType type, EventModel model) {
        Map<String, String> map = new HashMap<String, String>();
        switch (type) {
            case QUESTION_COMMENT_ADD:
                map.put("content", "问题增加了评论");
                break;
            case QUESTION_FOLLOWED:
                map.put("content", "问题被关注了");
                break;
            case QUESTION_ADD:
                map.put("content", "提了新问题");
                break;
            case USER_ADDFOLLOWER:
                map.put("content", "用户被新关注了");
                break;
            default:
        }
        return JSONObject.toJSONString(map);
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.QUESTION_COMMENT_ADD, EventType.QUESTION_FOLLOWED
                , EventType.QUESTION_ADD, EventType.USER_ADDFOLLOWER);
    }
}
