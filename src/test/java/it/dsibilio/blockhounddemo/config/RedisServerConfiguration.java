package it.dsibilio.blockhounddemo.config;

import redis.embedded.RedisServer;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Configuration
public class RedisServerConfiguration {

    private final RedisServer redisServer;

    public RedisServerConfiguration(RedisProperties redisProperties) {
        this.redisServer = new RedisServer(redisProperties.getPort());
    }

    @PostConstruct
    public void init() {
        redisServer.start();
    }

    @PreDestroy
    public void destroy() {
        redisServer.stop();
    }

}
