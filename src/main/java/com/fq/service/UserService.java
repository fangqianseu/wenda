/*
Date: 04/10,2019, 21:15
*/
package com.fq.service;

import com.fq.dao.UserDao;
import com.fq.model.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    /**
     * 注册用户
     *
     * @param username
     * @param password
     * @return map 类型 保存错误信息 或者 登录 ticket
     */
    public Map<String, Object> register(String username, String password) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isBlank(username)) {
            map.put("msg", "用户名不能为空");
            return map;
        }
        if (StringUtils.isBlank(password)) {
            map.put("msg", "密码不能为空");
            return map;
        }

        User user = new User();
        user.setName(username);
        user.setSalt(UUID.randomUUID().toString().substring(0, 4));
        user.setPassword(DigestUtils.md5Hex(password + user.getSalt()));
        userDao.addUser(user);

        return map;
    }
}
