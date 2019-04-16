/*
Date: 04/16,2019, 21:29
*/
package com.fq.aop;

import com.alibaba.fastjson.JSONObject;
import com.fq.model.User;
import com.fq.util.JedisAdapter;
import com.fq.util.RedisKeyUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Aspect
public class UserAspect {
    private static final Logger logger = LoggerFactory.getLogger(UserAspect.class);
    @Autowired
    private JedisAdapter jedisAdapter;


    @Transactional(rollbackFor = Exception.class)
    @Around(value = "bean(userService)&&execution(* getUserById(..))&& args(id)")
    public User getUserByIdAspect(ProceedingJoinPoint pjp, int id) throws Throwable {
        String key = RedisKeyUtil.getUSERIDKey(id);
        String res = null;
        User user;
        if ((res = jedisAdapter.get(key)) != null) {
            user = JSONObject.parseObject(res, User.class);
            logger.info("UserAspect.GetTicketByTicketAspect: get userId " + user.toString());
        } else {
            user = (User) pjp.proceed();
            jedisAdapter.setex(key, RedisKeyUtil.EXPRIE_TIME, JSONObject.toJSONString(user));
            logger.info("UserAspect.GetTicketByTicketAspect: add userId " + user.toString());

        }
        return user;
    }

    @Transactional(rollbackFor = Exception.class)
    @Around(value = "bean(userService)&&execution(* getUserByName(..))&& args(userName)")
    public User getUserByNameAspect(ProceedingJoinPoint pjp, String userName) throws Throwable {
        String key = RedisKeyUtil.getUSERNAMEKey(userName);
        String res = null;
        User user;
        if ((res = jedisAdapter.get(key)) != null) {
            user = JSONObject.parseObject(res, User.class);
            logger.info("UserAspect.getUserByNameAspect: get userName " + user.toString());
        } else {
            user = (User) pjp.proceed();
            jedisAdapter.setex(key, RedisKeyUtil.EXPRIE_TIME, JSONObject.toJSONString(user));
            logger.info("UserAspect.getUserByNameAspect: add userName " + user.toString());

        }
        return user;
    }
}
