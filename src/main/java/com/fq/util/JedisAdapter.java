/*
Date: 04/13,2019, 10:07
*/
package com.fq.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

@Component
public class JedisAdapter {
    private static final Logger logger = LoggerFactory.getLogger(JedisAdapter.class);

    @Autowired
    private JedisPool jedisPool;

    public String set(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.set(key, value);
        } catch (Exception e) {
            logger.error("发生异常" + e.toString());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    public long setnx(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.setnx(key, value);
        } catch (Exception e) {
            logger.error("发生异常" + e.toString());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return 0;
    }

    public String setex(String key, int seconds, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.setex(key, seconds, value);
        } catch (Exception e) {
            logger.error("发生异常" + e.toString());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    public String get(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.get(key);
        } catch (Exception e) {
            logger.error("发生异常" + e.toString());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    public Long del(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.del(key);
        } catch (Exception e) {
            logger.error("发生异常" + e.toString());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return 0L;
    }

    public long lpush(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.lpush(key, value);
        } catch (Exception e) {
            logger.error("发生异常" + e.toString());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return 0;
    }

    public List<String> brpop(int timeout, String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.brpop(timeout, key);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }


}
