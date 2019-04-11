/*
Date: 04/11,2019, 09:49
*/
package com.fq.model;

import org.springframework.stereotype.Component;

/**
 * 保存 登录的 user 信息
 */
@Component
public class SessionHolder {
    private ThreadLocal<User> users = new ThreadLocal<>();

    public User getUser() {
        return users.get();
    }

    public void setUser(User user) {
        users.set(user);
    }

    public void removeUser() {
        users.remove();
    }
}
