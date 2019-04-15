/*
Date: 04/15,2019, 09:39
*/
package com.fq.service.cacheservice;

import com.fq.util.JedisAdapter;
import com.fq.util.RedisKeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginTicketCacheService {
    private static final Logger logger = LoggerFactory.getLogger(LoginTicketCacheService.class);
    @Autowired
    private JedisAdapter jedisAdapter;

    public boolean addLoginCache(String ticket, int seconds, int userId) {
        String key = RedisKeyUtil.getLoginKey(ticket);

        logger.info("LoginCacheService addLoginCache: " + ticket + " " + seconds + " " + userId);

        return jedisAdapter.setex(key, seconds, String.valueOf(userId)) != null;
    }

    public boolean removeLoginCache(String ticket) {
        String key = RedisKeyUtil.getLoginKey(ticket);
        logger.info("LoginCacheService removeLoginCache: " + ticket);
        return jedisAdapter.del(key) != 0L;
    }

    public String getLoginTicket(String ticket) {
        String key = RedisKeyUtil.getLoginKey(ticket);
        logger.info("LoginCacheService getLoginTicket: " + ticket);
        return jedisAdapter.get(key);
    }
}
