package com.wedul.common.config;

import com.wedul.common.util.AES256Cipher;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;

import java.util.Properties;

/**
 * db 접속정보 복호화
 *
 * @author wedul
 * @since 2018. 8. 21.
 **/
@Slf4j
public class EncryptionEnvironmentPostProcessor implements EnvironmentPostProcessor {

  @Override
  public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
    Properties props = new Properties();
    try {
      props.put("spring.datasource.password", AES256Cipher.getInstance().AES_Decode(environment.getProperty("spring.datasource.password")));
      props.put("spring.datasource.username", AES256Cipher.getInstance().AES_Decode(environment.getProperty("spring.datasource.username")));
      props.put("spring.redis.password", AES256Cipher.getInstance().AES_Decode(environment.getProperty("spring.redis.password")));
    } catch (Exception e) {
      log.error("Fail decrypt datasource info", e);
    }

    environment.getPropertySources().addFirst(new PropertiesPropertySource("myProps", props));
  }

}
