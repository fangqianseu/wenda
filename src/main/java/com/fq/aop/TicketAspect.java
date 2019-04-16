/*
Date: 04/15,2019, 20:40
*/
package com.fq.aop;

import com.alibaba.fastjson.JSONObject;
import com.fq.model.LoginTicket;
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
public class TicketAspect {
    private static final Logger logger = LoggerFactory.getLogger(TicketAspect.class);
    @Autowired
    private JedisAdapter jedisAdapter;

    @Transactional(rollbackFor = Exception.class)
    @Around(value = "bean(ticketService)&&execution(* getTicketByTicket(..))&& args(ticket)")
    public LoginTicket GetTicketByTicketAspect(ProceedingJoinPoint pjp, String ticket) throws Throwable {
        String key = RedisKeyUtil.getTICKETKey(ticket);
        String res = null;
        LoginTicket loginTicket;
        if ((res = jedisAdapter.get(key)) != null) {
            loginTicket = JSONObject.parseObject(res, LoginTicket.class);
            logger.info("TicketAspect.GetTicketByTicketAspect: get ticket " + loginTicket.toString());
        } else {
            loginTicket = (LoginTicket) pjp.proceed();
            if (loginTicket.getStatus() == 0) {
                jedisAdapter.setex(key, RedisKeyUtil.EXPRIE_TIME, JSONObject.toJSONString(loginTicket));
                logger.info("TicketAspect.GetTicketByTicketAspect: add ticket " + loginTicket.toString());
            }
        }
        return loginTicket;
    }

    @Transactional(rollbackFor = Exception.class)
    @Around(value = "bean(ticketService)&&execution(* delectTicket(..))&& args(ticket)")
    public int DelectTicketAspect(ProceedingJoinPoint pjp, String ticket) throws Throwable {
        int result = (int) pjp.proceed();
        if (result > 0) {
            String key = RedisKeyUtil.getTICKETKey(ticket);
            jedisAdapter.del(key);
            logger.info("TicketAspect.DelectTicketAspect: delect ticket " + key);
        }
        return result;
    }
}
