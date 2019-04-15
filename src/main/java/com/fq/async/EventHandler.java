package com.fq.async;

import org.springframework.scheduling.annotation.Async;

import java.util.List;

public interface EventHandler {
    /**
     * 时间处理器 接口
     * spring boot 自带的 异步调用注解
     * @param model
     */
    @Async
    void doHandle(EventModel model);

    List<EventType> getSupportEventTypes();
}
