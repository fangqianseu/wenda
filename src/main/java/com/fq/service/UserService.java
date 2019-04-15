/*
Date: 04/10,2019, 21:15
*/
package com.fq.service;

import com.fq.dao.UserDao;
import com.fq.model.SessionHolder;
import com.fq.model.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private String headUrl = "https://avatars0.githubusercontent.com/u/";
    @Autowired
    private UserDao userDao;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private SessionHolder sessionHolder;

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

        if (userDao.selectUserByname(username) != null) {
            map.put("msg", "用户已经注册");
            return map;
        }

        User user = new User();
        user.setName(username);
        user.setHeadUrl(headUrl + new Random().nextInt(20000));
        user.setSalt(UUID.randomUUID().toString().substring(0, 4));
        user.setPassword(DigestUtils.md5Hex(password + user.getSalt()));
        userDao.addUser(user);

        logger.info("User register: " + user.toString());

        String ticket = ticketService.addTicket(user.getId());

        logger.info("ticket add: " + ticket);

        map.put("ticket", ticket);
        map.put("userId", user.getId());

        return map;
    }

    public Map<String, Object> login(String username, String password) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isBlank(username)) {
            map.put("msg", "用户名不能为空");
            return map;
        }
        if (StringUtils.isBlank(password)) {
            map.put("msg", "密码不能为空");
            return map;
        }

        User user = userDao.selectUserByname(username);
        if (user == null) {
            map.put("msg", "用户不存在");
            return map;
        }

        if (!user.getPassword().equals(DigestUtils.md5Hex(password + user.getSalt()))) {
            map.put("msg", "用户名或密码错误");
            return map;
        }

        String ticket = ticketService.addTicket(user.getId());
        logger.info("ticket add: " + ticket);

        map.put("ticket", ticket);
        map.put("userId", user.getId());

        return map;
    }

    public User getUserById(int id) {
        logger.info("User query By Id: " + id);
        return userDao.selectUserById(id);
    }

    public User getUserByName(String name) {
        logger.info("User query By name: " + name);
        return userDao.selectUserByname(name);
    }

    public void loginout(String ticket) {
        logger.info("ticket delect: " + ticket);
        ticketService.delectTicket(ticket);
    }
}
