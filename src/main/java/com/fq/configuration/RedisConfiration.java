/*
Date: 04/14,2019, 20:58
*/
package com.fq.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfiration {

    @Bean
    @Qualifier(value ="JedisPoolConfig" )
    public JedisPool JedisPoolBean(JedisPoolConfig jedisPoolConfig) {
        return new JedisPool(jedisPoolConfig, "localhost", 6379);
    }

    @Bean(name = "JedisPoolConfig")
    public JedisPoolConfig JedisPoolConfigBean() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(50);
        return jedisPoolConfig;
    }
}
