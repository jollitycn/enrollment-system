package com.educiot.recruit.common.util;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @author Jason Hong
 */
@PropertySource(value = {"classpath:redis.properties"})
@Component
@ConfigurationProperties(prefix = "redis")
@Data
public class RedisSetting implements Serializable {
    private int database = 2;
    private boolean ssl = false;

    //    @Value("${spring.redis.host}")
    private String host = "127.0.0.1";
    //    @Value("${spring.redis.port}")
    private int port = 6379;
    private String AUTH = "";
    //    @Value("${spring.redis.lettuce.pool.max-active}")
    private int maxActive = 10;
    //    @Value("${spring.redis.lettuce.pool.max-idle}")
    private int maxIdle = 5;
    //    @Value("${spring.redis.lettuce.pool.min-idle}")
    private int minIdle = 5;
    //    @Value("${spring.redis.lettuce.pool.max-wait}")
    private int maxWait = 10000;
    //    @Value("${spring.redis.timeout}")
    private int timeout = 10000;
    //    @Value("${spring.redis.lettuce.pool.test-on-borrow}")
    private boolean TEST_ON_BORROW = true;
    private String password = null;

}
