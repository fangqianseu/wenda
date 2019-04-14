/*
Date: 04/13,2019, 10:21
*/
package com.fq.async;

/**
 * 定义 异步处理框架 处理事件的类型
 */
public enum EventType {
    QUESTION_ADD(1),
    QUESTION_COMMENT_ADD(2),
    QUESTION_FOLLOWED(3),

    COMMENT_AGREEMENT(4),
    COMMENT_DISAGREEMENT(5),

    USER_ADDFOLLOWER(6),
    USER_UNFOLLOWER(7);

    private int value;

    EventType(int i) {
        this.value = i;
    }

    public int getValue() {
        return value;
    }
}
