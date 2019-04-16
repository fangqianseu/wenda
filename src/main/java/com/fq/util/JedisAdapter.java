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
import java.util.Map;

@Component
public class JedisAdapter {
    private static final Logger logger = LoggerFactory.getLogger(JedisAdapter.class);

    @Autowired
    private JedisPool jedisPool;

    public String set(String key, String value) {
        String res = null;
        try (Jedis jedis = jedisPool.getResource()) {
            res = jedis.set(key, value);
        } catch (Exception e) {
            logger.error("发生异常" + e.toString());
            throw e;
        }

        return res;
    }

    public long setnx(String key, String value) {
        Long res = 0L;
        try (Jedis jedis = jedisPool.getResource()) {
            res = jedis.setnx(key, value);
        } catch (Exception e) {
            logger.error("发生异常" + e.toString());
            throw e;
        }
        return res;
    }

    public String setex(String key, int seconds, String value) {
        String res = null;
        try (Jedis jedis = jedisPool.getResource()) {
            res = jedis.setex(key, seconds, value);
        } catch (Exception e) {
            logger.error("发生异常" + e.toString());
            throw e;
        }
        return res;
    }

    public String get(String key) {
        String res = null;
        try (Jedis jedis = jedisPool.getResource()) {
            res = jedis.get(key);
        } catch (Exception e) {
            logger.error("发生异常" + e.toString());
            throw e;
        }
        return res;
    }

    public Long del(String key) {
        Long res = 0L;
        try (Jedis jedis = jedisPool.getResource()) {
            res = jedis.del(key);
        } catch (Exception e) {
            logger.error("发生异常" + e.toString());
            throw e;
        }
        return res;
    }

    public long lpush(String key, String value) {
        Long res = 0L;
        try (Jedis jedis = jedisPool.getResource()) {
            res = jedis.lpush(key, value);
        } catch (Exception e) {
            logger.error("发生异常" + e.toString());
            throw e;
        }
        return res;
    }

    public List<String> brpop(int timeout, String key) {
        List<String> res = null;
        try (Jedis jedis = jedisPool.getResource()) {
            res = jedis.brpop(timeout, key);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
            throw e;
        }
        return res;
    }


    public void hset(String key, Map<String, String> map) {
        try (Jedis jedis = jedisPool.getResource()) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                jedis.hset(key, entry.getKey(), entry.getValue());
            }
        } catch (Exception e) {
            logger.error("发生异常" + e.toString());
            throw e;
        }
    }

    public String hget(String key, String field) {
        String res = null;
        try (Jedis jedis = jedisPool.getResource()) {
            res = jedis.hget(key, field);
        } catch (Exception e) {
            logger.error("发生异常" + e.toString());
            throw e;
        }
        return res;
    }
}
