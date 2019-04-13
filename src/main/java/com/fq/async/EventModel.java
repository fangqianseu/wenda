/*
Date: 04/13,2019, 10:25
*/
package com.fq.async;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 发送到 异步处理通道的 事件模型
 */
public class EventModel {
    // 事件类型
    private EventType type;
    // 触发者id
    private int entityId;
    // 触发者类型
    private int entityType;
    // 触发时间
    private Date date;
    // 事件具体信息 k-v结构
    private Map<String, String> eventDetial = new HashMap<String, String>();

    // 设置 eventDetial中具体属性
    public EventModel setEventDetial(String key, String value) {
        eventDetial.put(key, value);
        return this;
    }

    // 获取 eventDetial中具体属性
    public String getEventDetial(String key) {
        return eventDetial.get(key);
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public int getEntityType() {
        return entityType;
    }

    public void setEntityType(int entityType) {
        this.entityType = entityType;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Map<String, String> getEventDetial() {
        return eventDetial;
    }

    public void setEventDetial(Map<String, String> eventDetial) {
        this.eventDetial = eventDetial;
    }
}
