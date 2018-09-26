package com.wedul.common.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;
import org.springframework.stereotype.Component;


/**
 * wedulpos
 *
 * @author wedul
 * @since 2018. 9. 24.
 **/
@Configuration
@EnableRedisHttpSession
public class RedisConfiguration extends AbstractHttpSessionApplicationInitializer {

  @Data
  @Component
  @ConfigurationProperties(prefix = "spring.redis")
  public static class RedisProperties {
    private String host;
    private int port;
    private String password;
  }

  @Autowired
  RedisProperties props;

  @Bean
  public JedisConnectionFactory jedisConnectionFactory() {
    RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration(props.getHost(), props.getPort());
    configuration.setPassword(RedisPassword.of(props.getPassword()));

    return new JedisConnectionFactory(configuration);
  }

  @Bean("redisTemplage")
  public StringRedisTemplate stringRedisTemplate(JedisConnectionFactory jedisConnectionFactory) {

    StringRedisTemplate template = new StringRedisTemplate();
    template.setConnectionFactory(jedisConnectionFactory);

    return template;
  }
}
