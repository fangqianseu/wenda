/*
Date: 04/13,2019, 10:48
*/
package com.fq.service;

import com.alibaba.fastjson.JSON;
import com.fq.async.EventHandler;
import com.fq.async.EventModel;
import com.fq.async.EventType;
import com.fq.util.JedisAdapter;
import com.fq.util.RedisKeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service
@PropertySource({"classpath:my.properties"})
public class EventListerService implements InitializingBean, ApplicationContextAware {
    private static final Logger logger = LoggerFactory.getLogger(EventListerService.class);
    private ApplicationContext applicationContext;
    private Map<EventType, List<EventHandler>> eventListerConfig = new HashMap<>();
    private ThreadPoolExecutor threadpool;

    @Autowired
    private JedisAdapter jedisAdapter;

    @Value("${threadpool.poolsize}")
    private int poolsize;

    @Override
    /**
     * 初始化 eventListerConfig, 生成 eventType - eventHandler的对应列表
     */
    public void afterPropertiesSet() throws Exception {
        Map<String, EventHandler> beans = applicationContext.getBeansOfType(EventHandler.class);
        if (beans != null) {
            for (EventHandler eventHandler : beans.values()) {
                List<EventType> eventTypes = eventHandler.getSupportEventTypes();
                for (EventType eventType : eventTypes) {
                    if (!eventListerConfig.containsKey(eventType))
                        eventListerConfig.put(eventType, new ArrayList<EventHandler>());
                    eventListerConfig.get(eventType).add(eventHandler);
                }
            }
        }

        threadpool = new ThreadPoolExecutor(poolsize, poolsize, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
        beginLister();
    }

    private void beginLister() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    String key = RedisKeyUtil.getEventQueueKey();
                    List<String> events = jedisAdapter.brpop(0, key);

                    for (String message : events) {
                        // redis返回的第一行为key
                        if (message.equals(key)) {
                            continue;
                        }
                        logger.info("Get Event from redis: " + message);
                        //处理事件
                        EventModel eventModel = JSON.parseObject(message, EventModel.class);
                        if (!eventListerConfig.containsKey(eventModel.getType())) {
                            logger.error("不能识别的事件");
                            continue;
                        }

                        for (EventHandler handler : eventListerConfig.get(eventModel.getType())) {
                            threadpool.execute(new EventModelTask(handler, eventModel));
                        }
                    }
                }
            }
        }).start();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    private static class EventModelTask implements Runnable {
        private EventHandler handler;
        private EventModel eventModel;

        private EventModelTask(EventHandler handler, EventModel eventModel) {
            this.handler = handler;
            this.eventModel = eventModel;
        }

        @Override
        public void run() {
            handler.doHandle(eventModel);
        }
    }
}
